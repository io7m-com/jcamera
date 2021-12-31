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

import com.io7m.jcamera.JCameraSpherical;
import com.io7m.jcamera.JCameraSphericalReadableType;
import com.io7m.jcamera.JCameraSphericalSnapshot;
import com.io7m.jcamera.JCameraSphericalSnapshots;
import com.io7m.jcamera.JCameraSphericalType;
import com.io7m.jequality.AlmostEqualDouble;
import com.io7m.jequality.AlmostEqualDouble.ContextRelative;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix4x4D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class JCameraSphericalTest
{
  private static void dumpVector(
    final String name,
    final Vector3D v)
  {
    System.out.printf(
      "%-18s : %f %f %f\n",
      name,
      Double.valueOf(v.x()),
      Double.valueOf(v.y()),
      Double.valueOf(v.z()));
  }

  private static ContextRelative newContextRelative6dp()
  {
    return new ContextRelative();
  }

  private static double random()
  {
    return Math.random();
  }

  private void compareSnapshot(
    final JCameraSphericalReadableType c)
  {
    final JCameraSphericalSnapshot snap = JCameraSphericalSnapshots.of(c);
    assertEquals(
      snap.cameraGetAngleHeading(),
      c.cameraGetAngleHeading(),
      0.0);
    assertEquals(
      snap.cameraGetAngleIncline(),
      c.cameraGetAngleIncline(),
      0.0);
    this.compareVector(snap.cameraGetForward(), c.cameraGetForward());
    this.compareVector(snap.cameraGetRight(), c.cameraGetRight());
    this.compareVector(snap.cameraGetUp(), c.cameraGetUp());
    this.compareVector(snap.cameraGetPosition(), c.cameraGetPosition());

    final Matrix4x4D m = c.cameraMakeViewMatrix();
    final PMatrix4x4D<Object, Object> pm = c.cameraMakeViewPMatrix();

    final Matrix4x4D snap_m = snap.cameraMakeViewMatrix();
    final PMatrix4x4D<Object, Object> snap_pm = snap.cameraMakeViewPMatrix();

    assertEquals(m, snap_m);
    assertEquals(pm, snap_pm);

    final JCameraSphericalSnapshot snap2 = JCameraSphericalSnapshots.of(c);
    final JCameraSphericalSnapshot snap3 = JCameraSphericalSnapshots.of(snap);

    assertEquals(snap, snap2);
    assertEquals(snap2, snap3);
    assertEquals(snap, snap3);
  }

  private void compareVector(
    final Vector3D a,
    final Vector3D b)
  {
    assertEquals(a.x(), b.x(), 0.0);
    assertEquals(a.y(), b.y(), 0.0);
    assertEquals(a.z(), b.z(), 0.0);
  }

  @Test
  public void testDirectionsAzimuthDecrease90()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();

    System.out.println("-- azimuth decrease 90");

    c.cameraOrbitHeading(Math.toRadians(-90.0));

    {
      final ContextRelative cr = newContextRelative6dp();
      final boolean r =
        AlmostEqualDouble.almostEqual(
          cr,
          Math.toRadians(-180.0),
          c.cameraGetAngleHeading());
      assertTrue(r);
    }

    {
      final Vector3D expected = Vector3D.of(1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      dumpVector("forward expected", expected);
      dumpVector("forward actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetForwardProjectedOnXZ();
      dumpVector("forward on x/z expected", expected);
      dumpVector("forward on x/z actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 1.0, 0.0);
      final Vector3D actual = c.cameraGetUp();
      dumpVector("up expected", expected);
      dumpVector("up actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, 1.0);
      final Vector3D actual = c.cameraGetRight();
      dumpVector("right expected", expected);
      dumpVector("right actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(-8.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    this.compareSnapshot(c);
  }

  @Test
  public void testDirectionsAzimuthIncrease180()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();

    System.out.println("-- azimuth increase 180");

    c.cameraOrbitHeading(Math.toRadians(180.0));

    {
      final ContextRelative cr = newContextRelative6dp();
      final boolean r =
        AlmostEqualDouble.almostEqual(
          cr,
          Math.toRadians(90.0),
          c.cameraGetAngleHeading());
      assertTrue(r);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, 1.0);
      final Vector3D actual = c.cameraGetForward();
      dumpVector("forward expected", expected);
      dumpVector("forward actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, 1.0);
      final Vector3D actual = c.cameraGetForwardProjectedOnXZ();
      dumpVector("forward on x/z expected", expected);
      dumpVector("forward on x/z actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 1.0, 0.0);
      final Vector3D actual = c.cameraGetUp();
      dumpVector("up expected", expected);
      dumpVector("up actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(-1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetRight();
      dumpVector("right expected", expected);
      dumpVector("right actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, -8.0);
      final Vector3D actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    this.compareSnapshot(c);
  }

  @Test
  public void testDirectionsAzimuthIncrease90()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();

    System.out.println("-- azimuth increase 90");

    c.cameraOrbitHeading(Math.toRadians(90.0));

    {
      final ContextRelative cr = newContextRelative6dp();
      final boolean r =
        AlmostEqualDouble.almostEqual(
          cr,
          Math.toRadians(0.0),
          c.cameraGetAngleHeading());
      assertTrue(r);
    }

    {
      final Vector3D expected = Vector3D.of(-1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      dumpVector("forward expected", expected);
      dumpVector("forward actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(-1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetForwardProjectedOnXZ();
      dumpVector("forward on x/z expected", expected);
      dumpVector("forward on x/z actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 1.0, 0.0);
      final Vector3D actual = c.cameraGetUp();
      dumpVector("up expected", expected);
      dumpVector("up actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, -1.0);
      final Vector3D actual = c.cameraGetRight();
      dumpVector("right expected", expected);
      dumpVector("right actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(8.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    this.compareSnapshot(c);
  }

  @Test
  public void testDirectionsInclineDecrease90()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    c.cameraClampInclineDisable();
    c.cameraOrbitIncline(Math.toRadians(-89.99999));

    System.out.println("-- incline decrease 90");

    {
      final ContextRelative cr = newContextRelative6dp();
      final boolean r =
        AlmostEqualDouble.almostEqual(
          cr,
          Math.toRadians(-89.99999),
          c.cameraGetAngleIncline());
      assertTrue(r);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 1.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      dumpVector("forward expected", expected);
      dumpVector("forward actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, -1.0);
      final Vector3D actual = c.cameraGetForwardProjectedOnXZ();
      dumpVector("forward on x/z expected", expected);
      dumpVector("forward on x/z actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, 1.0);
      final Vector3D actual = c.cameraGetUp();
      dumpVector("up expected", expected);
      dumpVector("up actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetRight();
      dumpVector("right expected", expected);
      dumpVector("right actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, -8.0, 0.0);
      final Vector3D actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    this.compareSnapshot(c);
  }

  @Test
  public void testDirectionsInclineIncrease90()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    c.cameraClampInclineDisable();
    c.cameraOrbitIncline(Math.toRadians(89.99999));

    System.out.println("-- incline increase 90");

    {
      final ContextRelative cr = newContextRelative6dp();
      final boolean r =
        AlmostEqualDouble.almostEqual(
          cr,
          Math.toRadians(89.99999),
          c.cameraGetAngleIncline());
      assertTrue(r);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, -1.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      dumpVector("forward expected", expected);
      dumpVector("forward actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, -1.0);
      final Vector3D actual = c.cameraGetForwardProjectedOnXZ();
      dumpVector("forward on x/z expected", expected);
      dumpVector("forward on x/z actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, -1.0);
      final Vector3D actual = c.cameraGetUp();
      dumpVector("up expected", expected);
      dumpVector("up actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetRight();
      dumpVector("right expected", expected);
      dumpVector("right actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 8.0, 0.0);
      final Vector3D actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    this.compareSnapshot(c);
  }

  @Test
  public void testDirectionsInitial()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();

    System.out.println("-- initial");

    {
      final ContextRelative cr = newContextRelative6dp();
      final boolean r =
        AlmostEqualDouble.almostEqual(
          cr,
          Math.toRadians(-90.0),
          c.cameraGetAngleHeading());
      assertTrue(r);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, -1.0);
      final Vector3D actual = c.cameraGetForward();
      dumpVector("forward expected", expected);
      dumpVector("forward actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, -1.0);
      final Vector3D actual = c.cameraGetForwardProjectedOnXZ();
      dumpVector("forward on x/z expected", expected);
      dumpVector("forward on x/z actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 1.0, 0.0);
      final Vector3D actual = c.cameraGetUp();
      dumpVector("up expected", expected);
      dumpVector("up actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetRight();
      dumpVector("right expected", expected);
      dumpVector("right actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, 8.0);
      final Vector3D actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    this.compareSnapshot(c);
  }

  @Test
  public void testDirectionsRadiusIncrease90()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    c.cameraSetZoom(10.0);

    System.out.println("-- radius increase 10");

    {
      final ContextRelative cr = newContextRelative6dp();
      final boolean r =
        AlmostEqualDouble.almostEqual(
          cr,
          Math.toRadians(-90.0),
          c.cameraGetAngleHeading());
      assertTrue(r);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, -1.0);
      final Vector3D actual = c.cameraGetForward();
      dumpVector("forward expected", expected);
      dumpVector("forward actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, -1.0);
      final Vector3D actual = c.cameraGetForwardProjectedOnXZ();
      dumpVector("forward on x/z expected", expected);
      dumpVector("forward on x/z actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 1.0, 0.0);
      final Vector3D actual = c.cameraGetUp();
      dumpVector("up expected", expected);
      dumpVector("up actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetRight();
      dumpVector("right expected", expected);
      dumpVector("right actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, 10.0);
      final Vector3D actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    this.compareSnapshot(c);
  }

  @Test
  public void testEquality()
  {
    final JCameraSphericalType c0 = JCameraSpherical.newCamera();

    c0.cameraSetAngleHeading(random() * 100.0);
    c0.cameraSetAngleIncline(random() * 100.0);
    c0.cameraSetTargetPosition3(
      random() * 100.0,
      random() * 100.0,
      random() * 100.0);
    c0.cameraSetZoom(random() * 100.0);

    final JCameraSphericalType c1 = JCameraSpherical.newCameraFrom(c0);

    System.out.println("c0: " + c0);
    System.out.println("c1: " + c1);

    assertEquals(c0, c1);
    assertEquals(c0.hashCode(), (long) c1.hashCode());
    assertEquals(c0.toString(), c1.toString());

    this.compareSnapshot(c1);
    this.compareSnapshot(c0);
  }

  @Test
  public void testPositionsOffset100()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    c.cameraSetTargetPosition3(100.0, 100.0, 100.0);

    System.out.println("-- position offset 100");

    {
      final ContextRelative cr = newContextRelative6dp();
      final boolean r =
        AlmostEqualDouble.almostEqual(
          cr,
          Math.toRadians(-90.0),
          c.cameraGetAngleHeading());
      assertTrue(r);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, -1.0);
      final Vector3D actual = c.cameraGetForward();
      dumpVector("forward expected", expected);
      dumpVector("forward actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 1.0, 0.0);
      final Vector3D actual = c.cameraGetUp();
      dumpVector("up expected", expected);
      dumpVector("up actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetRight();
      dumpVector("right expected", expected);
      dumpVector("right actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(100.0, 100.0, 108.0);
      final Vector3D actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    this.compareSnapshot(c);
  }

  @Test
  public void testSnapshotEquality()
  {
    final JCameraSphericalType c0 = JCameraSpherical.newCamera();

    c0.cameraOrbitHeading(random() * 100.0);
    c0.cameraOrbitIncline(random() * 100.0);
    c0.cameraMoveTargetRight(random() * 100.0);
    c0.cameraMoveTargetUp(random() * 100.0);
    c0.cameraMoveTargetForwardOnXZ(random() * 100.0);
    c0.cameraZoomOut(random() * 100.0);

    final JCameraSphericalType c1 = JCameraSpherical.newCameraFrom(c0);

    c1.cameraOrbitHeading(random() * 100.0);
    c1.cameraOrbitIncline(random() * 100.0);
    c1.cameraMoveTargetRight(random() * 100.0);
    c1.cameraMoveTargetUp(random() * 100.0);
    c1.cameraMoveTargetForwardOnXZ(random() * 100.0);
    c1.cameraZoomOut(random() * 100.0);

    System.out.println("c0: " + c0);
    System.out.println("c1: " + c1);

    final JCameraSphericalSnapshot snap_0 = JCameraSphericalSnapshots.of(c0);
    final JCameraSphericalSnapshot snap_1 = JCameraSphericalSnapshots.of(c1);

    assertEquals(snap_0, snap_0);
    assertNotEquals(snap_0, snap_1);
    assertNotEquals(snap_0, null);
    assertNotEquals(snap_0, Integer.valueOf(23));

    assertEquals(snap_0.hashCode(), (long) snap_0.hashCode());
    assertNotEquals(snap_0.hashCode(), (long) snap_1.hashCode());

    assertEquals(snap_0.toString(), snap_0.toString());
    assertNotEquals(snap_0.toString(), snap_1.toString());
  }

  @Test
  public void testSnapshotInterpolation()
  {
    final JCameraSphericalType c0 = JCameraSpherical.newCamera();

    c0.cameraOrbitHeading(random() * 100.0);
    c0.cameraOrbitIncline(random() * 100.0);
    c0.cameraMoveTargetRight(random() * 100.0);
    c0.cameraMoveTargetUp(random() * 100.0);
    c0.cameraMoveTargetForwardOnXZ(random() * 100.0);
    c0.cameraZoomOut(random() * 100.0);

    final JCameraSphericalType c1 = JCameraSpherical.newCamera();

    System.out.println("c0: " + c0);
    System.out.println("c1: " + c1);

    final JCameraSphericalSnapshot snap_0 = JCameraSphericalSnapshots.of(c0);
    final JCameraSphericalSnapshot snap_1 = JCameraSphericalSnapshots.of(c1);

    System.out.println("snap_0: " + snap_0);
    System.out.println("snap_1: " + snap_1);

    final JCameraSphericalSnapshot snap_0_i0 =
      JCameraSphericalSnapshots.interpolate(snap_0, snap_1, 0.0);
    final JCameraSphericalSnapshot snap_0_i1 =
      JCameraSphericalSnapshots.interpolate(snap_0, snap_1, 1.0);

    System.out.println("snap_0    : " + snap_0);
    System.out.println("snap_0_i0 : " + snap_0_i0);

    assertEquals(
      snap_0.cameraGetAngleHeading(),
      snap_0_i0.cameraGetAngleHeading(),
      0.0);
    assertEquals(
      snap_0.cameraGetAngleIncline(),
      snap_0_i0.cameraGetAngleIncline(),
      0.0);
    assertEquals(
      snap_0.cameraGetZoom(),
      snap_0_i0.cameraGetZoom(),
      0.0);
    assertEquals(
      snap_0.cameraGetForward(),
      snap_0_i0.cameraGetForward());
    assertEquals(snap_0.cameraGetRight(), snap_0_i0.cameraGetRight());
    assertEquals(snap_0.cameraGetUp(), snap_0_i0.cameraGetUp());
    assertEquals(
      snap_0.cameraGetForwardProjectedOnXZ(),
      snap_0_i0.cameraGetForwardProjectedOnXZ());
    assertEquals(
      snap_0.cameraGetPosition(),
      snap_0_i0.cameraGetPosition());
    assertEquals(
      snap_0.cameraGetTargetPosition(),
      snap_0_i0.cameraGetTargetPosition());

    System.out.println("snap_0_i1 : " + snap_0_i1);
    System.out.println("snap_1    : " + snap_1);

    assertEquals(
      snap_1.cameraGetAngleHeading(),
      snap_0_i1.cameraGetAngleHeading(),
      0.0);
    assertEquals(
      snap_1.cameraGetAngleIncline(),
      snap_0_i1.cameraGetAngleIncline(),
      0.0);
    assertEquals(
      snap_1.cameraGetZoom(),
      snap_0_i1.cameraGetZoom(),
      0.0);
    assertEquals(
      snap_1.cameraGetForward(),
      snap_0_i1.cameraGetForward());
    assertEquals(snap_1.cameraGetRight(), snap_0_i1.cameraGetRight());
    assertEquals(snap_1.cameraGetUp(), snap_0_i1.cameraGetUp());

    final ContextRelative ctx = new ContextRelative();
    ctx.setMaxAbsoluteDifference(0.0000001);

    assertTrue(
      AlmostEqualDouble.almostEqual(
        ctx, snap_1.cameraGetForwardProjectedOnXZ().x(),
        snap_0_i1.cameraGetForwardProjectedOnXZ().x()));
    assertTrue(
      AlmostEqualDouble.almostEqual(
        ctx, snap_1.cameraGetForwardProjectedOnXZ().y(),
        snap_0_i1.cameraGetForwardProjectedOnXZ().y()));
    assertTrue(
      AlmostEqualDouble.almostEqual(
        ctx, snap_1.cameraGetForwardProjectedOnXZ().z(),
        snap_0_i1.cameraGetForwardProjectedOnXZ().z()));

    assertEquals(
      snap_1.cameraGetPosition(),
      snap_0_i1.cameraGetPosition());
    assertEquals(
      snap_1.cameraGetTargetPosition(),
      snap_0_i1.cameraGetTargetPosition());
  }

  private interface ViewSpace
  {
    // Nothing
  }

  private interface WorldSpace
  {
    // Nothing
  }
}
