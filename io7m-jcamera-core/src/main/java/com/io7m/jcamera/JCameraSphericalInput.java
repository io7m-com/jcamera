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

/**
 * <p> An input for a spherical camera. </p> <p> It is safe to access values of
 * this type from multiple threads. </p>
 */

@EqualityReference
public final class JCameraSphericalInput implements JCameraSphericalInputType
{
  private volatile boolean backward_cursor;
  private volatile boolean backward_key;
  private volatile boolean down;
  private volatile float   forward_continuous;
  private volatile boolean forward_cursor;
  private volatile float   forward_factor;
  private volatile boolean forward_key;
  private volatile boolean left_cursor;
  private volatile boolean left_key;
  private volatile boolean orbit_heading_negative;
  private volatile boolean orbit_heading_positive;
  private volatile boolean orbit_incline_negative;
  private volatile boolean orbit_incline_positive;
  private volatile float   right_continuous;
  private volatile boolean right_cursor;
  private volatile float   right_factor;
  private volatile boolean right_key;
  private volatile boolean up;
  private volatile boolean zoom_in;
  private volatile boolean zoom_out;

  private JCameraSphericalInput()
  {
    this.forward_factor = 1.0f;
    this.right_factor = 1.0f;
  }

  /**
   * @return A new input
   */

  public static JCameraSphericalInputType newInput()
  {
    return new JCameraSphericalInput();
  }

  @Override
  public void addTargetMovingContinuousForward(
    final float in_forward)
  {
    this.forward_continuous += in_forward;
  }

  @Override
  public void addTargetMovingContinuousRight(
    final float in_right)
  {
    this.right_continuous += in_right;
  }

  @Override
  public float getForwardFactor()
  {
    return this.forward_factor;
  }

  @Override
  public float getRightFactor()
  {
    return this.right_factor;
  }

  @Override
  public float getTargetMovingForwardContinuous()
  {
    return this.forward_continuous * this.forward_factor;
  }

  @Override
  public float getTargetMovingRight()
  {
    return this.right_continuous * this.right_factor;
  }

  @Override
  public boolean isOrbitingHeadingNegative()
  {
    return this.orbit_heading_negative;
  }

  @Override
  public boolean isOrbitingHeadingPositive()
  {
    return this.orbit_heading_positive;
  }

  @Override
  public boolean isOrbitingInclineNegative()
  {
    return this.orbit_incline_negative;
  }

  @Override
  public boolean isOrbitingInclinePositive()
  {
    return this.orbit_incline_positive;
  }

  @Override
  public boolean isTargetMovingBackward()
  {
    return this.backward_key || this.backward_cursor;
  }

  @Override
  public boolean isTargetMovingDown()
  {
    return this.down;
  }

  @Override
  public void setTargetMovingDown(
    final boolean in_down)
  {
    this.down = in_down;
  }

  @Override
  public boolean isTargetMovingForward()
  {
    return this.forward_key || this.forward_cursor;
  }

  @Override
  public boolean isTargetMovingLeft()
  {
    return this.left_key || this.left_cursor;
  }

  @Override
  public boolean isTargetMovingRight()
  {
    return this.right_key || this.right_cursor;
  }

  @Override
  public boolean isTargetMovingUp()
  {
    return this.up;
  }

  @Override
  public void setTargetMovingUp(
    final boolean in_up)
  {
    this.up = in_up;
  }

  @Override
  public boolean isZoomingIn()
  {
    return this.zoom_in;
  }

  @Override
  public void setZoomingIn(
    final boolean in_zoom_in)
  {
    this.zoom_in = in_zoom_in;
  }

  @Override
  public boolean isZoomingOut()
  {
    return this.zoom_out;
  }

  @Override
  public void setZoomingOut(
    final boolean in_zoom_out)
  {
    this.zoom_out = in_zoom_out;
  }

  @Override
  public void setContinuousForwardFactor(
    final float f)
  {
    this.forward_factor = f;
  }

  @Override
  public void setContinuousRightwardFactor(
    final float f)
  {
    this.right_factor = f;
  }

  @Override
  public void setOrbitHeadingNegative(
    final boolean o)
  {
    this.orbit_heading_negative = o;
  }

  @Override
  public void setOrbitHeadingPositive(
    final boolean o)
  {
    this.orbit_heading_positive = o;
  }

  @Override
  public void setOrbitInclineNegative(
    final boolean o)
  {
    this.orbit_incline_negative = o;
  }

  @Override
  public void setOrbitInclinePositive(
    final boolean o)
  {
    this.orbit_incline_positive = o;
  }

  @Override
  public void setTargetMovingBackwardCursor(
    final boolean in_backward)
  {
    this.backward_cursor = in_backward;
  }

  @Override
  public void setTargetMovingBackwardKey(
    final boolean in_backward)
  {
    this.backward_key = in_backward;
  }

  @Override
  public void setTargetMovingContinuousForward(
    final float f)
  {
    this.forward_continuous = f;
  }

  @Override
  public void setTargetMovingContinuousRight(
    final float f)
  {
    this.right_continuous = f;
  }

  @Override
  public void setTargetMovingForwardCursor(
    final boolean in_forward)
  {
    this.forward_cursor = in_forward;
  }

  @Override
  public void setTargetMovingForwardKey(
    final boolean in_forward)
  {
    this.forward_key = in_forward;
  }

  @Override
  public void setTargetMovingLeftCursor(
    final boolean in_left)
  {
    this.left_cursor = in_left;
  }

  @Override
  public void setTargetMovingLeftKey(
    final boolean in_left)
  {
    this.left_key = in_left;
  }

  @Override
  public void setTargetMovingRightCursor(
    final boolean in_right)
  {
    this.right_cursor = in_right;
  }

  @Override
  public void setTargetMovingRightKey(
    final boolean in_right)
  {
    this.right_key = in_right;
  }

  @Override
  public float takeTargetMovingForward()
  {
    final float r = this.getTargetMovingForwardContinuous();
    this.forward_continuous = 0.0f;
    return r;
  }

  @Override
  public float takeTargetMovingRight()
  {
    final float r = this.getTargetMovingRight();
    this.right_continuous = 0.0f;
    return r;
  }
}
