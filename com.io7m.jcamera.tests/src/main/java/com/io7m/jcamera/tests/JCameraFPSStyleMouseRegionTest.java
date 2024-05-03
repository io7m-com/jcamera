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

package com.io7m.jcamera.tests;

import com.io7m.jcamera.JCameraFPSStyleMouseRegion;
import com.io7m.jcamera.JCameraRotationCoefficients;
import com.io7m.jcamera.JCameraScreenOrigin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class JCameraFPSStyleMouseRegionTest
{
  @Test
  public void testCoefficientsBottomLeft()
  {
    final JCameraFPSStyleMouseRegion r =
      JCameraFPSStyleMouseRegion.of(
        JCameraScreenOrigin.SCREEN_ORIGIN_BOTTOM_LEFT,
        640.0,
        480.0);

    {
      final JCameraRotationCoefficients out = r.coefficients(
        0,
        0);
      assertEquals(-1.0, out.horizontal(), 0.0);
      assertEquals(1.0, out.vertical(), 0.0);
    }

    {
      final JCameraRotationCoefficients out = r.coefficients(640.0, 480.0);
      assertEquals(1.0, out.horizontal(), 0.0);
      assertEquals(-1.0, out.vertical(), 0.0);
    }

    {
      final JCameraRotationCoefficients out = r.coefficients(320.0, 240.0);
      assertEquals(0.0, out.horizontal(), 0.0);
      assertEquals(0.0, out.vertical(), 0.0);
    }
  }

  @Test
  public void testCoefficientsBottomLeftExhaustive()
  {
    for (int width = 2; width <= 640; ++width) {
      for (int height = 2; height <= 480; ++height) {
        final JCameraFPSStyleMouseRegion r =
          JCameraFPSStyleMouseRegion.of(
            JCameraScreenOrigin.SCREEN_ORIGIN_BOTTOM_LEFT,
            width,
            height);

        assertEquals(
          JCameraScreenOrigin.SCREEN_ORIGIN_BOTTOM_LEFT,
          r.origin());
        assertEquals(height, r.height(), 0.0);
        assertEquals(width, r.width(), 0.0);

        {
          final JCameraRotationCoefficients out =
            r.coefficients((double) width / 2.0, (double) height / 2.0);
          assertEquals(0.0, out.horizontal(), 0.0);
          assertEquals(0.0, out.vertical(), 0.0);
        }

        {
          final JCameraRotationCoefficients out =
            r.coefficients(0, 0);
          assertEquals(-1.0, out.horizontal(), 0.0);
          assertEquals(1.0, out.vertical(), 0.0);
        }


        {
          final JCameraRotationCoefficients out =
            r.coefficients(width, height);
          assertEquals(1.0, out.horizontal(), 0.0);
          assertEquals(-1.0, out.vertical(), 0.0);
        }
      }
    }
  }

  @Test
  public void testCoefficientsTopLeft()
  {
    final JCameraFPSStyleMouseRegion r =
      JCameraFPSStyleMouseRegion.of(
        JCameraScreenOrigin.SCREEN_ORIGIN_TOP_LEFT,
        640.0,
        480.0);

    {
      final JCameraRotationCoefficients out = r.coefficients(
        0,
        0);
      assertEquals(1.0, out.horizontal(), 0.0);
      assertEquals(1.0, out.vertical(), 0.0);
    }

    {
      final JCameraRotationCoefficients out = r.coefficients(640.0, 480.0);
      assertEquals(-1.0, out.horizontal(), 0.0);
      assertEquals(-1.0, out.vertical(), 0.0);
    }

    {
      final JCameraRotationCoefficients out = r.coefficients(320.0, 240.0);
      assertEquals(0.0, out.horizontal(), 0.0);
      assertEquals(0.0, out.vertical(), 0.0);
    }
  }
}
