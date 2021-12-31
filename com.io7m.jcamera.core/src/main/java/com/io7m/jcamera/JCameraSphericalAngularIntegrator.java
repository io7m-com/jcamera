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

import com.io7m.jequality.annotations.EqualityReference;
import com.io7m.jranges.RangeCheck;

import java.util.Objects;

/**
 * The default implementation of {@link JCameraSphericalAngularIntegratorType}
 * .
 */

@EqualityReference
public final class JCameraSphericalAngularIntegrator implements
  JCameraSphericalAngularIntegratorType
{
  private final JCameraSphericalType camera;
  private final JCameraSphericalInputType input;
  private double acceleration_heading;
  private double acceleration_incline;
  private double drag_heading;
  private double drag_incline;
  private double maximum_speed_heading;
  private double maximum_speed_incline;
  private double speed_heading;
  private double speed_incline;

  private JCameraSphericalAngularIntegrator(
    final JCameraSphericalType in_camera,
    final JCameraSphericalInputType in_input)
  {
    this.camera = Objects.requireNonNull(in_camera, "Camera");
    this.input = Objects.requireNonNull(in_input, "Input");

    this.maximum_speed_heading = 2.0 * Math.PI;
    this.maximum_speed_incline = 2.0 * Math.PI;
    this.acceleration_heading = this.maximum_speed_heading / 2.0;
    this.acceleration_incline = this.maximum_speed_incline / 2.0;
    this.drag_incline = 0.05;
    this.drag_heading = 0.05;
    this.speed_heading = 0.0;
    this.speed_incline = 0.0;
  }

  private static double applyDrag(
    final double f,
    final double drag,
    final double time)
  {
    return f * Math.pow(drag, time);
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
    final JCameraSphericalInputType in_input)
  {
    return new JCameraSphericalAngularIntegrator(in_camera, in_input);
  }

  @Override
  public void integrate(
    final double time)
  {
    this.speed_heading = this.integrateHeading(time);
    this.speed_incline = this.integrateIncline(time);
  }

  private double integrateHeading(
    final double time)
  {
    double s = this.speed_heading;

    final boolean positive = this.input.isOrbitingHeadingPositive();
    if (positive) {
      s += this.acceleration_heading * time;
    }
    final boolean negative = this.input.isOrbitingHeadingNegative();
    if (negative) {
      s -= this.acceleration_heading * time;
    }

    s = Clamp.clamp(s, -this.maximum_speed_heading, this.maximum_speed_heading);

    this.camera.cameraOrbitHeading(s * time);
    return applyDrag(
      s,
      this.drag_heading,
      time);
  }

  private double integrateIncline(
    final double time)
  {
    double s = this.speed_incline;

    final boolean positive = this.input.isOrbitingInclinePositive();
    if (positive) {
      s += this.acceleration_incline * time;
    }
    final boolean negative = this.input.isOrbitingInclineNegative();
    if (negative) {
      s -= this.acceleration_incline * time;
    }

    s = Clamp.clamp(s, -this.maximum_speed_incline, this.maximum_speed_incline);

    /*
     * If applying the movement resulted in a value that was clamped, then
     * remove all speed in that direction by returning zero. Otherwise, the
     * user has to achieve a greater than or equal speed in the opposite
     * direction just to get the camera to appear to start moving.
     */

    final boolean clamped = this.camera.cameraOrbitIncline(s * time);
    if (clamped) {
      return 0.0;
    }

    return applyDrag(
      s,
      this.drag_incline,
      time);
  }

  @Override
  public void integratorAngularOrbitHeadingSetAcceleration(
    final double a)
  {
    this.acceleration_heading =
      RangeCheck.checkGreaterDouble(
        a,
        "Acceleration",
        0.0,
        "Minimum acceleration");
  }

  @Override
  public void integratorAngularOrbitHeadingSetDrag(
    final double d)
  {
    this.drag_heading =
      RangeCheck.checkGreaterEqualDouble(
        RangeCheck
          .checkLessEqualDouble(d, "Drag factor", 1.0, "Maximum drag"),
        "Drag factor",
        0.0,
        "Minimum drag");
  }

  @Override
  public void integratorAngularOrbitHeadingSetMaximumSpeed(
    final double s)
  {
    this.maximum_speed_heading =
      RangeCheck.checkGreaterEqualDouble(
        s,
        "Speed limit",
        0.0,
        "Minimum limit");
  }

  @Override
  public void integratorAngularOrbitInclineSetAcceleration(
    final double a)
  {
    this.acceleration_incline =
      RangeCheck.checkGreaterDouble(
        a,
        "Acceleration",
        0.0,
        "Minimum acceleration");
  }

  @Override
  public void integratorAngularOrbitInclineSetDrag(
    final double d)
  {
    this.drag_incline =
      RangeCheck.checkGreaterEqualDouble(
        RangeCheck
          .checkLessEqualDouble(d, "Drag factor", 1.0, "Maximum drag"),
        "Drag factor",
        0.0,
        "Minimum drag");
  }

  @Override
  public void integratorAngularOrbitInclineSetMaximumSpeed(
    final double s)
  {
    this.maximum_speed_incline =
      RangeCheck.checkGreaterEqualDouble(
        s,
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
  public JCameraSphericalInputType integratorGetInput()
  {
    return this.input;
  }
}
