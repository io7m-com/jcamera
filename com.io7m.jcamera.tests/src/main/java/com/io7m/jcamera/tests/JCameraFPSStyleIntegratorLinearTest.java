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
import com.io7m.jcamera.JCameraFPSStyleAngularIntegrator;
import com.io7m.jcamera.JCameraFPSStyleInput;
import com.io7m.jcamera.JCameraFPSStyleInputType;
import com.io7m.jcamera.JCameraFPSStyleIntegrator;
import com.io7m.jcamera.JCameraFPSStyleLinearIntegrator;
import com.io7m.jcamera.JCameraFPSStyleLinearIntegratorType;
import com.io7m.jcamera.JCameraFPSStyleType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public final class JCameraFPSStyleIntegratorLinearTest extends
  JCameraFPSStyleLinearIntegratorContract
{
  @Override
  JCameraFPSStyleLinearIntegratorType newIntegrator(
    final JCameraFPSStyleType c,
    final JCameraFPSStyleInputType i)
  {
    return JCameraFPSStyleIntegrator.newIntegrator(c, i);
  }

  @Test
  public void
  testCameraIncorrect()
  {
    final var c0 = JCameraFPSStyle.newCamera();
    final var c1 = JCameraFPSStyle.newCamera();

    final var i = JCameraFPSStyleInput.newInput();
    final var ai =
      JCameraFPSStyleAngularIntegrator.newIntegrator(c0, i);
    final var li =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c1, i);

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      JCameraFPSStyleIntegrator.newIntegratorWith(ai, li);
    });
  }

  @Test
  public void
  testInputIncorrect()
  {
    final var c = JCameraFPSStyle.newCamera();

    final var i0 = JCameraFPSStyleInput.newInput();
    final var i1 = JCameraFPSStyleInput.newInput();

    final var ai =
      JCameraFPSStyleAngularIntegrator.newIntegrator(c, i0);
    final var li =
      JCameraFPSStyleLinearIntegrator.newIntegrator(c, i1);

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      JCameraFPSStyleIntegrator.newIntegratorWith(ai, li);
    });
  }
}
