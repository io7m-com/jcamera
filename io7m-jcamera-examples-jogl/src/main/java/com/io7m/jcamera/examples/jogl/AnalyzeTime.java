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

package com.io7m.jcamera.examples.jogl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

// CHECKSTYLE:OFF

public final class AnalyzeTime implements Runnable
{
  private final BufferedReader reader;
  private Long                 sim_time_last;
  private int                  sim_count;
  private double               sim_diff_total;
  private double               sim_diff_upper;
  private double               sim_diff_lower;
  private int                  run_sim;
  private int                  run_renderer;
  private int                  renderer_count;
  private Long                 renderer_time_last;
  private double               renderer_diff_total;
  private double               renderer_diff_lower;
  private double               renderer_diff_upper;

  public AnalyzeTime(
    final Reader r)
  {
    this.reader = new BufferedReader(r);
    this.sim_time_last = Long.valueOf(0);
    this.sim_diff_upper = -Double.MAX_VALUE;
    this.sim_diff_lower = Double.MAX_VALUE;
    this.renderer_time_last = Long.valueOf(0);
  }

  public static void main(
    final String[] args)
    throws FileNotFoundException
  {
    if (args.length < 1) {
      System.err.println("usage: file");
      System.exit(1);
    }

    final AnalyzeTime a = new AnalyzeTime(new FileReader(new File(args[0])));
    a.run();
  }

  @Override public void run()
  {
    try {
      while (true) {
        final String line = this.reader.readLine();
        if (line == null) {
          break;
        }

        final String[] segments = line.split("\\s+");
        if ("render".equals(segments[0])) {
          ++this.run_renderer;
          this.run_sim = 0;
          this.handleRenderer(segments);
        }
        if ("simulation".equals(segments[0])) {
          ++this.run_sim;
          this.run_renderer = 0;
          this.handleSimulation(segments);
        }
      }

      System.out.println("Simulation average time: "
        + (this.sim_diff_total / this.sim_count));
      System.out.println("Simulation maximum time: " + this.sim_diff_upper);
      System.out.println("Simulation minimum time: " + this.sim_diff_lower);

      System.out.println("Renderer average time: "
        + (this.renderer_diff_total / this.renderer_count));
      System.out
        .println("Renderer maximum time: " + this.renderer_diff_upper);
      System.out
        .println("Renderer minimum time: " + this.renderer_diff_lower);

    } catch (final IOException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

  private void handleSimulation(
    final String[] segments)
  {
    final Long time = Long.valueOf(segments[1]);

    if (this.sim_count > 0) {
      final double diff_nanos = time - this.sim_time_last;
      final double diff_ms = diff_nanos / 1000000.0;
      this.sim_diff_total = this.sim_diff_total + diff_ms;
      this.sim_diff_lower = Math.min(this.sim_diff_lower, diff_ms);
      this.sim_diff_upper = Math.max(this.sim_diff_upper, diff_ms);
    }

    ++this.sim_count;
    this.sim_time_last = time;
  }

  private void handleRenderer(
    final String[] segments)
  {
    final Long time = Long.valueOf(segments[1]);

    if (this.renderer_count > 0) {
      final double diff_nanos = time - this.renderer_time_last;
      final double diff_ms = diff_nanos / 1000000.0;
      this.renderer_diff_total = this.renderer_diff_total + diff_ms;
      this.renderer_diff_lower = Math.min(this.renderer_diff_lower, diff_ms);
      this.renderer_diff_upper = Math.max(this.renderer_diff_upper, diff_ms);
    }

    ++this.renderer_count;
    this.renderer_time_last = time;
  }
}
