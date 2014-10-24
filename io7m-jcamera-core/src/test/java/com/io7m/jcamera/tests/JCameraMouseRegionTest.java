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

package com.io7m.jcamera.tests;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jcamera.JCameraMouseRegion;
import com.io7m.jcamera.JCameraRotationCoefficients;
import com.io7m.jcamera.JCameraScreenOrigin;
import com.io7m.jtensors.VectorReadable2FType;

@SuppressWarnings("static-method") public final class JCameraMouseRegionTest
{
  private static void dumpVector(
    final String name,
    final VectorReadable2FType v)
  {
    System.out.printf("%-18s : %f %f\n", name, v.getXF(), v.getYF());
  }

  @Test public void testCoefficientsBottomLeft()
  {
    final JCameraRotationCoefficients out = new JCameraRotationCoefficients();
    final JCameraMouseRegion r =
      JCameraMouseRegion.newRegion(
        JCameraScreenOrigin.SCREEN_ORIGIN_BOTTOM_LEFT,
        640,
        480);

    r.getCoefficients(0, 0, out);
    Assert.assertEquals(-1.0f, out.getHorizontal(), 0.0f);
    Assert.assertEquals(1.0f, out.getVertical(), 0.0f);

    r.getCoefficients(640, 480, out);
    Assert.assertEquals(1.0f, out.getHorizontal(), 0.0f);
    Assert.assertEquals(-1.0f, out.getVertical(), 0.0f);

    r.getCoefficients(320, 240, out);
    Assert.assertEquals(0.0f, out.getHorizontal(), 0.0f);
    Assert.assertEquals(0.0f, out.getVertical(), 0.0f);
  }

  @Test public void testCoefficientsBottomLeftExhaustive()
  {
    final JCameraRotationCoefficients out = new JCameraRotationCoefficients();
    final JCameraMouseRegion r =
      JCameraMouseRegion.newRegion(
        JCameraScreenOrigin.SCREEN_ORIGIN_BOTTOM_LEFT,
        640,
        480);

    for (int width = 2; width <= 640; ++width) {
      for (int height = 2; height <= 480; ++height) {
        r.setWidth(width);
        r.setHeight(height);
        r.setScreenOrigin(JCameraScreenOrigin.SCREEN_ORIGIN_BOTTOM_LEFT);

        Assert.assertEquals(
          JCameraScreenOrigin.SCREEN_ORIGIN_BOTTOM_LEFT,
          r.getOrigin());
        Assert.assertEquals(height, r.getHeight(), 0.0f);
        Assert.assertEquals(width, r.getWidth(), 0.0f);

        r.getCoefficients(width / 2.0f, height / 2.0f, out);
        Assert.assertEquals(0.0f, out.getHorizontal(), 0.0f);
        Assert.assertEquals(0.0f, out.getVertical(), 0.0f);

        r.getCoefficients(0, 0, out);
        Assert.assertEquals(-1.0f, out.getHorizontal(), 0.0f);
        Assert.assertEquals(1.0f, out.getVertical(), 0.0f);

        r.getCoefficients(width, height, out);
        Assert.assertEquals(1.0f, out.getHorizontal(), 0.0f);
        Assert.assertEquals(-1.0f, out.getVertical(), 0.0f);
      }
    }
  }

  @Test public void testCoefficientsTopLeft()
  {
    final JCameraRotationCoefficients out = new JCameraRotationCoefficients();
    final JCameraMouseRegion r =
      JCameraMouseRegion.newRegion(
        JCameraScreenOrigin.SCREEN_ORIGIN_TOP_LEFT,
        640,
        480);

    r.getCoefficients(0, 0, out);
    Assert.assertEquals(1.0f, out.getHorizontal(), 0.0f);
    Assert.assertEquals(1.0f, out.getVertical(), 0.0f);

    r.getCoefficients(640, 480, out);
    Assert.assertEquals(-1.0f, out.getHorizontal(), 0.0f);
    Assert.assertEquals(-1.0f, out.getVertical(), 0.0f);

    r.getCoefficients(320, 240, out);
    Assert.assertEquals(0.0f, out.getHorizontal(), 0.0f);
    Assert.assertEquals(0.0f, out.getVertical(), 0.0f);
  }
}
