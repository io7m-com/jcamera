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

package com.io7m.jcamera.tests;

import org.junit.Assert;
import org.junit.Test;

import com.io7m.jcamera.JCameraScreenOrigin;
import com.io7m.jcamera.JCameraSphericalMouseRegion;
import com.io7m.jtensors.VectorM2F;

@SuppressWarnings("static-method") public final class JCameraSphericalMouseRegionTest
{
  @Test public void testCoefficientsBottomLeft()
  {
    final VectorM2F out = new VectorM2F();
    final JCameraSphericalMouseRegion r =
      JCameraSphericalMouseRegion.newRegion(
        JCameraScreenOrigin.SCREEN_ORIGIN_BOTTOM_LEFT,
        640,
        480);

    r.getPosition(0, 0, out);
    Assert.assertEquals(-1.0f, out.getXF(), 0.0f);
    Assert.assertEquals(-1.0f, out.getYF(), 0.0f);

    r.getPosition(640, 480, out);
    Assert.assertEquals(1.0f, out.getXF(), 0.0f);
    Assert.assertEquals(1.0f, out.getYF(), 0.0f);

    r.getPosition(320, 240, out);
    Assert.assertEquals(0.0f, out.getXF(), 0.0f);
    Assert.assertEquals(0.0f, out.getYF(), 0.0f);
  }

  @Test public void testCoefficientsTopLeft()
  {
    final VectorM2F out = new VectorM2F();
    final JCameraSphericalMouseRegion r =
      JCameraSphericalMouseRegion.newRegion(
        JCameraScreenOrigin.SCREEN_ORIGIN_TOP_LEFT,
        640,
        480);

    r.getPosition(0, 0, out);
    Assert.assertEquals(-1.0f, out.getXF(), 0.0f);
    Assert.assertEquals(1.0f, out.getYF(), 0.0f);

    r.getPosition(640, 480, out);
    Assert.assertEquals(1.0f, out.getXF(), 0.0f);
    Assert.assertEquals(-1.0f, out.getYF(), 0.0f);

    r.getPosition(320, 240, out);
    Assert.assertEquals(0.0f, out.getXF(), 0.0f);
    Assert.assertEquals(0.0f, out.getYF(), 0.0f);
  }
}
