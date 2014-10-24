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
import com.io7m.jcamera.JCameraFPSStyleAngularIntegrator;
import com.io7m.jcamera.JCameraFPSStyleAngularIntegratorType;
import com.io7m.jcamera.JCameraFPSStyleType;
import com.io7m.jcamera.JCameraInput;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorReadable3FType;

@SuppressWarnings("static-method") public final class JCameraFPSStyleAngularIntegratorTest
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

  @Test public void testDragHorizontal_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    c.cameraClampHorizontalDisable();

    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleAngularIntegratorType d =
      JCameraFPSStyleAngularIntegrator.newIntegrator(c, i);

    i.addRotationAroundHorizontal(1.0f);

    d.integratorAngularSetMaximumSpeedHorizontal((float) (Math.PI / 2.0f));
    d.integratorAngularSetAccelerationHorizontal((float) (Math.PI / 2.0f));
    d.integratorAngularSetDragHorizontal(0.0f);
    d.integrate(0.9999f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.001f);
    }

    d.integrate(0.9999f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.001f);
    }
  }

  @Test public void testDragVertical_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleAngularIntegratorType d =
      JCameraFPSStyleAngularIntegrator.newIntegrator(c, i);

    i.addRotationAroundVertical(1.0f);

    d.integratorAngularSetMaximumSpeedVertical((float) (Math.PI / 2.0f));
    d.integratorAngularSetAccelerationVertical((float) (Math.PI / 2.0f));
    d.integratorAngularSetDragVertical(0.0f);
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(-1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.001f);
    }

    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(-1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.001f);
    }
  }

  @Test public void testInput_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleAngularIntegratorType d =
      JCameraFPSStyleAngularIntegrator.newIntegrator(c, i);
    Assert.assertEquals(i, d.integratorGetInput());
  }

  @Test public void testRotatingRight_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleAngularIntegratorType d =
      JCameraFPSStyleAngularIntegrator.newIntegrator(c, i);

    i.addRotationAroundVertical(-1.0f);

    d.integratorAngularSetMaximumSpeedVertical((float) (Math.PI / 2.0f));
    d.integratorAngularSetAccelerationVertical((float) (Math.PI / 2.0f));
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    i.addRotationAroundVertical(-1.0f);
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 1.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    i.addRotationAroundVertical(-1.0f);
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(-1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    i.addRotationAroundVertical(-1.0f);
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public void testRotatingUp_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    c.cameraClampHorizontalDisable();

    final JCameraInput i = JCameraInput.newInput();
    final JCameraFPSStyleAngularIntegratorType d =
      JCameraFPSStyleAngularIntegrator.newIntegrator(c, i);

    i.addRotationAroundHorizontal(1.0f);

    d.integratorAngularSetMaximumSpeedHorizontal((float) (Math.PI / 2.0f));
    d.integratorAngularSetAccelerationHorizontal((float) (Math.PI / 2.0f));
    d.integrate(0.9999f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraFPSStyleAngularIntegratorTest.dumpVector(
        "right expected",
        expected);
      JCameraFPSStyleAngularIntegratorTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 1.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraFPSStyleAngularIntegratorTest
        .dumpVector("up expected", expected);
      JCameraFPSStyleAngularIntegratorTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }
}
