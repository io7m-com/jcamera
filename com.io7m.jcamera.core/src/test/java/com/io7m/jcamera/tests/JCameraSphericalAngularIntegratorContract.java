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
import com.io7m.jcamera.JCameraSphericalAngularIntegratorType;
import com.io7m.jcamera.JCameraSphericalInput;
import com.io7m.jcamera.JCameraSphericalInputType;
import com.io7m.jcamera.JCameraSphericalType;
import com.io7m.jnull.NonNull;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import org.junit.Assert;
import org.junit.Test;

public abstract class JCameraSphericalAngularIntegratorContract
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

  abstract
  @NonNull
  JCameraSphericalAngularIntegratorType newIntegrator(
    final @NonNull JCameraSphericalType c,
    final @NonNull JCameraSphericalInputType i);

  @Test
  public final void testCamera_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType d = this.newIntegrator(c, i);
    Assert.assertEquals(c, d.integratorGetCamera());
  }

  @Test
  public final void testInput_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType d = this.newIntegrator(c, i);
    Assert.assertEquals(i, d.integratorGetInput());
  }

  @Test
  public final void testOrbitHeadingNegative_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType d = this.newIntegrator(c, i);

    i.setOrbitHeadingNegative(true);

    d.integratorAngularOrbitHeadingSetDrag(0.0);
    d.integratorAngularOrbitHeadingSetAcceleration(Math.PI / 2.0);
    d.integratorAngularOrbitHeadingSetMaximumSpeed(Math.PI / 2.0);
    d.integrate(1.0);

    {
      final Vector3D expected = Vector3D.of(-8.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetPosition();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, 1.0);
      final Vector3D actual = c.cameraGetRight();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 1.0, 0.0);
      final Vector3D actual = c.cameraGetUp();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testOrbitHeadingPositive_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType d = this.newIntegrator(c, i);

    i.setOrbitHeadingPositive(true);

    d.integratorAngularOrbitHeadingSetDrag(0.0);
    d.integratorAngularOrbitHeadingSetAcceleration(Math.PI / 2.0);
    d.integratorAngularOrbitHeadingSetMaximumSpeed(Math.PI / 2.0);
    d.integrate(1.0);

    {
      final Vector3D expected = Vector3D.of(8.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetPosition();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(-1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, -1.0);
      final Vector3D actual = c.cameraGetRight();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 1.0, 0.0);
      final Vector3D actual = c.cameraGetUp();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testOrbitInclineNegative_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType d = this.newIntegrator(c, i);

    c.cameraClampInclineDisable();

    i.setOrbitInclineNegative(true);

    d.integratorAngularOrbitInclineSetDrag(0.0);
    d.integratorAngularOrbitInclineSetAcceleration(Math.PI / 2.0);
    d.integratorAngularOrbitInclineSetMaximumSpeed(Math.PI / 2.0);
    d.integrate(1.0);

    {
      final Vector3D expected = Vector3D.of(0.0, -8.0, 0.0);
      final Vector3D actual = c.cameraGetPosition();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 1.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetRight();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, 1.0);
      final Vector3D actual = c.cameraGetUp();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testOrbitInclinePositive_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType d = this.newIntegrator(c, i);

    i.setOrbitInclinePositive(true);

    c.cameraClampInclineDisable();

    d.integratorAngularOrbitInclineSetDrag(0.0);
    d.integratorAngularOrbitInclineSetAcceleration(Math.PI / 2.0);
    d.integratorAngularOrbitInclineSetMaximumSpeed(Math.PI / 2.0);
    d.integrate(1.0);

    {
      final Vector3D expected = Vector3D.of(0.0, 8.0, 0.0);
      final Vector3D actual = c.cameraGetPosition();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, -1.0, 0.0);
      final Vector3D actual = c.cameraGetForward();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(1.0, 0.0, 0.0);
      final Vector3D actual = c.cameraGetRight();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, -1.0);
      final Vector3D actual = c.cameraGetUp();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }

  @Test
  public final void testStatic()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType d = this.newIntegrator(c, i);

    d.integrate(1.0);

    {
      final Vector3D expected = Vector3D.of(0.0, 0.0, 8.0);
      final Vector3D actual = c.cameraGetPosition();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.x(), actual.x(), 0.00001);
      Assert.assertEquals(expected.y(), actual.y(), 0.00001);
      Assert.assertEquals(expected.z(), actual.z(), 0.00001);
    }
  }
}
