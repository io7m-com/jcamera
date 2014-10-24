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

import com.io7m.jcamera.JCameraFPSStyle;
import com.io7m.jcamera.JCameraFPSStyleLinearIntegrator;
import com.io7m.jcamera.JCameraFPSStyleLinearIntegratorType;
import com.io7m.jcamera.JCameraFPSStyleType;
import com.io7m.jcamera.JCameraInput;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorReadable3FType;

@SuppressWarnings("static-method") public final class JCameraFPSStyleLinearIntegratorTest
{
  private static void dumpVector(
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

  @Test public void testInput_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);
    Assert.assertEquals(i, d.integratorGetInput());
  }

  @Test public void testMovingBackward_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);

    i.setMovingBackward(true);

    d.integratorLinearSetMaximumSpeed(1.0f);
    d.integratorLinearSetAcceleration(1.0f);
    d.integratorLinearSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 10.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public void testMovingBackward_1()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);

    i.setMovingBackward(true);

    d.integratorLinearSetMaximumSpeed(0.5f);
    d.integratorLinearSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 5.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public void testMovingDown_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);

    i.setMovingDown(true);

    d.integratorLinearSetMaximumSpeed(1.0f);
    d.integratorLinearSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, -10.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public void testMovingDown_1()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);

    i.setMovingDown(true);

    d.integratorLinearSetMaximumSpeed(0.5f);
    d.integratorLinearSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, -5.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public void testMovingDrag_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);

    i.setMovingForward(true);

    d.integratorLinearSetMaximumSpeed(1.0f);
    d.integratorLinearSetAcceleration(1.0f);
    d.integratorLinearSetDrag(0.0f);
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    i.setMovingForward(false);
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public void testMovingForward_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);

    i.setMovingForward(true);

    d.integratorLinearSetMaximumSpeed(1.0f);
    d.integratorLinearSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -10.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public void testMovingForward_1()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);

    i.setMovingForward(true);

    d.integratorLinearSetMaximumSpeed(0.5f);
    d.integratorLinearSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -5.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public void testMovingLeft_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);

    i.setMovingLeft(true);

    d.integratorLinearSetMaximumSpeed(1.0f);
    d.integratorLinearSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(-10.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public void testMovingLeft_1()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);

    i.setMovingLeft(true);

    d.integratorLinearSetMaximumSpeed(0.5f);
    d.integratorLinearSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(-5.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public void testMovingRight_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);

    i.setMovingRight(true);

    d.integratorLinearSetMaximumSpeed(1.0f);
    d.integratorLinearSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(10.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public void testMovingRight_1()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);

    i.setMovingRight(true);

    d.integratorLinearSetMaximumSpeed(0.5f);
    d.integratorLinearSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(5.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public void testMovingUp_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);

    i.setMovingUp(true);

    d.integratorLinearSetMaximumSpeed(1.0f);
    d.integratorLinearSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 10.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public void testMovingUp_1()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);

    i.setMovingUp(true);

    d.integratorLinearSetMaximumSpeed(0.5f);
    d.integratorLinearSetAcceleration(1.0f);
    d.integrate(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 5.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public void testStatic()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleLinearIntegratorType d =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i);

    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position expected",
        expected);
      JCameraFPSStyleLinearIntegratorTest.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }
}
