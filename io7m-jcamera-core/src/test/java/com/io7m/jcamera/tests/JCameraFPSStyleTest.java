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
import com.io7m.jcamera.JCameraFPSStyle.Context;
import com.io7m.jcamera.JCameraFPSStyleReadableType;
import com.io7m.jcamera.JCameraFPSStyleType;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorReadable3FType;

@SuppressWarnings("static-method") public final class JCameraFPSStyleTest
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

  @Test public void testDirectionsHorizontal()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final MatrixM4x4F m = new MatrixM4x4F();
    final Context ctx = new JCameraFPSStyle.Context();

    for (float index = 0.0f; index < 360.0f; index += 0.1f) {
      c.cameraRotateAroundHorizontal(0.1f);
      JCameraFPSStyle.cameraMakeViewMatrix(ctx, c, m);
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
  }

  @Test public void testDirectionsVertical()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final MatrixM4x4F m = new MatrixM4x4F();
    final Context ctx = new JCameraFPSStyle.Context();

    for (float index = 0.0f; index < 360.0f; index += 0.1f) {
      c.cameraRotateAroundVertical(0.1f);
      JCameraFPSStyle.cameraMakeViewMatrix(ctx, c, m);
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
  }
}
