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

package com.io7m.jcamera;

/**
 * The type of integrators that integrate angular movement for {@link
 * JCameraFPSStyleType} cameras.
 */

public interface JCameraFPSStyleAngularIntegratorType extends
  JCameraIntegratorType
{
  /**
   * Set the acceleration around the horizontal axis to {@code a}.
   *
   * @param a The acceleration value.
   */

  void integratorAngularSetAccelerationHorizontal(
    double a);

  /**
   * Set the acceleration around the vertical axis to {@code a}.
   *
   * @param a The acceleration value.
   */

  void integratorAngularSetAccelerationVertical(
    double a);

  /**
   * Set the drag around the horizontal axis to {@code a}.
   *
   * @param d The drag value.
   */

  void integratorAngularSetDragHorizontal(
    double d);

  /**
   * Set the drag around the vertical axis to {@code a}.
   *
   * @param d The drag value.
   */

  void integratorAngularSetDragVertical(
    double d);

  /**
   * Set the maximum rotation speed around the horizontal axis to {@code s}.
   *
   * @param s The maximum rotation speed.
   */

  void integratorAngularSetMaximumSpeedHorizontal(
    double s);

  /**
   * Set the maximum rotation speed around the vertical axis to {@code s} .
   *
   * @param s The maximum rotation speed.
   */

  void integratorAngularSetMaximumSpeedVertical(
    double s);

  /**
   * @return The camera that will be affected by the integrator
   */

  JCameraFPSStyleReadableType integratorGetCamera();

  /**
   * @return The input used to drive the integrator.
   */

  JCameraFPSStyleInputType integratorGetInput();
}
