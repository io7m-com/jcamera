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

import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;

/**
 * <p> The type of <i>first-person shooter</i> style flying cameras that allow
 * for: </p>
 *
 * <ul> <li>Rotation around a local X axis</li> <li>Rotation around a global Y
 * axis</li> <li>Movement <i>up</i> and <i>down</i> on the global Y axis</li>
 * <li>Movement <i>left</i> and <i>right</i> on the local X axis</li>
 * <li>Movement <i>forward</i> and <i>backward</i> based on the current
 * orientation on the global Y and local X axes</li> </ul>
 */

public interface JCameraFPSStyleType extends
  JCameraType,
  JCameraFPSStyleReadableType
{
  /**
   * Do not clamp rotations around the horizontal axis.
   *
   * @see #cameraClampHorizontalEnable(double, double)
   */

  void cameraClampHorizontalDisable();

  /**
   * Clamp the possible rotations around the horizontal axis to the given
   * bounds.
   *
   * @param min The minimum angle
   * @param max The maximum angle
   */

  void cameraClampHorizontalEnable(
    double min,
    double max);

  /**
   * <p>Move the camera <i>forward</i> {@code u} units iff {@code u}
   * is positive, or <i>backward</i> {@code u} units iff {@code u} is
   * negative.</p>
   *
   * <p>The <i>forward</i> direction, in this case, means the
   * current view direction.</p>
   *
   * @param u The units to move
   */

  void cameraMoveForward(
    double u);

  /**
   * <p>Move the camera <i>right</i> {@code u} units iff {@code u} is
   * positive, or <i>left</i> {@code u} units iff {@code u} is negative.</p>
   *
   * <p>The <i>right</i> direction, in this case, means the
   * direction perpendicular to the current view direction, {@code -π / 2}
   * radians around the global Y axis.</p>
   *
   * @param u The units to move
   */

  void cameraMoveRight(
    double u);

  /**
   * <p> Move the camera <i>up</i> {@code u} units iff {@code u} is
   * positive, or <i>down</i> {@code u} units iff {@code u} is negative.</p>
   *
   * <p>The <i>up</i> direction, in this case, means the
   * direction towards positive infinity on the global Y axis.</p>
   *
   * @param u The units to move
   */

  void cameraMoveUp(
    double u);

  /**
   * <p>Rotate by {@code r} radians around the local X axis.</p>
   *
   * <p>Note
   * that by convention, when looking towards negative infinity on an axis, a
   * positive rotation value results in a counter-clockwise rotation on the
   * axis.</p>
   *
   * <p>A human analogy would be that a positive value passed to
   * this function causes the camera to look <i>up</i> towards the sky. A
   * negative value causes the camera to look <i>down</i> towards the ground.
   * </p>
   *
   * @param r The radians to rotate
   *
   * @return {@code true} if the rotation has been clamped
   *
   * @see #cameraClampHorizontalEnable(double, double)
   */

  boolean cameraRotateAroundHorizontal(
    double r);

  /**
   * <p> Rotate by {@code r} radians around the global Y axis.</p>
   *
   * <p>
   * Note that by convention, when looking towards negative infinity on an axis,
   * a positive rotation value results in a counter-clockwise rotation on the
   * axis.</p>
   *
   * <p>A human analogy would be that a positive value passed to
   * this function causes the camera to turn <i>left</i>, while a negative value
   * causes the camera to look <i>right</i>.
   * </p>
   *
   * @param r The radians to rotate
   */

  void cameraRotateAroundVertical(
    double r);

  /**
   * Set the angle around the horizontal axis to {@code h}.
   *
   * @param h The angle.
   */

  void cameraSetAngleAroundHorizontal(
    double h);

  /**
   * Set the angle around the vertical axis to {@code v}.
   *
   * @param v The angle.
   */

  void cameraSetAngleAroundVertical(
    double v);

  /**
   * Set the position of the camera.
   *
   * @param v The position.
   */

  void cameraSetPosition(
    Vector3D v);

  /**
   * Set the position of the camera.
   *
   * @param x The x coordinate.
   * @param y The y coordinate.
   * @param z The z coordinate.
   */

  void cameraSetPosition3(
    double x,
    double y,
    double z);
}
