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

import com.io7m.jcamera.JCameraFPSStyle;
import com.io7m.jcamera.JCameraFPSStyleAngularIntegratorType;
import com.io7m.jcamera.JCameraFPSStyleInput;
import com.io7m.jcamera.JCameraFPSStyleInputType;
import com.io7m.jcamera.JCameraFPSStyleType;
import com.io7m.jnull.NonNull;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import org.junit.Assert;
import org.junit.Test;

public abstract class JCameraFPSStyleAngularIntegratorContract
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

  abstract JCameraFPSStyleAngularIntegratorType newIntegrator(
    final @NonNull JCameraFPSStyleType c,
    final @NonNull JCameraFPSStyleInputType i);

  @Test
  public final void testCamera_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraFPSStyleInputType i = JCameraFPSStyleInput.newInput();
    final JCameraFPSStyleAngularIntegratorType d = this.newIntegrator(c, i);
    Assert.assertEquals(c, d.integratorGetCamera());
  }

  @Test
  public final void testDragHorizontal_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    c.cameraClampHorizontalDisable();

    final JCameraFPSStyleInputType i = JCameraFPSStyleInput.newInput();
    final JCameraFPSStyleAngularIntegratorType d = this.newIntegrator(c, i);

    i.addRotationAroundHorizontal(1.0);

    d.integratorAngularSetMaximumSpeedHorizontal(Math.PI / 2.0);
    d.integratorAngularSetAccelerationHorizontal(Math.PI / 2.0);
    d.integratorAngularSetDragHorizontal(0.0);
    d.integrate(0.9999);

    {
      final Vector3D expected = Vector3D.of(0.0, 1.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.001);
    }

    d.integrate(0.9999);

    {
      final Vector3D expected = Vector3D.of(0.0, 1.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.001);
    }
  }

  @Test
  public final void testDragVertical_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    final JCameraFPSStyleInputType i = JCameraFPSStyleInput.newInput();
    final JCameraFPSStyleAngularIntegratorType d = this.newIntegrator(c, i);

    i.addRotationAroundVertical(1.0);

    d.integratorAngularSetMaximumSpeedVertical(Math.PI / 2.0);
    d.integratorAngularSetAccelerationVertical(Math.PI / 2.0);
    d.integratorAngularSetDragVertical(0.0);
    d.integrate(1.0);

    {
      final Vector3D expected = Vector3D.of(-1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.001);
    }

    d.integrate(1.0);

    {
      final Vector3D expected = Vector3D.of(-1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.001);
    }
  }

  @Test
  public final void testInput_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraFPSStyleInputType i = JCameraFPSStyleInput.newInput();
    final JCameraFPSStyleAngularIntegratorType d = this.newIntegrator(c, i);
    Assert.assertEquals(i, d.integratorGetInput());
  }

  @Test
  public final void testRotatingRight_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    final JCameraFPSStyleInputType i = JCameraFPSStyleInput.newInput();
    final JCameraFPSStyleAngularIntegratorType d = this.newIntegrator(c, i);

    i.addRotationAroundVertical(-1.0);

    d.integratorAngularSetMaximumSpeedVertical(Math.PI / 2.0);
    d.integratorAngularSetAccelerationVertical(Math.PI / 2.0);
    d.integrate(1.0);

    {
      final Vector3D expected = Vector3D.of(1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    i.addRotationAroundVertical(-1.0);
    d.integrate(1.0);

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, 1.0);
      final Vector3D actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    i.addRotationAroundVertical(-1.0);
    d.integrate(1.0);

    {
      final Vector3D expected = Vector3D.of(-1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    i.addRotationAroundVertical(-1.0);
    d.integrate(1.0);

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, -1.0);
      final Vector3D actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testRotatingUp_0()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();
    c.cameraClampHorizontalDisable();

    final JCameraFPSStyleInputType i = JCameraFPSStyleInput.newInput();
    final JCameraFPSStyleAngularIntegratorType d = this.newIntegrator(c, i);

    i.addRotationAroundHorizontal(1.0);

    d.integratorAngularSetMaximumSpeedHorizontal(Math.PI / 2.0);
    d.integratorAngularSetAccelerationHorizontal(Math.PI / 2.0);
    d.integrate(0.9999);

    {
      final Vector3D expected = Vector3D.of(0.0, 1.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.001);
    }

    {
      final Vector3D expected = Vector3D.of(1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetRight();
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "right expected",
        expected);
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "right actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, 1.0);
      final Vector3D actual = c.cameraGetUp();
      JCameraFPSStyleAngularIntegratorContract.dumpVector(
        "up expected",
        expected);
      JCameraFPSStyleAngularIntegratorContract
        .dumpVector("up actual", actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }
}
