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

import com.io7m.jcamera.JCameraFPSStyle;
import com.io7m.jcamera.JCameraFPSStyleInput;
import com.io7m.jcamera.JCameraFPSStyleInputType;
import com.io7m.jcamera.JCameraFPSStyleLinearIntegratorType;
import com.io7m.jcamera.JCameraFPSStyleType;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class JCameraFPSStyleLinearIntegratorContract
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

  abstract JCameraFPSStyleLinearIntegratorType newIntegrator(
    JCameraFPSStyleType c,
    JCameraFPSStyleInputType i);

  @Test
  public final void testCamera_0()
  {
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);
    assertEquals(c, d.integratorGetCamera());
  }

  @Test
  public final void testInput_0()
  {
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);
    assertEquals(i, d.integratorGetInput());
  }

  @Test
  public final void testMovingBackward_0()
  {
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setMovingBackward(true);

    d.integratorLinearSetMaximumSpeed(1.0);
    d.integratorLinearSetAcceleration(1.0);
    d.integratorLinearSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, 10.0);
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
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setMovingBackward(true);

    d.integratorLinearSetMaximumSpeed(0.5);
    d.integratorLinearSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, 5.0);
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
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setMovingDown(true);

    d.integratorLinearSetMaximumSpeed(1.0);
    d.integratorLinearSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, -10.0, 0.0);
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
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setMovingDown(true);

    d.integratorLinearSetMaximumSpeed(0.5);
    d.integratorLinearSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, -5.0, 0.0);
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
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setMovingForward(true);

    d.integratorLinearSetMaximumSpeed(1.0);
    d.integratorLinearSetAcceleration(1.0);
    d.integratorLinearSetDrag(0.0);
    d.integrate(1.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, -1.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }

    i.setMovingForward(false);
    d.integrate(1.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, -1.0);
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
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setMovingForward(true);

    d.integratorLinearSetMaximumSpeed(1.0);
    d.integratorLinearSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, -10.0);
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
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setMovingForward(true);

    d.integratorLinearSetMaximumSpeed(0.5);
    d.integratorLinearSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, -5.0);
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
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setMovingLeft(true);

    d.integratorLinearSetMaximumSpeed(1.0);
    d.integratorLinearSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(-10.0, 0.0, 0.0);
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
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setMovingLeft(true);

    d.integratorLinearSetMaximumSpeed(0.5);
    d.integratorLinearSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(-5.0, 0.0, 0.0);
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
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setMovingRight(true);

    d.integratorLinearSetMaximumSpeed(1.0);
    d.integratorLinearSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(10.0, 0.0, 0.0);
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
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setMovingRight(true);

    d.integratorLinearSetMaximumSpeed(0.5);
    d.integratorLinearSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(5.0, 0.0, 0.0);
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
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setMovingUp(true);

    d.integratorLinearSetMaximumSpeed(1.0);
    d.integratorLinearSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, 10.0, 0.0);
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
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);

    i.setMovingUp(true);

    d.integratorLinearSetMaximumSpeed(0.5);
    d.integratorLinearSetAcceleration(1.0);
    d.integrate(10.0);

    {
      final var expected = Vector3D.of(0.0, 5.0, 0.0);
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
    final var c = JCameraFPSStyle.newCamera();
    final var i = JCameraFPSStyleInput.newInput();
    final var d = this.newIntegrator(c, i);

    d.integrate(1.0);

    {
      final var expected = Vector3D.of(0.0, 0.0, 0.0);
      final var actual = c.cameraGetPosition();
      dumpVector("position expected", expected);
      dumpVector("position actual", actual);
      assertEquals(expected.x(), actual.x(), 0.00001);
      assertEquals(expected.y(), actual.y(), 0.00001);
      assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }
}
