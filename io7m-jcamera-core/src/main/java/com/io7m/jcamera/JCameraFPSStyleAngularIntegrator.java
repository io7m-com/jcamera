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
 * The default implementation of {@link JCameraFPSStyleAngularIntegratorType}.
 */

@EqualityReference
public final class JCameraFPSStyleAngularIntegrator implements
  JCameraFPSStyleAngularIntegratorType
{
  private final JCameraFPSStyleType  camera;
  private final JCameraFPSStyleInput input;
  private       float                acceleration_horizontal;
  private       float                acceleration_vertical;
  private       float                drag_horizontal;
  private       float                drag_vertical;
  private       float                maximum_speed_horizontal;
  private       float                maximum_speed_vertical;
  private       float                speed_horizontal;
  private       float                speed_vertical;
  private JCameraFPSStyleAngularIntegrator(
    final JCameraFPSStyleType in_camera,
    final JCameraFPSStyleInput in_input)
  {
    this.camera = NullCheck.notNull(in_camera, "Camera");
    this.input = NullCheck.notNull(in_input, "Input");

    this.maximum_speed_horizontal = (float) (2 * Math.PI);
    this.maximum_speed_vertical = (float) (2 * Math.PI);
    this.acceleration_horizontal = this.maximum_speed_horizontal / 2.0f;
    this.acceleration_vertical = this.maximum_speed_vertical / 2.0f;
    this.drag_horizontal = 0.05f;
    this.drag_vertical = 0.05f;
  }

  private static float applyDrag(
    final float f,
    final float drag,
    final float time)
  {
    return (float) (f * Math.pow(drag, time));
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
    final JCameraFPSStyleInput in_input)
  {
    return new JCameraFPSStyleAngularIntegrator(in_camera, in_input);
  }

  @Override
  public void integrate(
    final float time)
  {
    this.speed_horizontal = this.integrateHorizontal(time);
    this.integrateVertical(time);
  }

  private float integrateHorizontal(
    final float time)
  {
    final float r = this.input.takeRotationHorizontal();
    final float s =
      this.speed_horizontal + (r * (this.acceleration_horizontal * time));

    final float sc =
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

    return JCameraFPSStyleAngularIntegrator.applyDrag(
      sc,
      this.drag_horizontal,
      time);
  }

  private void integrateVertical(
    final float time)
  {
    final float r = this.input.takeRotationVertical();
    final float s =
      this.speed_vertical + (r * (this.acceleration_vertical * time));
    final float sc =
      Clamp.clamp(
        s,
        -this.maximum_speed_vertical,
        this.maximum_speed_vertical);
    this.camera.cameraRotateAroundVertical(sc);
    this.speed_vertical =
      JCameraFPSStyleAngularIntegrator
        .applyDrag(sc, this.drag_vertical, time);
  }

  @Override
  public void integratorAngularSetAccelerationHorizontal(
    final float a)
  {
    this.acceleration_horizontal =
      (float) RangeCheck.checkGreaterDouble(
        a,
        "Acceleration",
        0.0,
        "Minimum acceleration");
  }

  @Override
  public void integratorAngularSetAccelerationVertical(
    final float a)
  {
    this.acceleration_vertical =
      (float) RangeCheck.checkGreaterDouble(
        a,
        "Acceleration",
        0.0,
        "Minimum acceleration");
  }

  @Override
  public void integratorAngularSetDragHorizontal(
    final float d)
  {
    this.drag_horizontal =
      (float) RangeCheck.checkGreaterEqualDouble(
        RangeCheck
          .checkLessEqualDouble(d, "Drag factor", 1.0, "Maximum drag"),
        "Drag factor",
        0.0,
        "Minimum drag");
  }

  @Override
  public void integratorAngularSetDragVertical(
    final float d)
  {
    this.drag_vertical =
      (float) RangeCheck.checkGreaterEqualDouble(
        RangeCheck
          .checkLessEqualDouble(d, "Drag factor", 1.0, "Maximum drag"),
        "Drag factor",
        0.0,
        "Minimum drag");
  }

  @Override
  public void integratorAngularSetMaximumSpeedHorizontal(
    final float s)
  {
    this.maximum_speed_horizontal =
      (float) RangeCheck.checkGreaterEqualDouble(
        s,
        "Speed limit",
        0.0,
        "Minimum limit");
  }

  @Override
  public void integratorAngularSetMaximumSpeedVertical(
    final float s)
  {
    this.maximum_speed_vertical =
      (float) RangeCheck.checkGreaterEqualDouble(
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
  public JCameraFPSStyleInput integratorGetInput()
  {
    return this.input;
  }
}
