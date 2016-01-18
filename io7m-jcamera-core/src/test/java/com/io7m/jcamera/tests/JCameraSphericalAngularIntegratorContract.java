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

import com.io7m.jcamera.JCameraSphericalInputType;
import org.junit.Assert;
import org.junit.Test;

import com.io7m.jcamera.JCameraSpherical;
import com.io7m.jcamera.JCameraSphericalAngularIntegratorType;
import com.io7m.jcamera.JCameraSphericalInput;
import com.io7m.jcamera.JCameraSphericalType;
import com.io7m.jnull.NonNull;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorReadable3FType;

public abstract class JCameraSphericalAngularIntegratorContract
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

  abstract @NonNull JCameraSphericalAngularIntegratorType newIntegrator(
    final @NonNull JCameraSphericalType c,
    final @NonNull JCameraSphericalInputType i);

  @Test public final void testCamera_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType d = this.newIntegrator(c, i);
    Assert.assertEquals(c, d.integratorGetCamera());
  }

  @Test public final void testInput_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType d = this.newIntegrator(c, i);
    Assert.assertEquals(i, d.integratorGetInput());
  }

  @Test public final void testOrbitHeadingNegative_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType d = this.newIntegrator(c, i);

    i.setOrbitHeadingNegative(true);

    d.integratorAngularOrbitHeadingSetDrag(0.0f);
    d.integratorAngularOrbitHeadingSetAcceleration((float) (Math.PI / 2.0f));
    d.integratorAngularOrbitHeadingSetMaximumSpeed((float) (Math.PI / 2.0f));
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(-8.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 1.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testOrbitHeadingPositive_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType d = this.newIntegrator(c, i);

    i.setOrbitHeadingPositive(true);

    d.integratorAngularOrbitHeadingSetDrag(0.0f);
    d.integratorAngularOrbitHeadingSetAcceleration((float) (Math.PI / 2.0f));
    d.integratorAngularOrbitHeadingSetMaximumSpeed((float) (Math.PI / 2.0f));
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(8.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(-1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testOrbitInclineNegative_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType d = this.newIntegrator(c, i);

    c.cameraClampInclineDisable();

    i.setOrbitInclineNegative(true);

    d.integratorAngularOrbitInclineSetDrag(0.0f);
    d.integratorAngularOrbitInclineSetAcceleration((float) (Math.PI / 2.0f));
    d.integratorAngularOrbitInclineSetMaximumSpeed((float) (Math.PI / 2.0f));
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, -8.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 1.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testOrbitInclinePositive_0()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType d = this.newIntegrator(c, i);

    i.setOrbitInclinePositive(true);

    c.cameraClampInclineDisable();

    d.integratorAngularOrbitInclineSetDrag(0.0f);
    d.integratorAngularOrbitInclineSetAcceleration((float) (Math.PI / 2.0f));
    d.integratorAngularOrbitInclineSetMaximumSpeed((float) (Math.PI / 2.0f));
    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 8.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, -1.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetForward();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "forward actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(1.0f, 0.0f, 0.0f);
      final VectorReadable3FType actual = c.cameraGetRight();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "right actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, -1.0f);
      final VectorReadable3FType actual = c.cameraGetUp();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "up actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }

  @Test public final void testStatic()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();
    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType d = this.newIntegrator(c, i);

    d.integrate(1.0f);

    {
      final VectorI3F expected = new VectorI3F(0.0f, 0.0f, 8.0f);
      final VectorReadable3FType actual = c.cameraGetPosition();
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position expected",
        expected);
      JCameraSphericalAngularIntegratorContract.dumpVector(
        "position actual",
        actual);
      Assert.assertEquals(expected.getXF(), actual.getXF(), 0.00001f);
      Assert.assertEquals(expected.getYF(), actual.getYF(), 0.00001f);
      Assert.assertEquals(expected.getZF(), actual.getZF(), 0.00001f);
    }
  }
}
