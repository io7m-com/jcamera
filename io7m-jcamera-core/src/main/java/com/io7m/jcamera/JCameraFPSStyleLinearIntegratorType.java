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

package com.io7m.jcamera;

/**
 * The type of integrators that integrate linear movement for {@link
 * JCameraFPSStyleType} cameras.
 */

public interface JCameraFPSStyleLinearIntegratorType extends
  JCameraIntegratorType
{
  /**
   * @return The camera that will be affected by the integrator
   */

  JCameraFPSStyleReadableType integratorGetCamera();

  /**
   * @return The input used to drive the integrator.
   */

  JCameraFPSStyleInput integratorGetInput();

  /**
   * Set the linear acceleration to <code>a</code>.
   *
   * @param a The linear acceleration.
   */

  void integratorLinearSetAcceleration(
    final float a);

  /**
   * Set the linear drag factor to <code>f</code>.
   *
   * @param f The linear drag factor.
   */

  void integratorLinearSetDrag(
    final float f);

  /**
   * Set the maximum linear movement speed to <code>s</code>.
   *
   * @param s The maximum linear speed.
   */

  void integratorLinearSetMaximumSpeed(
    final float s);
}
