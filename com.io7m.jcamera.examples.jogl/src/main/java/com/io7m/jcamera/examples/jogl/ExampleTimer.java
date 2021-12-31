/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

/**
 * <p>
 * Functions to work around the idiotic lack of solution given for bug 6435126.
 * </p>
 * <p>
 * See <a href="http://bugs.java.com/view_bug.do?bug_id=6435126">Bug
 * 6435126</a>.
 * </p>
 */

public final class ExampleTimer
{
  private static volatile boolean ENABLED;

  private ExampleTimer()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Enable the high resolution timer.
   */

  @SuppressWarnings("unused")
  public static void enableHighResolutionTimer()
  {
    if (ENABLED) {
      return;
    }

    new Thread()
    {
      {
        this.setDaemon(true);
        this.setName("high-resolution-timer-hack");
        this.start();
      }

      @Override
      public void run()
      {
        while (true) {
          try {
            Thread.sleep(Integer.MAX_VALUE);
          } catch (final InterruptedException ex) {
            // Nothing
          }
        }
      }
    };

    ENABLED = true;
  }
}
