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
 * JCameraSphericalType} cameras.
 */

public interface JCameraSphericalAngularIntegratorType extends
  JCameraIntegratorType
{
  /**
   * Set the acceleration for heading orbiting to {@code a}.
   *
   * @param a The acceleration value.
   */

  void integratorAngularOrbitHeadingSetAcceleration(
    double a);

  /**
   * Set the drag for heading orbiting to {@code a}.
   *
   * @param d The drag value.
   */

  void integratorAngularOrbitHeadingSetDrag(
    double d);

  /**
   * Set the maximum rotation speed for heading orbiting to {@code s} .
   *
   * @param s The maximum rotation speed.
   */

  void integratorAngularOrbitHeadingSetMaximumSpeed(
    double s);

  /**
   * Set the acceleration for incline orbiting to {@code a}.
   *
   * @param a The acceleration value.
   */

  void integratorAngularOrbitInclineSetAcceleration(
    double a);

  /**
   * Set the drag for incline orbiting to {@code a}.
   *
   * @param d The drag value.
   */

  void integratorAngularOrbitInclineSetDrag(
    double d);

  /**
   * Set the maximum rotation speed for incline orbiting to {@code s}.
   *
   * @param s The maximum rotation speed.
   */

  void integratorAngularOrbitInclineSetMaximumSpeed(
    double s);

  /**
   * @return The camera that will be affected by the integrator
   */

  JCameraSphericalReadableType integratorGetCamera();

  /**
   * @return The input used to drive the integrator.
   */

  JCameraSphericalInputType integratorGetInput();
}
