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

import com.io7m.jcamera.JCameraContext;
import com.io7m.jcamera.JCameraFPSStyle;
import com.io7m.jcamera.JCameraFPSStyleReadableType;
import com.io7m.jcamera.JCameraFPSStyleSnapshot;
import com.io7m.jcamera.JCameraFPSStyleType;
import com.io7m.jtensors.MatrixI4x4F;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.parameterized.PMatrixI4x4F;
import com.io7m.jtensors.parameterized.PMatrixM4x4F;

@SuppressWarnings("static-method") public final class JCameraFPSStyleTest
{
  private interface ViewSpace
  {
    // Nothing
  }

  private interface WorldSpace
  {
    // Nothing
  }

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

  @Test public void testDirectionsHorizontal()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    for (float index = 0.0f; index < 360.0f; index += 0.1f) {
      c.cameraRotateAroundHorizontal(0.1f);
      this.compareSnapshot(c);
    }
  }

  @Test public void testDirectionsInitial()
  {
    final JCameraFPSStyleReadableType c = JCameraFPSStyle.newCamera();

    System.out.println("-- initial");

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleTest.dumpVector("forward expected", expected);
      JCameraFPSStyleTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraFPSStyleTest.dumpVector("right expected", expected);
      JCameraFPSStyleTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraFPSStyleTest.dumpVector("up expected", expected);
      JCameraFPSStyleTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsTurnDown45()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    System.out.println("-- down 45");

    c.cameraRotateAroundHorizontal((float) Math.toRadians(-45.0));

    {
      final VectorI3F expected = new VectorI3F(0.0f, -0.707107f, -0.707107f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleTest.dumpVector("forward expected", expected);
      JCameraFPSStyleTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraFPSStyleTest.dumpVector("right expected", expected);
      JCameraFPSStyleTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.707107f, -0.707107f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraFPSStyleTest.dumpVector("up expected", expected);
      JCameraFPSStyleTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsTurnDown90()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    System.out.println("-- down 90");

    c.cameraClampHorizontalDisable();
    c.cameraRotateAroundHorizontal((float) Math.toRadians(-89.999));

    {
      final VectorI3F expected = new VectorI3F(0.0f, -1.0f, -0.00001f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleTest.dumpVector("forward expected", expected);
      JCameraFPSStyleTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraFPSStyleTest.dumpVector("right expected", expected);
      JCameraFPSStyleTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.00001f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraFPSStyleTest.dumpVector("up expected", expected);
      JCameraFPSStyleTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsTurnLeft90()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    System.out.println("-- left 90");

    c.cameraRotateAroundVertical((float) Math.toRadians(90.0));

    {
      final VectorI3F expected = new VectorI3F(-1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleTest.dumpVector("forward expected", expected);
      JCameraFPSStyleTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraFPSStyleTest.dumpVector("right expected", expected);
      JCameraFPSStyleTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraFPSStyleTest.dumpVector("up expected", expected);
      JCameraFPSStyleTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsTurnRight180()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    System.out.println("-- right 180");

    c.cameraRotateAroundVertical((float) Math.toRadians(-180.0));

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 1.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleTest.dumpVector("forward expected", expected);
      JCameraFPSStyleTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(-1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraFPSStyleTest.dumpVector("right expected", expected);
      JCameraFPSStyleTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraFPSStyleTest.dumpVector("up expected", expected);
      JCameraFPSStyleTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsTurnRight270()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    System.out.println("-- right 270");

    c.cameraRotateAroundVertical((float) Math.toRadians(-270.0));

    {
      final VectorI3F expected = new VectorI3F(-1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleTest.dumpVector("forward expected", expected);
      JCameraFPSStyleTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraFPSStyleTest.dumpVector("right expected", expected);
      JCameraFPSStyleTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraFPSStyleTest.dumpVector("up expected", expected);
      JCameraFPSStyleTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsTurnRight90()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    System.out.println("-- right 90");

    c.cameraRotateAroundVertical((float) Math.toRadians(-90.0));

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleTest.dumpVector("forward expected", expected);
      JCameraFPSStyleTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 1.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraFPSStyleTest.dumpVector("right expected", expected);
      JCameraFPSStyleTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraFPSStyleTest.dumpVector("up expected", expected);
      JCameraFPSStyleTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsTurnUp45()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    System.out.println("-- up 45");

    c.cameraRotateAroundHorizontal((float) Math.toRadians(45.0));

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.707107f, -0.707107f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleTest.dumpVector("forward expected", expected);
      JCameraFPSStyleTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraFPSStyleTest.dumpVector("right expected", expected);
      JCameraFPSStyleTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.707107f, 0.707107f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraFPSStyleTest.dumpVector("up expected", expected);
      JCameraFPSStyleTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsTurnUp90()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    System.out.println("-- up 90");

    c.cameraClampHorizontalDisable();
    c.cameraRotateAroundHorizontal((float) Math.toRadians(89.999));

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, -0.00001f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraFPSStyleTest.dumpVector("forward expected", expected);
      JCameraFPSStyleTest.dumpVector("forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraFPSStyleTest.dumpVector("right expected", expected);
      JCameraFPSStyleTest.dumpVector("right actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.00001f, 1.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraFPSStyleTest.dumpVector("up expected", expected);
      JCameraFPSStyleTest.dumpVector("up actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testDirectionsVertical()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    for (float index = 0.0f; index < 360.0f; index += 0.1f) {
      c.cameraRotateAroundVertical(0.1f);
      this.compareSnapshot(c);
    }
  }

  @Test public void testMoveForwardInitial()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    System.out.println("-- move forward initial");

    c.cameraMoveForward(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -10.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleTest.dumpVector("move forward expected", expected);
      JCameraFPSStyleTest.dumpVector("move forward actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testStrafeInitial()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    System.out.println("-- strafe initial");

    c.cameraMoveRight(10.0f);

    {
      final VectorI3F expected = new VectorI3F(10.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleTest.dumpVector("strafe expected", expected);
      JCameraFPSStyleTest.dumpVector("strafe actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testStrafeLeft90_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    System.out.println("-- strafe left 90");

    c.cameraRotateAroundVertical((float) Math.toRadians(90));
    c.cameraMoveRight(10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -10.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleTest.dumpVector("strafe expected", expected);
      JCameraFPSStyleTest.dumpVector("strafe actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testStrafeLeft90_1()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    System.out.println("-- strafe left 90");

    c.cameraRotateAroundVertical((float) Math.toRadians(90));
    c.cameraMoveRight(-10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 10.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleTest.dumpVector("strafe expected", expected);
      JCameraFPSStyleTest.dumpVector("strafe actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testStrafeLeft90_2()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    System.out.println("-- strafe left 90");

    c.cameraRotateAroundVertical((float) Math.toRadians(90));
    c.cameraSetPosition3f(0.0f, 0.0f, -10.0f);
    c.cameraMoveRight(-10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleTest.dumpVector("strafe expected", expected);
      JCameraFPSStyleTest.dumpVector("strafe actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  @Test public void testStrafeLeft90_3()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    System.out.println("-- strafe left 90");

    c.cameraRotateAroundVertical((float) Math.toRadians(90));
    c.cameraSetPosition(new VectorI3F(0.0f, 0.0f, -10.0f));
    c.cameraMoveRight(-10.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraFPSStyleTest.dumpVector("strafe expected", expected);
      JCameraFPSStyleTest.dumpVector("strafe actual", actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    this.compareSnapshot(c);
  }

  private void compareVector(
    final VectorReadable3FType a,
    final VectorReadable3FType b)
  {
    Assert.assertEquals(a.getXF(), b.getXF(), 0.0);
    Assert.assertEquals(a.getYF(), b.getYF(), 0.0);
    Assert.assertEquals(a.getZF(), b.getZF(), 0.0);
  }

  private void compareSnapshot(
    final JCameraFPSStyleReadableType c)
  {
    final JCameraFPSStyleSnapshot snap = c.cameraMakeSnapshot();
    Assert.assertEquals(
      snap.cameraGetAngleAroundHorizontal(),
      c.cameraGetAngleAroundHorizontal(),
      0.0);
    Assert.assertEquals(
      snap.cameraGetAngleAroundVertical(),
      c.cameraGetAngleAroundVertical(),
      0.0);
    this.compareVector(snap.cameraGetForward(), c.cameraGetForward());
    this.compareVector(snap.cameraGetRight(), c.cameraGetRight());
    this.compareVector(snap.cameraGetUp(), c.cameraGetUp());
    this.compareVector(snap.cameraGetPosition(), c.cameraGetPosition());

    final MatrixM4x4F m = new MatrixM4x4F();
    final PMatrixM4x4F<WorldSpace, ViewSpace> pm =
      new PMatrixM4x4F<WorldSpace, ViewSpace>();
    final MatrixM4x4F snap_m = new MatrixM4x4F();
    final PMatrixM4x4F<WorldSpace, ViewSpace> snap_pm =
      new PMatrixM4x4F<WorldSpace, ViewSpace>();
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

    final JCameraFPSStyleSnapshot snap2 = c.cameraMakeSnapshot();
    final JCameraFPSStyleSnapshot snap3 = snap.cameraMakeSnapshot();

    Assert.assertEquals(snap, snap2);
    Assert.assertEquals(snap2, snap3);
    Assert.assertEquals(snap, snap3);
  }
}
