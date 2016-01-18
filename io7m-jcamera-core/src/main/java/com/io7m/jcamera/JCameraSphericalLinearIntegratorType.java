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
 * JCameraSphericalType} cameras.
 */

public interface JCameraSphericalLinearIntegratorType extends
  JCameraIntegratorType
{
  /**
   * @return The camera that will be affected by the integrator
   */

  JCameraSphericalReadableType integratorGetCamera();

  /**
   * @return The input used to drive the integrator.
   */

  JCameraSphericalInput integratorGetInput();

  /**
   * Set the linear acceleration (of the camera target) to {@code a}.
   *
   * @param a The linear acceleration.
   */

  void integratorLinearTargetSetAcceleration(
    final float a);

  /**
   * Set the linear drag factor (of the camera target) to {@code f}.
   *
   * @param f The linear drag factor.
   */

  void integratorLinearTargetSetDrag(
    final float f);

  /**
   * Set the maximum linear movement speed (of the camera target) to
   * {@code s}.
   *
   * @param s The maximum linear speed.
   */

  void integratorLinearTargetSetMaximumSpeed(
    final float s);

  /**
   * Set the linear acceleration (of the camera zoom) to {@code a}.
   *
   * @param a The linear acceleration.
   */

  void integratorLinearZoomSetAcceleration(
    final float a);

  /**
   * Set the linear drag factor (of the camera zoom) to {@code f}.
   *
   * @param f The linear drag factor.
   */

  void integratorLinearZoomSetDrag(
    final float f);

  /**
   * Set the maximum linear movement speed (of the camera zoom) to
   * {@code s}.
   *
   * @param s The maximum linear speed.
   */

  void integratorLinearZoomSetMaximumSpeed(
    final float s);
}
