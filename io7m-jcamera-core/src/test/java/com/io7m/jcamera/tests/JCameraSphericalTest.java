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

import com.io7m.jcamera.JCameraContext;
import com.io7m.jcamera.JCameraSpherical;
import com.io7m.jcamera.JCameraSphericalReadableType;
import com.io7m.jcamera.JCameraSphericalSnapshot;
import com.io7m.jcamera.JCameraSphericalType;
import com.io7m.jequality.AlmostEqualFloat;
import com.io7m.jequality.AlmostEqualFloat.ContextRelative;
import com.io7m.jnull.NonNull;
import com.io7m.jtensors.Matrix4x4FType;
import com.io7m.jtensors.MatrixHeapArrayM4x4F;
import com.io7m.jtensors.MatrixI4x4F;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.parameterized.PMatrix4x4FType;
import com.io7m.jtensors.parameterized.PMatrixHeapArrayM4x4F;
import com.io7m.jtensors.parameterized.PMatrixI4x4F;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("static-method") public final class JCameraSphericalTest
{
  private interface ViewSpace
  {
    // Nothing
  }

  private interface WorldSpace
  {
    // Nothing
  }

  @SuppressWarnings("boxing") private static void dumpVector(
    final String name,
    final VectorReadable3FType v)
  {
    System.out.printf(
      "%-18s : %f %f %f\n",
      name,
      v.getXF(),
      v.getYF(),
      v.getZF());
  }

  private static @NonNull ContextRelative newContextRelative6dp()
  {
    return new ContextRelative();
  }

  private void compareSnapshot(
    final JCameraSphericalReadableType c)
  {
    final JCameraSphericalSnapshot snap = c.cameraMakeSnapshot();
    Assert.assertEquals(
      snap.cameraGetAngleHeading(),
      c.cameraGetAngleHeading(),
      0.0);
    Assert.assertEquals(
      snap.cameraGetAngleIncline(),
      c.cameraGetAngleIncline(),
      0.0);
    this.compareVector(snap.cameraGetForward(), c.cameraGetForward());
    this.compareVector(snap.cameraGetRight(), c.cameraGetRight());
    this.compareVector(snap.cameraGetUp(), c.cameraGetUp());
    this.compareVector(snap.cameraGetPosition(), c.cameraGetPosition());

    final Matrix4x4FType m =
      MatrixHeapArrayM4x4F.newMatrix();
    final PMatrix4x4FType<WorldSpace, ViewSpace> pm =
      PMatrixHeapArrayM4x4F.newMatrix();
    final Matrix4x4FType snap_m =
      MatrixHeapArrayM4x4F.newMatrix();
    final PMatrix4x4FType<WorldSpace, ViewSpace> snap_pm =
      PMatrixHeapArrayM4x4F.newMatrix();
    final JCameraContext ctx = new JCameraContext();

    c.cameraMakeViewMatrix(ctx, m);
    c.cameraMakeViewPMatrix(ctx, pm);

    final PMatrixI4x4F<WorldSpace, ViewSpace> ipm =
      PMatrixI4x4F.newFromReadable(pm);
    final MatrixI4x4F im = MatrixI4x4F.newFromReadable(m);

    snap.cameraMakeViewMatrix(ctx, snap_m);
    snap.cameraMakeViewPMatrix(ctx, snap_pm);

    final PMatrixI4x4F<WorldSpace, ViewSpace> snap_ipm =
      PMatrixI4x4F.newFromReadable(snap_pm);
    final MatrixI4x4F snap_im = MatrixI4x4F.newFromReadable(snap_m);

    Assert.assertEquals(im, snap_im);
    Assert.assertEquals(ipm, snap_ipm);

    final JCameraSphericalSnapshot snap2 = c.cameraMakeSnapshot();
    final JCameraSphericalSnapshot snap3 = snap.cameraMakeSnapshot();

    Assert.assertEquals(snap, snap2);
    Assert.assertEquals(snap2, snap3);
    Assert.assertEquals(snap, snap3);
  }

  private void compareVector(
    final VectorReadable3FType a,
    final VectorReadable3FType b)
  {
    Assert.assertEquals(a.getXF(), b.getXF(), 0.0);
    Assert.assertEquals(a.getYF(), b.getYF(), 0.0);
    Assert.assertEquals(a.getZF(), b.getZF(), 0.0);
  }

  @Test public void testDirectionsAzimuthDecrease90()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();

    System.out.println("-- azimuth decrease 90");

    c.cameraOrbitHeading((float) Math.toRadians(-90));

    {
      final ContextRelative cr = JCameraSphericalTest.newContextRelative6dp();
      final boolean r =
        AlmostEqualFloat.almostEqual(
          cr,
          (float) Math.toRadians(-180.0f),
          c.cameraGetAngleHeading());
      Assert.assertTrue(r);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraSphericalTest.dumpVector("forward expected", expected);
      JCameraSphericalTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForwardProjectedOnXZ();
      JCameraSphericalTest.dumpVector("forward on x/z expected", expected);
      JCameraSphericalTest.dumpVector("forward on x/z actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraSphericalTest.dumpVector("up expected", expected);
      JCameraSphericalTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 1.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraSphericalTest.dumpVector("right expected", expected);
      JCameraSphericalTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(-8.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalTest.dumpVector("position expected", expected);
      JCameraSphericalTest.dumpVector("position actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsAzimuthIncrease180()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();

    System.out.println("-- azimuth increase 180");

    c.cameraOrbitHeading((float) Math.toRadians(180));

    {
      final ContextRelative cr = JCameraSphericalTest.newContextRelative6dp();
      final boolean r =
        AlmostEqualFloat.almostEqual(
          cr,
          (float) Math.toRadians(90.0f),
          c.cameraGetAngleHeading());
      Assert.assertTrue(r);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 1.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraSphericalTest.dumpVector("forward expected", expected);
      JCameraSphericalTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 1.0f);
      final VectorReadable3FType actual = c.cameraGetForwardProjectedOnXZ();
      JCameraSphericalTest.dumpVector("forward on x/z expected", expected);
      JCameraSphericalTest.dumpVector("forward on x/z actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraSphericalTest.dumpVector("up expected", expected);
      JCameraSphericalTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(-1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraSphericalTest.dumpVector("right expected", expected);
      JCameraSphericalTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -8.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalTest.dumpVector("position expected", expected);
      JCameraSphericalTest.dumpVector("position actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsAzimuthIncrease90()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();

    System.out.println("-- azimuth increase 90");

    c.cameraOrbitHeading((float) Math.toRadians(90));

    {
      final ContextRelative cr = JCameraSphericalTest.newContextRelative6dp();
      final boolean r =
        AlmostEqualFloat.almostEqual(
          cr,
          (float) Math.toRadians(0.0f),
          c.cameraGetAngleHeading());
      Assert.assertTrue(r);
    }

    {
      final VectorI3F expected = new VectorI3F(-1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraSphericalTest.dumpVector("forward expected", expected);
      JCameraSphericalTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(-1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForwardProjectedOnXZ();
      JCameraSphericalTest.dumpVector("forward on x/z expected", expected);
      JCameraSphericalTest.dumpVector("forward on x/z actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraSphericalTest.dumpVector("up expected", expected);
      JCameraSphericalTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraSphericalTest.dumpVector("right expected", expected);
      JCameraSphericalTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(8.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalTest.dumpVector("position expected", expected);
      JCameraSphericalTest.dumpVector("position actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsInclineDecrease90()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    c.cameraClampInclineDisable();
    c.cameraOrbitIncline((float) Math.toRadians(-89.99999));

    System.out.println("-- incline decrease 90");

    {
      final ContextRelative cr = JCameraSphericalTest.newContextRelative6dp();
      final boolean r =
        AlmostEqualFloat.almostEqual(
          cr,
          (float) Math.toRadians(-89.99999),
          c.cameraGetAngleIncline());
      Assert.assertTrue(r);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraSphericalTest.dumpVector("forward expected", expected);
      JCameraSphericalTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetForwardProjectedOnXZ();
      JCameraSphericalTest.dumpVector("forward on x/z expected", expected);
      JCameraSphericalTest.dumpVector("forward on x/z actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 1.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraSphericalTest.dumpVector("up expected", expected);
      JCameraSphericalTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraSphericalTest.dumpVector("right expected", expected);
      JCameraSphericalTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, -8.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalTest.dumpVector("position expected", expected);
      JCameraSphericalTest.dumpVector("position actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsInclineIncrease90()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    c.cameraClampInclineDisable();
    c.cameraOrbitIncline((float) Math.toRadians(89.99999));

    System.out.println("-- incline increase 90");

    {
      final ContextRelative cr = JCameraSphericalTest.newContextRelative6dp();
      final boolean r =
        AlmostEqualFloat.almostEqual(
          cr,
          (float) Math.toRadians(89.99999),
          c.cameraGetAngleIncline());
      Assert.assertTrue(r);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, -1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraSphericalTest.dumpVector("forward expected", expected);
      JCameraSphericalTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetForwardProjectedOnXZ();
      JCameraSphericalTest.dumpVector("forward on x/z expected", expected);
      JCameraSphericalTest.dumpVector("forward on x/z actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraSphericalTest.dumpVector("up expected", expected);
      JCameraSphericalTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraSphericalTest.dumpVector("right expected", expected);
      JCameraSphericalTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 8.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalTest.dumpVector("position expected", expected);
      JCameraSphericalTest.dumpVector("position actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsInitial()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();

    System.out.println("-- initial");

    {
      final ContextRelative cr = JCameraSphericalTest.newContextRelative6dp();
      final boolean r =
        AlmostEqualFloat.almostEqual(
          cr,
          (float) Math.toRadians(-90.0f),
          c.cameraGetAngleHeading());
      Assert.assertTrue(r);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraSphericalTest.dumpVector("forward expected", expected);
      JCameraSphericalTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetForwardProjectedOnXZ();
      JCameraSphericalTest.dumpVector("forward on x/z expected", expected);
      JCameraSphericalTest.dumpVector("forward on x/z actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraSphericalTest.dumpVector("up expected", expected);
      JCameraSphericalTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraSphericalTest.dumpVector("right expected", expected);
      JCameraSphericalTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 8.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalTest.dumpVector("position expected", expected);
      JCameraSphericalTest.dumpVector("position actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsRadiusIncrease90()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    c.cameraSetZoom(10.0f);

    System.out.println("-- radius increase 10");

    {
      final ContextRelative cr = JCameraSphericalTest.newContextRelative6dp();
      final boolean r =
        AlmostEqualFloat.almostEqual(
          cr,
          (float) Math.toRadians(-90.0f),
          c.cameraGetAngleHeading());
      Assert.assertTrue(r);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraSphericalTest.dumpVector("forward expected", expected);
      JCameraSphericalTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetForwardProjectedOnXZ();
      JCameraSphericalTest.dumpVector("forward on x/z expected", expected);
      JCameraSphericalTest.dumpVector("forward on x/z actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraSphericalTest.dumpVector("up expected", expected);
      JCameraSphericalTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraSphericalTest.dumpVector("right expected", expected);
      JCameraSphericalTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 10.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalTest.dumpVector("position expected", expected);
      JCameraSphericalTest.dumpVector("position actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testEquality()
  {
    final JCameraSphericalType c0 = JCameraSpherical.newCamera();

    c0.cameraSetAngleHeading((float) (Math.random() * 100.0f));
    c0.cameraSetAngleIncline((float) (Math.random() * 100.0f));
    c0.cameraSetTargetPosition3f(
      (float) (Math.random() * 100.0f),
      (float) (Math.random() * 100.0f),
      (float) (Math.random() * 100.0f));
    c0.cameraSetZoom((float) (Math.random() * 100.0f));

    final JCameraSphericalType c1 = JCameraSpherical.newCameraFrom(c0);

    System.out.println("c0: " + c0);
    System.out.println("c1: " + c1);

    Assert.assertEquals(c0, c1);
    Assert.assertEquals(c0.hashCode(), c1.hashCode());
    Assert.assertEquals(c0.toString(), c1.toString());

    this.compareSnapshot(c1);
    this.compareSnapshot(c0);
  }

  @Test public void testPositionsOffset100()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    c.cameraSetTargetPosition3f(100.0f, 100.0f, 100.0f);

    System.out.println("-- position offset 100");

    {
      final ContextRelative cr = JCameraSphericalTest.newContextRelative6dp();
      final boolean r =
        AlmostEqualFloat.almostEqual(
          cr,
          (float) Math.toRadians(-90.0f),
          c.cameraGetAngleHeading());
      Assert.assertTrue(r);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraSphericalTest.dumpVector("forward expected", expected);
      JCameraSphericalTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraSphericalTest.dumpVector("up expected", expected);
      JCameraSphericalTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraSphericalTest.dumpVector("right expected", expected);
      JCameraSphericalTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(100.0f, 100.0f, 108.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalTest.dumpVector("position expected", expected);
      JCameraSphericalTest.dumpVector("position actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testSnapshotEquality()
  {
    final JCameraSphericalType c0 = JCameraSpherical.newCamera();

    c0.cameraOrbitHeading((float) (Math.random() * 100.0f));
    c0.cameraOrbitIncline((float) (Math.random() * 100.0f));
    c0.cameraMoveTargetRight((float) (Math.random() * 100.0f));
    c0.cameraMoveTargetUp((float) (Math.random() * 100.0f));
    c0.cameraMoveTargetForwardOnXZ((float) (Math.random() * 100.0f));
    c0.cameraZoomOut((float) (Math.random() * 100.0f));

    final JCameraSphericalType c1 = JCameraSpherical.newCameraFrom(c0);

    c1.cameraOrbitHeading((float) (Math.random() * 100.0f));
    c1.cameraOrbitIncline((float) (Math.random() * 100.0f));
    c1.cameraMoveTargetRight((float) (Math.random() * 100.0f));
    c1.cameraMoveTargetUp((float) (Math.random() * 100.0f));
    c1.cameraMoveTargetForwardOnXZ((float) (Math.random() * 100.0f));
    c1.cameraZoomOut((float) (Math.random() * 100.0f));

    System.out.println("c0: " + c0);
    System.out.println("c1: " + c1);

    final JCameraSphericalSnapshot snap_0 = c0.cameraMakeSnapshot();
    final JCameraSphericalSnapshot snap_1 = c1.cameraMakeSnapshot();

    Assert.assertEquals(snap_0, snap_0);
    Assert.assertNotEquals(snap_0, snap_1);
    Assert.assertNotEquals(snap_0, null);
    Assert.assertNotEquals(snap_0, Integer.valueOf(23));

    Assert.assertEquals(snap_0.hashCode(), snap_0.hashCode());
    Assert.assertNotEquals(snap_0.hashCode(), snap_1.hashCode());

    Assert.assertEquals(snap_0.toString(), snap_0.toString());
    Assert.assertNotEquals(snap_0.toString(), snap_1.toString());
  }

  @Test public void testSnapshotInterpolation()
  {
    final JCameraSphericalType c0 = JCameraSpherical.newCamera();

    c0.cameraOrbitHeading((float) (Math.random() * 100.0f));
    c0.cameraOrbitIncline((float) (Math.random() * 100.0f));
    c0.cameraMoveTargetRight((float) (Math.random() * 100.0f));
    c0.cameraMoveTargetUp((float) (Math.random() * 100.0f));
    c0.cameraMoveTargetForwardOnXZ((float) (Math.random() * 100.0f));
    c0.cameraZoomOut((float) (Math.random() * 100.0f));

    final JCameraSphericalType c1 = JCameraSpherical.newCamera();

    System.out.println("c0: " + c0);
    System.out.println("c1: " + c1);

    final JCameraSphericalSnapshot snap_0 = c0.cameraMakeSnapshot();
    final JCameraSphericalSnapshot snap_1 = c1.cameraMakeSnapshot();

    System.out.println("snap_0: " + snap_0);
    System.out.println("snap_1: " + snap_1);

    final JCameraSphericalSnapshot snap_0_i0 =
      JCameraSphericalSnapshot.interpolate(snap_0, snap_1, 0.0f);
    final JCameraSphericalSnapshot snap_0_i1 =
      JCameraSphericalSnapshot.interpolate(snap_0, snap_1, 1.0f);

    System.out.println("snap_0    : " + snap_0);
    System.out.println("snap_0_i0 : " + snap_0_i0);

    Assert.assertEquals(
      snap_0.cameraGetAngleHeading(),
      snap_0_i0.cameraGetAngleHeading(),
      0.0);
    Assert.assertEquals(
      snap_0.cameraGetAngleIncline(),
      snap_0_i0.cameraGetAngleIncline(),
      0.0);
    Assert.assertEquals(
      snap_0.cameraGetZoom(),
      snap_0_i0.cameraGetZoom(),
      0.0);
    Assert.assertEquals(
      snap_0.cameraGetForward(),
      snap_0_i0.cameraGetForward());
    Assert.assertEquals(snap_0.cameraGetRight(), snap_0_i0.cameraGetRight());
    Assert.assertEquals(snap_0.cameraGetUp(), snap_0_i0.cameraGetUp());
    Assert.assertEquals(
      snap_0.cameraGetForwardProjectedOnXZ(),
      snap_0_i0.cameraGetForwardProjectedOnXZ());
    Assert.assertEquals(
      snap_0.cameraGetPosition(),
      snap_0_i0.cameraGetPosition());
    Assert.assertEquals(
      snap_0.cameraGetTargetPosition(),
      snap_0_i0.cameraGetTargetPosition());

    System.out.println("snap_0_i1 : " + snap_0_i1);
    System.out.println("snap_1    : " + snap_1);

    Assert.assertEquals(
      snap_1.cameraGetAngleHeading(),
      snap_0_i1.cameraGetAngleHeading(),
      0.0);
    Assert.assertEquals(
      snap_1.cameraGetAngleIncline(),
      snap_0_i1.cameraGetAngleIncline(),
      0.0);
    Assert.assertEquals(
      snap_1.cameraGetZoom(),
      snap_0_i1.cameraGetZoom(),
      0.0);
    Assert.assertEquals(
      snap_1.cameraGetForward(),
      snap_0_i1.cameraGetForward());
    Assert.assertEquals(snap_1.cameraGetRight(), snap_0_i1.cameraGetRight());
    Assert.assertEquals(snap_1.cameraGetUp(), snap_0_i1.cameraGetUp());

    final ContextRelative ctx = new ContextRelative();
    ctx.setMaxAbsoluteDifference(0.0000001f);
    Assert.assertTrue(VectorI3F.almostEqual(
      ctx,
      snap_1.cameraGetForwardProjectedOnXZ(),
      snap_0_i1.cameraGetForwardProjectedOnXZ()));

    Assert.assertEquals(
      snap_1.cameraGetPosition(),
      snap_0_i1.cameraGetPosition());
    Assert.assertEquals(
      snap_1.cameraGetTargetPosition(),
      snap_0_i1.cameraGetTargetPosition());
  }
}
