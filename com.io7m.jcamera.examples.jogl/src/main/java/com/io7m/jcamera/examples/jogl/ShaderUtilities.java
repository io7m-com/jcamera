/*
 * Copyright © 2016 <code@io7m.com> http://io7m.com
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

package com.io7m.jcamera.examples.jogl;

import com.io7m.junreachable.UnreachableCodeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

final class ShaderUtilities
{
  private ShaderUtilities()
  {
    throw new UnreachableCodeException();
  }

  /**
   * @param lines The list of lines.
   *
   * @return {@code true} iff any of the following hold: <ul> <li>The list of
   * lines is empty.</li> <li>There are no lines that contain anything other
   * than whitespace.</li> </ul>
   */

  static boolean isEmpty(
    final List<String> lines)
  {
    Objects.requireNonNull(lines, "Lines");

    for (final String line : lines) {
      Objects.requireNonNull(line, "Line");
      if (!line.matches("^\\s*$")) {
        return false;
      }
    }

    return true;
  }

  /**
   * <p> Read lines of input from {@code stream} until there is nothing
   * left to read, and return a list of all the lines returned. Each line will
   * be terminated with (at least) an LF character. </p>
   *
   * @param stream The input stream.
   *
   * @return A list of lines.
   *
   * @throws IOException Iff an I/O error occurs whilst reading.
   */

  static List<String> readLines(
    final InputStream stream)
    throws IOException
  {
    Objects.requireNonNull(stream, "Input stream");

    final BufferedReader reader =
      new BufferedReader(new InputStreamReader(stream));
    final List<String> lines = new ArrayList<>();
    while (true) {
      final String line = reader.readLine();
      if (line == null) {
        break;
      }
      lines.add(line + "\n");
    }
    reader.close();
    return lines;
  }
}
