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
import com.io7m.jcamera.JCameraSphericalAngularIntegrator;
import com.io7m.jcamera.JCameraSphericalAngularIntegratorType;
import com.io7m.jcamera.JCameraSphericalInput;
import com.io7m.jcamera.JCameraSphericalInputType;
import com.io7m.jcamera.JCameraSphericalIntegrator;
import com.io7m.jcamera.JCameraSphericalLinearIntegrator;
import com.io7m.jcamera.JCameraSphericalLinearIntegratorType;
import com.io7m.jcamera.JCameraSphericalType;
import org.junit.Test;

@SuppressWarnings("static-method")
public final class JCameraSphericalIntegratorLinearTest extends
  JCameraSphericalLinearIntegratorContract
{
  @Override
  JCameraSphericalLinearIntegratorType newIntegrator(
    final JCameraSphericalType c,
    final JCameraSphericalInputType i)
  {
    return JCameraSphericalIntegrator.newIntegrator(c, i);
  }

  @Test(expected = IllegalArgumentException.class)
  public void
  testCameraIncorrect()
  {
    final JCameraSphericalType c0 = JCameraSpherical.newCamera();
    final JCameraSphericalType c1 = JCameraSpherical.newCamera();

    final JCameraSphericalInputType i = JCameraSphericalInput.newInput();
    final JCameraSphericalAngularIntegratorType ai =
      JCameraSphericalAngularIntegrator.newIntegrator(c0, i);
    final JCameraSphericalLinearIntegratorType li =
      JCameraSphericalLinearIntegrator.newIntegrator(c1, i);

    JCameraSphericalIntegrator.newIntegratorWith(ai, li);
  }

  @Test(expected = IllegalArgumentException.class)
  public void
  testInputIncorrect()
  {
    final JCameraSphericalType c = JCameraSpherical.newCamera();

    final JCameraSphericalInputType i0 = JCameraSphericalInput.newInput();
    final JCameraSphericalInputType i1 = JCameraSphericalInput.newInput();

    final JCameraSphericalAngularIntegratorType ai =
      JCameraSphericalAngularIntegrator.newIntegrator(c, i0);
    final JCameraSphericalLinearIntegratorType li =
      JCameraSphericalLinearIntegrator.newIntegrator(c, i1);

    JCameraSphericalIntegrator.newIntegratorWith(ai, li);
  }
}
