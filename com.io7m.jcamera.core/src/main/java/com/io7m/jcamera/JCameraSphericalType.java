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

package com.io7m.jcamera;

import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;

/**
 * <p> The type of <i>spherical</i> style flying cameras that point towards a
 * given point {@code p} and allow for: </p> <ul> <li>Orbiting horizontally
 * around {@code p}</li> <li>Orbiting vertically around {@code p}</li>
 * </ul>
 */

public interface JCameraSphericalType extends
  JCameraType,
  JCameraSphericalReadableType
{
  /**
   * Do not clamp rotations around the horizontal axis.
   *
   * @see #cameraClampInclineEnable(double, double)
   */

  void cameraClampInclineDisable();

  /**
   * Clamp the possible rotations around the horizontal axis to the given
   * bounds.
   *
   * @param min The minimum angle
   * @param max The maximum angle
   */

  void cameraClampInclineEnable(
    double min,
    double max);

  /**
   * Do not clamp the length of the radius.
   *
   * @see #cameraClampRadiusEnable(double, double)
   */

  void cameraClampRadiusDisable();

  /**
   * Clamp the length of the radius to the given bounds.
   *
   * @param min The minimum length
   * @param max The maximum length
   */

  void cameraClampRadiusEnable(
    double min,
    double max);

  /**
   * <p> Move the target point of the camera <i>forward</i> {@code u} units
   * iff {@code u} is positive, or <i>backward</i> {@code u} units iff {@code u}
   * is negative. </p> <p> The <i>forward</i> direction, in this case, means the
   * current view direction projected onto the X/Z plane. This means that the
   * camera will not move along the Y axis. </p>
   *
   * @param u The units to move
   */

  void cameraMoveTargetForwardOnXZ(
    double u);

  /**
   * <p> Move the target point of the camera <i>right</i> {@code u} units
   * iff {@code u} is positive, or <i>left</i> {@code u} units iff {@code u} is
   * negative. </p> <p> The <i>right</i> direction, in this case, means the
   * direction perpendicular to the current view direction, {@code -π / 2}
   * radians around the global Y axis. </p>
   *
   * @param u The units to move
   */

  void cameraMoveTargetRight(
    double u);

  /**
   * <p> Move the target point of the camera <i>up</i> {@code u} units iff
   * {@code u} is positive, or <i>down</i> {@code u} units iff {@code u} is
   * negative. </p> <p> The <i>up</i> direction, in this case, means the
   * direction towards positive infinity on the global Y axis. </p>
   *
   * @param u The units to move
   */

  void cameraMoveTargetUp(
    double u);

  /**
   * @param r The radians to rotate
   */

  void cameraOrbitHeading(
    double r);

  /**
   * @param r The radians to orbit
   *
   * @return {@code true} if the rotation has been clamped
   *
   * @see #cameraClampInclineEnable(double, double)
   */

  boolean cameraOrbitIncline(
    double r);

  /**
   * Set the heading angle to {@code a}.
   *
   * @param a The angle.
   */

  void cameraSetAngleHeading(
    double a);

  /**
   * Set the incline angle to {@code a}.
   *
   * @param a The angle.
   */

  void cameraSetAngleIncline(
    double a);

  /**
   * Set the position of the target point of camera.
   *
   * @param v The position.
   */

  void cameraSetTargetPosition(
    Vector3D v);

  /**
   * Set the position of the target point of camera.
   *
   * @param x The x coordinate.
   * @param y The y coordinate.
   * @param z The z coordinate.
   */

  void cameraSetTargetPosition3(
    double x,
    double y,
    double z);

  /**
   * Set the zoom (or <i>radius</i>) of the camera to {@code r}.
   *
   * @param r The camera radius.
   */

  void cameraSetZoom(
    double r);

  /**
   * Zoom in the camera by reducing the radius of the sphere.
   *
   * @param r The amount by which to reduce the radius
   *
   * @return {@code true} if the radius has been clamped
   *
   * @see #cameraClampRadiusEnable(double, double)
   */

  boolean cameraZoomIn(
    double r);

  /**
   * Zoom out the camera by increasing the radius of the sphere.
   *
   * @param r The amount by which to increase the radius
   *
   * @return {@code true} if the radius has been clamped
   *
   * @see #cameraClampRadiusEnable(double, double)
   */

  boolean cameraZoomOut(
    double r);
}
