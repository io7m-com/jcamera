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
 * An implementation of {@link JCameraSphericalLinearIntegratorType} that scales
 * movement on the horizontal plane by the current zoom.
 */

@EqualityReference
public final class JCameraSphericalLinearIntegratorZoomScaled implements
  JCameraSphericalLinearIntegratorType
{
  private final JCameraSphericalType camera;
  private final JCameraSphericalInputType input;
  private final JCameraScalingFunctionType drag_scale;
  private final JCameraScalingFunctionType linear_scale;
  private double speed_forward;
  private double speed_right;
  private double speed_up;
  private double speed_zoom;
  private double target_acceleration;
  private double target_drag;
  private double target_maximum_speed;
  private double zoom_acceleration;
  private double zoom_drag;
  private double zoom_maximum_speed;

  private JCameraSphericalLinearIntegratorZoomScaled(
    final JCameraSphericalType in_camera,
    final JCameraSphericalInputType in_input,
    final JCameraScalingFunctionType in_drag_scale,
    final JCameraScalingFunctionType in_linear_scale)
  {
    this.camera = Objects.requireNonNull(in_camera, "Camera");
    this.input = Objects.requireNonNull(in_input, "Input");
    this.drag_scale = Objects.requireNonNull(
      in_drag_scale,
      "Drag scale");
    this.linear_scale = Objects.requireNonNull(
      in_linear_scale,
      "Linear scale");

    this.speed_forward = 0.0;
    this.speed_right = 0.0;
    this.speed_up = 0.0;
    this.speed_zoom = 0.0;

    this.target_maximum_speed = 3.0;
    this.target_drag = 0.25;
    this.target_acceleration = 30.0;

    this.zoom_maximum_speed = 3.0;
    this.zoom_drag = 0.25;
    this.zoom_acceleration = 30.0;
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

  public static JCameraSphericalLinearIntegratorType newIntegrator(
    final JCameraSphericalType in_camera,
    final JCameraSphericalInputType in_input)
  {
    return new JCameraSphericalLinearIntegratorZoomScaled(
      in_camera,
      in_input,
      JCameraScalingFunctions.scaleIdentity(),
      JCameraScalingFunctions.scaleSquareRoot());
  }

  /**
   * Construct a new integrator.
   *
   * @param in_camera       The camera to be integrated.
   * @param in_input        The input to be sampled.
   * @param in_drag_scale   The function used to produce a scaling factor for
   *                        mouse dragging.
   * @param in_linear_scale The function used to produce a scaling factor for
   *                        linear movement.
   *
   * @return A new integrator
   */

  public static JCameraSphericalLinearIntegratorType
  newIntegratorWithFunctions(
    final JCameraSphericalType in_camera,
    final JCameraSphericalInput in_input,
    final JCameraScalingFunctionType in_drag_scale,
    final JCameraScalingFunctionType in_linear_scale)
  {
    return new JCameraSphericalLinearIntegratorZoomScaled(
      in_camera,
      in_input,
      in_drag_scale,
      in_linear_scale);
  }

  @Override
  public void integrate(
    final double t)
  {
    this.speed_zoom = this.integrateZoom(t);

    final double linear_zoom_scale =
      this.linear_scale.evaluate(this.camera.cameraGetZoom());
    final double drag_zoom_scale =
      this.drag_scale.evaluate(this.camera.cameraGetZoom());

    this.speed_forward =
      this.integrateForward(t, linear_zoom_scale, drag_zoom_scale);
    this.speed_right =
      this.integrateRight(t, linear_zoom_scale, drag_zoom_scale);
    this.speed_up = this.integrateUp(t);
  }

  private double integrateForward(
    final double time,
    final double linear_zoom_scale,
    final double drag_zoom_scale)
  {
    double s = this.speed_forward;

    final boolean forward = this.input.isTargetMovingForward();
    if (forward) {
      s += this.target_acceleration * time * linear_zoom_scale;
    }
    final boolean backward = this.input.isTargetMovingBackward();
    if (backward) {
      s -= this.target_acceleration * time * linear_zoom_scale;
    }

    s =
      Clamp.clamp(
        s,
        -this.target_maximum_speed * linear_zoom_scale,
        this.target_maximum_speed * linear_zoom_scale);

    s +=
      this.input.takeTargetMovingForward()
        * this.target_acceleration
        * drag_zoom_scale
        * time;

    this.camera.cameraMoveTargetForwardOnXZ(s * time);
    return applyDrag(
      s,
      this.target_drag,
      time);
  }

  private double integrateRight(
    final double time,
    final double linear_zoom_scale,
    final double drag_zoom_scale)
  {
    double s = this.speed_right;

    final boolean forward = this.input.isTargetMovingRight();
    if (forward) {
      s += this.target_acceleration * time * linear_zoom_scale;
    }
    final boolean backward = this.input.isTargetMovingLeft();
    if (backward) {
      s -= this.target_acceleration * time * linear_zoom_scale;
    }

    s =
      Clamp.clamp(
        s,
        -this.target_maximum_speed * linear_zoom_scale,
        this.target_maximum_speed * linear_zoom_scale);

    s +=
      this.input.takeTargetMovingRight()
        * this.target_acceleration
        * drag_zoom_scale
        * time;

    this.camera.cameraMoveTargetRight(s * time);
    return applyDrag(
      s,
      this.target_drag,
      time);
  }

  private double integrateUp(
    final double time)
  {
    double s = this.speed_up;

    final boolean forward = this.input.isTargetMovingUp();
    if (forward) {
      s += this.target_acceleration * time;
    }
    final boolean backward = this.input.isTargetMovingDown();
    if (backward) {
      s -= this.target_acceleration * time;
    }

    s = Clamp.clamp(s, -this.target_maximum_speed, this.target_maximum_speed);

    this.camera.cameraMoveTargetUp(s * time);
    return applyDrag(
      s,
      this.target_drag,
      time);
  }

  private double integrateZoom(
    final double time)
  {
    double s = this.speed_zoom;

    final boolean forward = this.input.isZoomingIn();
    if (forward) {
      s += this.zoom_acceleration * time;
    }
    final boolean backward = this.input.isZoomingOut();
    if (backward) {
      s -= this.zoom_acceleration * time;
    }

    s = Clamp.clamp(s, -this.zoom_maximum_speed, this.zoom_maximum_speed);

    this.camera.cameraZoomIn(s * time);
    return applyDrag(
      s,
      this.zoom_drag,
      time);
  }

  @Override
  public JCameraSphericalType integratorGetCamera()
  {
    return this.camera;
  }

  @Override
  public JCameraSphericalInputType integratorGetInput()
  {
    return this.input;
  }

  @Override
  public void integratorLinearTargetSetAcceleration(
    final double a)
  {
    this.target_acceleration =
      RangeCheck.checkGreaterDouble(
        a,
        "Acceleration",
        0.0,
        "Minimum acceleration");
  }

  @Override
  public void integratorLinearTargetSetDrag(
    final double f)
  {
    this.target_drag =
      RangeCheck.checkGreaterEqualDouble(
        RangeCheck
          .checkLessEqualDouble(f, "Drag factor", 1.0, "Maximum drag"),
        "Drag factor",
        0.0,
        "Minimum drag");
  }

  @Override
  public void integratorLinearTargetSetMaximumSpeed(
    final double s)
  {
    this.target_maximum_speed =
      RangeCheck.checkGreaterEqualDouble(
        s,
        "Speed limit",
        0.0,
        "Minimum limit");
  }

  @Override
  public void integratorLinearZoomSetAcceleration(
    final double a)
  {
    this.zoom_acceleration =
      RangeCheck.checkGreaterDouble(
        a,
        "Acceleration",
        0.0,
        "Minimum acceleration");
  }

  @Override
  public void integratorLinearZoomSetDrag(
    final double f)
  {
    this.zoom_drag =
      RangeCheck.checkGreaterEqualDouble(
        RangeCheck
          .checkLessEqualDouble(f, "Drag factor", 1.0, "Maximum drag"),
        "Drag factor",
        0.0,
        "Minimum drag");
  }

  @Override
  public void integratorLinearZoomSetMaximumSpeed(
    final double s)
  {
    this.zoom_maximum_speed =
      RangeCheck.checkGreaterEqualDouble(
        s,
        "Speed limit",
        0.0,
        "Minimum limit");
  }
}
