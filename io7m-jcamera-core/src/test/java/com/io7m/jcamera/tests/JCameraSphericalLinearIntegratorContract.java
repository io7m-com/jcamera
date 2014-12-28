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

import com.io7m.jcamera.JCameraSpherical;
import com.io7m.jcamera.JCameraSphericalInput;
import com.io7m.jcamera.JCameraSphericalLinearIntegratorType;
import com.io7m.jcamera.JCameraSphericalType;
import com.io7m.jnull.NonNull;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorReadable3FType;

public abstract class JCameraSphericalLinearIntegratorContract
{
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

  abstract @NonNull JCameraSphericalLinearIntegratorType newIntegrator(
    final @NonNull JCameraSphericalType c,
    final @NonNull JCameraSphericalInput i);

  @Test public final void testCamera_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);
    Assert.assertEquals(c, d.integratorGetCamera());
  }

  @Test public final void testInput_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);
    Assert.assertEquals(i, d.integratorGetInput());
  }

  @Test public final void testMovingBackward_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setTargetMovingBackwardKey(true);

    d.integratorLinearTargetSetMaximumSpeed(1.0f);
    d.integratorLinearTargetSetAcceleration(1.0f);
    d.integratorLinearTargetSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 18.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testMovingBackward_1()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setTargetMovingBackwardKey(true);

    d.integratorLinearTargetSetMaximumSpeed(0.5f);
    d.integratorLinearTargetSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 13.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testMovingDown_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setTargetMovingDown(true);

    d.integratorLinearTargetSetMaximumSpeed(1.0f);
    d.integratorLinearTargetSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, -10.0f, 8.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testMovingDown_1()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setTargetMovingDown(true);

    d.integratorLinearTargetSetMaximumSpeed(0.5f);
    d.integratorLinearTargetSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, -5.0f, 8.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testMovingDrag_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setTargetMovingForwardKey(true);

    d.integratorLinearTargetSetMaximumSpeed(1.0f);
    d.integratorLinearTargetSetAcceleration(1.0f);
    d.integratorLinearTargetSetDrag(0.0f);
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 7.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    i.setTargetMovingForwardKey(false);
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 7.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testMovingForward_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setTargetMovingForwardKey(true);

    d.integratorLinearTargetSetMaximumSpeed(1.0f);
    d.integratorLinearTargetSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -2.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testMovingForward_1()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setTargetMovingForwardKey(true);

    d.integratorLinearTargetSetMaximumSpeed(0.5f);
    d.integratorLinearTargetSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 3.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testMovingLeft_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setTargetMovingLeftKey(true);

    d.integratorLinearTargetSetMaximumSpeed(1.0f);
    d.integratorLinearTargetSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(-10.0f, 0.0f, 8.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testMovingLeft_1()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setTargetMovingLeftKey(true);

    d.integratorLinearTargetSetMaximumSpeed(0.5f);
    d.integratorLinearTargetSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(-5.0f, 0.0f, 8.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testMovingRight_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setTargetMovingRightKey(true);

    d.integratorLinearTargetSetMaximumSpeed(1.0f);
    d.integratorLinearTargetSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(10.0f, 0.0f, 8.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testMovingRight_1()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setTargetMovingRightKey(true);

    d.integratorLinearTargetSetMaximumSpeed(0.5f);
    d.integratorLinearTargetSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(5.0f, 0.0f, 8.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testMovingUp_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setTargetMovingUp(true);

    d.integratorLinearTargetSetMaximumSpeed(1.0f);
    d.integratorLinearTargetSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 10.0f, 8.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testMovingUp_1()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setTargetMovingUp(true);

    d.integratorLinearTargetSetMaximumSpeed(0.5f);
    d.integratorLinearTargetSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 5.0f, 8.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testStatic()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 8.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testZoomingDrag_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setZoomingIn(true);

    d.integratorLinearZoomSetMaximumSpeed(1.0f);
    d.integratorLinearZoomSetAcceleration(1.0f);
    d.integratorLinearZoomSetDrag(0.0f);
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 7.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    i.setZoomingIn(false);
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 7.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testZoomingIn_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setZoomingIn(true);

    d.integratorLinearZoomSetAcceleration(1.0f);
    d.integratorLinearZoomSetMaximumSpeed(1.0f);
    d.integrate(5.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 3.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testZoomingOut_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInput i = JCameraSphericalInput.newInput();
    final JCameraSphericalLinearIntegratorType d = this.newIntegrator(c, i);

    i.setZoomingOut(true);

    d.integratorLinearZoomSetAcceleration(1.0f);
    d.integratorLinearZoomSetMaximumSpeed(1.0f);
    d.integrate(5.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 13.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalLinearIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }
}
