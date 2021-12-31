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
 * The default implementation of {@link JCameraFPSStyleAngularIntegratorType}.
 */

@EqualityReference
public final class JCameraFPSStyleAngularIntegrator implements
  JCameraFPSStyleAngularIntegratorType
{
  private final JCameraFPSStyleType camera;
  private final JCameraFPSStyleInputType input;
  private double acceleration_horizontal;
  private double acceleration_vertical;
  private double drag_horizontal;
  private double drag_vertical;
  private double maximum_speed_horizontal;
  private double maximum_speed_vertical;
  private double speed_horizontal;
  private double speed_vertical;

  private JCameraFPSStyleAngularIntegrator(
    final JCameraFPSStyleType in_camera,
    final JCameraFPSStyleInputType in_input)
  {
    this.camera = Objects.requireNonNull(in_camera, "Camera");
    this.input = Objects.requireNonNull(in_input, "Input");

    this.maximum_speed_horizontal = 2.0 * Math.PI;
    this.maximum_speed_vertical = 2.0 * Math.PI;
    this.acceleration_horizontal = this.maximum_speed_horizontal / 2.0f;
    this.acceleration_vertical = this.maximum_speed_vertical / 2.0f;
    this.drag_horizontal = 0.05f;
    this.drag_vertical = 0.05f;
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

  public static JCameraFPSStyleAngularIntegratorType newIntegrator(
    final JCameraFPSStyleType in_camera,
    final JCameraFPSStyleInputType in_input)
  {
    return new JCameraFPSStyleAngularIntegrator(in_camera, in_input);
  }

  @Override
  public void integrate(
    final double time)
  {
    this.speed_horizontal = this.integrateHorizontal(time);
    this.integrateVertical(time);
  }

  private double integrateHorizontal(
    final double time)
  {
    final double r = this.input.takeRotationHorizontal();
    final double s =
      this.speed_horizontal + (r * (this.acceleration_horizontal * time));

    final double sc =
      Clamp.clamp(
        s,
        -this.maximum_speed_horizontal,
        this.maximum_speed_horizontal);

    /**
     * If applying the movement resulted in a value that was clamped, then
     * remove all speed in that direction by returning zero. Otherwise, the
     * user has to achieve a greater than or equal speed in the opposite
     * direction just to get the camera to appear to start moving.
     */

    final boolean clamped = this.camera.cameraRotateAroundHorizontal(sc);
    if (clamped) {
      return 0.0f;
    }

    return applyDrag(
      sc,
      this.drag_horizontal,
      time);
  }

  private void integrateVertical(
    final double time)
  {
    final double r = this.input.takeRotationVertical();
    final double s =
      this.speed_vertical + (r * (this.acceleration_vertical * time));
    final double sc =
      Clamp.clamp(
        s,
        -this.maximum_speed_vertical,
        this.maximum_speed_vertical);
    this.camera.cameraRotateAroundVertical(sc);
    this.speed_vertical =
      applyDrag(sc, this.drag_vertical, time);
  }

  @Override
  public void integratorAngularSetAccelerationHorizontal(
    final double a)
  {
    this.acceleration_horizontal =
      RangeCheck.checkGreaterDouble(
        a,
        "Acceleration",
        0.0,
        "Minimum acceleration");
  }

  @Override
  public void integratorAngularSetAccelerationVertical(
    final double a)
  {
    this.acceleration_vertical =
      RangeCheck.checkGreaterDouble(
        a,
        "Acceleration",
        0.0,
        "Minimum acceleration");
  }

  @Override
  public void integratorAngularSetDragHorizontal(
    final double d)
  {
    this.drag_horizontal =
      RangeCheck.checkGreaterEqualDouble(
        RangeCheck
          .checkLessEqualDouble(d, "Drag factor", 1.0, "Maximum drag"),
        "Drag factor",
        0.0,
        "Minimum drag");
  }

  @Override
  public void integratorAngularSetDragVertical(
    final double d)
  {
    this.drag_vertical =
      RangeCheck.checkGreaterEqualDouble(
        RangeCheck
          .checkLessEqualDouble(d, "Drag factor", 1.0, "Maximum drag"),
        "Drag factor",
        0.0,
        "Minimum drag");
  }

  @Override
  public void integratorAngularSetMaximumSpeedHorizontal(
    final double s)
  {
    this.maximum_speed_horizontal =
      RangeCheck.checkGreaterEqualDouble(
        s,
        "Speed limit",
        0.0,
        "Minimum limit");
  }

  @Override
  public void integratorAngularSetMaximumSpeedVertical(
    final double s)
  {
    this.maximum_speed_vertical =
      RangeCheck.checkGreaterEqualDouble(
        s,
        "Speed limit",
        0.0,
        "Minimum limit");
  }

  @Override
  public JCameraFPSStyleReadableType integratorGetCamera()
  {
    return this.camera;
  }

  @Override
  public JCameraFPSStyleInputType integratorGetInput()
  {
    return this.input;
  }
}
