/*
 * Copyright © 2014 <code@io7m.com> http://io7m.com
 * 
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jcamera.documentation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class ExampleSplit
{
  public static void main(
    final String[] args)
    throws Throwable
  {
    if (args.length < 2) {
      throw new IllegalArgumentException(
        "usage: source-file output-directory basename");
    }

    final String source = args[0];
    assert source != null;
    final File output = new File(args[1]);
    final String basename = args[2];
    assert basename != null;

    if (output.isDirectory() == false) {
      throw new IOException("Not a directory - " + output);
    }

    final BufferedReader br =
      new BufferedReader(new FileReader(new File(source)));

    final List<String> lines = new ArrayList<String>();

    {
      String previous = null;
      for (;;) {
        final String line = br.readLine();
        if (line == null) {
          throw new IllegalStateException("No $example annotation in file");
        }
        if (line.contains("$example")) {
          lines.add(previous);
          lines.add(line);
          break;
        }
        previous = line;
      }
    }

    int count = 0;
    for (;;) {
      final String line = br.readLine();
      if (line == null) {
        count = ExampleSplit.writeExample(count, output, basename, lines);
        break;
      }
      if (line.contains("$example")) {
        final String previous = lines.remove(lines.size() - 1);
        count = ExampleSplit.writeExample(count, output, basename, lines);
        lines.add(previous);
      }
      lines.add(line);
    }
  }

  private static int writeExample(
    final int count,
    final File output_directory,
    final String basename,
    final List<String> lines)
    throws IOException
  {
    final String name = String.format("%s%d.txt", basename, count);
    final File out = new File(output_directory, name);

    final BufferedWriter w = new BufferedWriter(new FileWriter(out));
    try {
      for (final String line : lines) {
        w.write(line + "\n");
      }
    } finally {
      w.flush();
      w.close();
    }

    lines.clear();
    System.err.println("Writing " + out);
    return count + 1;
  }

}
