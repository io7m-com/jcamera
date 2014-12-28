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

import org.junit.Test;

import com.io7m.jcamera.JCameraFPSStyle;
import com.io7m.jcamera.JCameraFPSStyleAngularIntegrator;
import com.io7m.jcamera.JCameraFPSStyleAngularIntegratorType;
import com.io7m.jcamera.JCameraFPSStyleInput;
import com.io7m.jcamera.JCameraFPSStyleIntegrator;
import com.io7m.jcamera.JCameraFPSStyleLinearIntegrator;
import com.io7m.jcamera.JCameraFPSStyleLinearIntegratorType;
import com.io7m.jcamera.JCameraFPSStyleType;

@SuppressWarnings("static-method") public final class JCameraFPSStyleIntegratorLinearTest extends
  JCameraFPSStyleLinearIntegratorContract
{
  @Override JCameraFPSStyleLinearIntegratorType newIntegrator(
    final JCameraFPSStyleType c,
    final JCameraFPSStyleInput i)
  {
    return JCameraFPSStyleIntegrator.newIntegrator(c, i);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testCameraIncorrect()
  {
    final JCameraFPSStyleType c0 = JCameraFPSStyle.newCamera();
    final JCameraFPSStyleType c1 = JCameraFPSStyle.newCamera();

    final JCameraFPSStyleInput i = JCameraFPSStyleInput.newInput();
    final JCameraFPSStyleAngularIntegratorType ai =
      JCameraFPSStyleAngularIntegrator.newIntegrator(c0, i);
    final JCameraFPSStyleLinearIntegratorType li =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c1, i);

    JCameraFPSStyleIntegrator.newIntegratorWith(ai, li);
  }

  @Test(expected = IllegalArgumentException.class) public
    void
    testInputIncorrect()
  {
    final JCameraFPSStyleType c = JCameraFPSStyle.newCamera();

    final JCameraFPSStyleInput i0 = JCameraFPSStyleInput.newInput();
    final JCameraFPSStyleInput i1 = JCameraFPSStyleInput.newInput();

    final JCameraFPSStyleAngularIntegratorType ai =
      JCameraFPSStyleAngularIntegrator.newIntegrator(c, i0);
    final JCameraFPSStyleLinearIntegratorType li =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i1);

    JCameraFPSStyleIntegrator.newIntegratorWith(ai, li);
  }
}
