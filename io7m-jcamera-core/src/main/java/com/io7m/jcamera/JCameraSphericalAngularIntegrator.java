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

import com.io7m.jequality.annotations.EqualityReference;
import com.io7m.jnull.NullCheck;
import com.io7m.jranges.RangeCheck;

/**
 * The default implementation of {@link JCameraSphericalAngularIntegratorType}
 * .
 */

@EqualityReference
public final class JCameraSphericalAngularIntegrator implements
  JCameraSphericalAngularIntegratorType
{
  private final JCameraSphericalType  camera;
  private final JCameraSphericalInput input;
  private       float                 acceleration_heading;
  private       float                 acceleration_incline;
  private       float                 drag_heading;
  private       float                 drag_incline;
  private       float                 maximum_speed_heading;
  private       float                 maximum_speed_incline;
  private       float                 speed_heading;
  private       float                 speed_incline;

  private JCameraSphericalAngularIntegrator(
    final JCameraSphericalType in_camera,
    final JCameraSphericalInput in_input)
  {
    this.camera = NullCheck.notNull(in_camera, "Camera");
    this.input = NullCheck.notNull(in_input, "Input");

    this.maximum_speed_heading = (float) (2.0 * Math.PI);
    this.maximum_speed_incline = (float) (2.0 * Math.PI);
    this.acceleration_heading = this.maximum_speed_heading / 2.0f;
    this.acceleration_incline = this.maximum_speed_incline / 2.0f;
    this.drag_incline = 0.05f;
    this.drag_heading = 0.05f;
    this.speed_heading = 0.0f;
    this.speed_incline = 0.0f;
  }

  private static float applyDrag(
    final float f,
    final float drag,
    final float time)
  {
    return (float) ((double) f * Math.pow((double) drag, (double) time));
  }

  /**
   * Construct a new integrator.
   *
   * @param in_camera The camera to be integrated.
   * @param in_input  The input to be sampled.
   *
   * @return A new integrator
   */

  public static JCameraSphericalAngularIntegratorType newIntegrator(
    final JCameraSphericalType in_camera,
    final JCameraSphericalInput in_input)
  {
    return new JCameraSphericalAngularIntegrator(in_camera, in_input);
  }

  @Override
  public void integrate(
    final float time)
  {
    this.speed_heading = this.integrateHeading(time);
    this.speed_incline = this.integrateIncline(time);
  }

  private float integrateHeading(
    final float time)
  {
    float s = this.speed_heading;

    final boolean positive = this.input.isOrbitingHeadingPositive();
    if (positive) {
      s += this.acceleration_heading * time;
    }
    final boolean negative = this.input.isOrbitingHeadingNegative();
    if (negative) {
      s -= this.acceleration_heading * time;
    }

    s =
      Clamp.clamp(s, -this.maximum_speed_heading, this.maximum_speed_heading);

    this.camera.cameraOrbitHeading(s * time);
    return JCameraSphericalAngularIntegrator.applyDrag(
      s,
      this.drag_heading,
      time);
  }

  private float integrateIncline(
    final float time)
  {
    float s = this.speed_incline;

    final boolean positive = this.input.isOrbitingInclinePositive();
    if (positive) {
      s += this.acceleration_incline * time;
    }
    final boolean negative = this.input.isOrbitingInclineNegative();
    if (negative) {
      s -= this.acceleration_incline * time;
    }

    s =
      Clamp.clamp(s, -this.maximum_speed_incline, this.maximum_speed_incline);

    /**
     * If applying the movement resulted in a value that was clamped, then
     * remove all speed in that direction by returning zero. Otherwise, the
     * user has to achieve a greater than or equal speed in the opposite
     * direction just to get the camera to appear to start moving.
     */

    final boolean clamped = this.camera.cameraOrbitIncline(s * time);
    if (clamped) {
      return 0.0f;
    }

    return JCameraSphericalAngularIntegrator.applyDrag(
      s,
      this.drag_incline,
      time);
  }

  @Override
  public void integratorAngularOrbitHeadingSetAcceleration(
    final float a)
  {
    this.acceleration_heading =
      (float) RangeCheck.checkGreaterDouble(
        (double) a,
        "Acceleration",
        0.0,
        "Minimum acceleration");
  }

  @Override
  public void integratorAngularOrbitHeadingSetDrag(
    final float d)
  {
    this.drag_heading =
      (float) RangeCheck.checkGreaterEqualDouble(
        RangeCheck
          .checkLessEqualDouble((double) d, "Drag factor", 1.0, "Maximum drag"),
        "Drag factor",
        0.0,
        "Minimum drag");
  }

  @Override
  public void integratorAngularOrbitHeadingSetMaximumSpeed(
    final float s)
  {
    this.maximum_speed_heading =
      (float) RangeCheck.checkGreaterEqualDouble(
        (double) s,
        "Speed limit",
        0.0,
        "Minimum limit");
  }

  @Override
  public void integratorAngularOrbitInclineSetAcceleration(
    final float a)
  {
    this.acceleration_incline =
      (float) RangeCheck.checkGreaterDouble(
        (double) a,
        "Acceleration",
        0.0,
        "Minimum acceleration");
  }

  @Override
  public void integratorAngularOrbitInclineSetDrag(
    final float d)
  {
    this.drag_incline =
      (float) RangeCheck.checkGreaterEqualDouble(
        RangeCheck
          .checkLessEqualDouble((double) d, "Drag factor", 1.0, "Maximum drag"),
        "Drag factor",
        0.0,
        "Minimum drag");
  }

  @Override
  public void integratorAngularOrbitInclineSetMaximumSpeed(
    final float s)
  {
    this.maximum_speed_incline =
      (float) RangeCheck.checkGreaterEqualDouble(
        (double) s,
        "Speed limit",
        0.0,
        "Minimum limit");
  }

  @Override
  public JCameraSphericalReadableType integratorGetCamera()
  {
    return this.camera;
  }

  @Override
  public JCameraSphericalInput integratorGetInput()
  {
    return this.input;
  }
}
