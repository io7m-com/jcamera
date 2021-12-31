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
import com.io7m.jcamera.JCameraSphericalInput;
import com.io7m.jcamera.JCameraSphericalInputType;
import com.io7m.jcamera.JCameraSphericalLinearIntegratorType;
import com.io7m.jcamera.JCameraSphericalType;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class JCameraSphericalLinearIntegratorContract
{
  @SuppressWarnings("boxing")
  private static void dumpVector(
    final String name,
    final Vector3D v)
  {
    System.out.printf(
      "%-18s : %f %f %f\n",
      name,
      v.x(),
      v.y(),
      v.z());
  }

  abstract JCameraSphericalLinearIntegratorType newIntegrator(
    JCameraSphericalType c,
    JCameraSphericalInputType i);

  @Test
  public final void testCamera_0()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);
    assertEquals(c, d.integratorGetCamera());
  }

  @Test
  public final void testInput_0()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);
    assertEquals(i, d.integratorGetInput());
  }

  @Test
  public final void testMovingBackward_0()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setTargetMovingBackwardKey(true);

    d.integratorLinearTargetSetMaximumSpeed(1.0);
    d.integratorLinearTargetSetAcceleration(1.0);
    d.integratorLinearTargetSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, 18.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testMovingBackward_1()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setTargetMovingBackwardKey(true);

    d.integratorLinearTargetSetMaximumSpeed(0.5);
    d.integratorLinearTargetSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, 13.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testMovingDown_0()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setTargetMovingDown(true);

    d.integratorLinearTargetSetMaximumSpeed(1.0);
    d.integratorLinearTargetSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, -10.0, 8.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testMovingDown_1()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setTargetMovingDown(true);

    d.integratorLinearTargetSetMaximumSpeed(0.5);
    d.integratorLinearTargetSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, -5.0, 8.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testMovingDrag_0()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setTargetMovingForwardKey(true);

    d.integratorLinearTargetSetMaximumSpeed(1.0);
    d.integratorLinearTargetSetAcceleration(1.0);
    d.integratorLinearTargetSetDrag(0.0);
    d.integrate(1.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, 7.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    i.setTargetMovingForwardKey(false);
    d.integrate(1.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, 7.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testMovingForward_0()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setTargetMovingForwardKey(true);

    d.integratorLinearTargetSetMaximumSpeed(1.0);
    d.integratorLinearTargetSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, -2.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testMovingForward_1()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setTargetMovingForwardKey(true);

    d.integratorLinearTargetSetMaximumSpeed(0.5);
    d.integratorLinearTargetSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, 3.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testMovingLeft_0()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setTargetMovingLeftKey(true);

    d.integratorLinearTargetSetMaximumSpeed(1.0);
    d.integratorLinearTargetSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(-10.0, 0.0, 8.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testMovingLeft_1()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setTargetMovingLeftKey(true);

    d.integratorLinearTargetSetMaximumSpeed(0.5);
    d.integratorLinearTargetSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(-5.0, 0.0, 8.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testMovingRight_0()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setTargetMovingRightKey(true);

    d.integratorLinearTargetSetMaximumSpeed(1.0);
    d.integratorLinearTargetSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(10.0, 0.0, 8.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testMovingRight_1()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setTargetMovingRightKey(true);

    d.integratorLinearTargetSetMaximumSpeed(0.5);
    d.integratorLinearTargetSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(5.0, 0.0, 8.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testMovingUp_0()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setTargetMovingUp(true);

    d.integratorLinearTargetSetMaximumSpeed(1.0);
    d.integratorLinearTargetSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, 10.0, 8.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testMovingUp_1()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setTargetMovingUp(true);

    d.integratorLinearTargetSetMaximumSpeed(0.5);
    d.integratorLinearTargetSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, 5.0, 8.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testStatic()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    d.integrate(1.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, 8.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testZoomingDrag_0()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setZoomingIn(true);

    d.integratorLinearZoomSetMaximumSpeed(1.0);
    d.integratorLinearZoomSetAcceleration(1.0);
    d.integratorLinearZoomSetDrag(0.0);
    d.integrate(1.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, 7.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    i.setZoomingIn(false);
    d.integrate(1.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, 7.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testZoomingIn_0()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setZoomingIn(true);

    d.integratorLinearZoomSetAcceleration(1.0);
    d.integratorLinearZoomSetMaximumSpeed(1.0);
    d.integrate(5.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, 3.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testZoomingOut_0()
  {
    final var c = JCameraSpherical.newCamera();
    final var i = JCameraSphericalInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setZoomingOut(true);

    d.integratorLinearZoomSetAcceleration(1.0);
    d.integratorLinearZoomSetMaximumSpeed(1.0);
    d.integrate(5.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, 13.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }
}
