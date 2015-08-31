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
 * The default implementation of {@link JCameraFPSStyleLinearIntegratorType}.
 */

@EqualityReference public final class JCameraFPSStyleLinearIntegrator implements
  JCameraFPSStyleLinearIntegratorType
{
  /**
   * Construct a new integrator.
   *
   * @param in_camera
   *          The camera to be integrated.
   * @param in_input
   *          The input to be sampled.
   * @return A new integrator
   */

  public static JCameraFPSStyleLinearIntegratorType newIntegrator(
    final JCameraFPSStyleType in_camera,
    final JCameraFPSStyleInput in_input)
  {
    return new JCameraFPSStyleLinearIntegrator(in_camera, in_input);
  }

  private float                      acceleration;
  private final JCameraFPSStyleType  camera;
  private float                      drag;
  private final JCameraFPSStyleInput input;
  private float                      maximum_speed;
  private float                      speed_forward;
  private float                      speed_right;
  private float                      speed_up;

  private JCameraFPSStyleLinearIntegrator(
    final JCameraFPSStyleType in_camera,
    final JCameraFPSStyleInput in_input)
  {
    this.camera = NullCheck.notNull(in_camera, "Camera");
    this.input = NullCheck.notNull(in_input, "Input");
    this.maximum_speed = 3.0f;
    this.speed_forward = 0.0f;
    this.speed_right = 0.0f;
    this.speed_up = 0.0f;
    this.drag = 0.25f;
    this.acceleration = 30.0f;
  }

  private float applyDrag(
    final float f,
    final float time)
  {
    return (float) (f * Math.pow(this.drag, time));
  }

  @Override public void integrate(
    final float t)
  {
    this.speed_forward = this.integrateForward(t);
    this.speed_right = this.integrateRight(t);
    this.speed_up = this.integrateUp(t);
  }

  private float integrateForward(
    final float time)
  {
    float s = this.speed_forward;

    final boolean forward = this.input.isMovingForward();
    if (forward) {
      s += this.acceleration * time;
    }
    final boolean backward = this.input.isMovingBackward();
    if (backward) {
      s -= this.acceleration * time;
    }

    s = Clamp.clamp(s, -this.maximum_speed, this.maximum_speed);
    this.camera.cameraMoveForward(s * time);
    return this.applyDrag(s, time);
  }

  private float integrateRight(
    final float time)
  {
    float s = this.speed_right;

    final boolean forward = this.input.isMovingRight();
    if (forward) {
      s += this.acceleration * time;
    }
    final boolean backward = this.input.isMovingLeft();
    if (backward) {
      s -= this.acceleration * time;
    }

    s = Clamp.clamp(s, -this.maximum_speed, this.maximum_speed);
    this.camera.cameraMoveRight(s * time);
    return this.applyDrag(s, time);
  }

  private float integrateUp(
    final float time)
  {
    float s = this.speed_up;

    final boolean forward = this.input.isMovingUp();
    if (forward) {
      s += this.acceleration * time;
    }
    final boolean backward = this.input.isMovingDown();
    if (backward) {
      s -= this.acceleration * time;
    }

    s = Clamp.clamp(s, -this.maximum_speed, this.maximum_speed);
    this.camera.cameraMoveUp(s * time);
    return this.applyDrag(s, time);
  }

  @Override public JCameraFPSStyleReadableType integratorGetCamera()
  {
    return this.camera;
  }

  @Override public JCameraFPSStyleInput integratorGetInput()
  {
    return this.input;
  }

  @Override public void integratorLinearSetAcceleration(
    final float a)
  {
    this.acceleration =
      (float) RangeCheck.checkGreaterDouble(
        a,
        "Acceleration",
        0.0,
        "Minimum acceleration");
  }

  @Override public void integratorLinearSetDrag(
    final float f)
  {
    this.drag =
      (float) RangeCheck.checkGreaterEqualDouble(
        RangeCheck
          .checkLessEqualDouble(f, "Drag factor", 1.0, "Maximum drag"),
        "Drag factor",
        0.0,
        "Minimum drag");
  }

  @Override public void integratorLinearSetMaximumSpeed(
    final float s)
  {
    this.maximum_speed =
      (float) RangeCheck.checkGreaterEqualDouble(
        s,
        "Speed limit",
        0.0,
        "Minimum limit");
  }
}
