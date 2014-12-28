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

/**
 * <p>
 * An input for a spherical camera.
 * </p>
 * <p>
 * It is safe to access values of this type from multiple threads.
 * </p>
 */

@EqualityReference public final class JCameraSphericalInput
{
  /**
   * @return A new input
   */

  public static JCameraSphericalInput newInput()
  {
    return new JCameraSphericalInput();
  }

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
   * Add a movement forward to the target position. A negative value decreases
   * the amount of movement forward and increases the amount of movement
   * backward.
   *
   * @param in_forward
   *          The amount to move forward
   */

  public void addTargetMovingContinuousForward(
    final float in_forward)
  {
    this.forward_continuous += in_forward;
  }

  /**
   * Add a movement right to the target position. A negative value decreases
   * the amount of movement to the right and increases the amount of movement
   * to the left.
   *
   * @param in_right
   *          The amount to move right
   */

  public void addTargetMovingContinuousRight(
    final float in_right)
  {
    this.right_continuous += in_right;
  }

  /**
   * @return The multiplication factor used for continuous forward/backward
   *         movement.
   */

  public float getForwardFactor()
  {
    return this.forward_factor;
  }

  /**
   * @return The multiplication factor used for continuous right/left
   *         movement.
   */

  public float getRightFactor()
  {
    return this.right_factor;
  }

  /**
   * @return The current forward movement.
   */

  public float getTargetMovingForwardContinuous()
  {
    return this.forward_continuous * this.forward_factor;
  }

  /**
   * @return The current rightward movement.
   */

  public float getTargetMovingRight()
  {
    return this.right_continuous * this.right_factor;
  }

  /**
   * @return <code>true</code> if the user is telling the camera to orbit
   */

  public boolean isOrbitingHeadingNegative()
  {
    return this.orbit_heading_negative;
  }

  /**
   * @return <code>true</code> if the user is telling the camera to orbit
   */

  public boolean isOrbitingHeadingPositive()
  {
    return this.orbit_heading_positive;
  }

  /**
   * @return <code>true</code> if the user is telling the camera to orbit
   */

  public boolean isOrbitingInclineNegative()
  {
    return this.orbit_incline_negative;
  }

  /**
   * @return <code>true</code> if the user is telling the camera to orbit
   */

  public boolean isOrbitingInclinePositive()
  {
    return this.orbit_incline_positive;
  }

  /**
   * @return <code>true</code> if the user is telling the camera target to
   *         move backward
   */

  public boolean isTargetMovingBackward()
  {
    return this.backward_key || this.backward_cursor;
  }

  /**
   * @return <code>true</code> if the user is telling the camera target to
   *         move down
   */

  public boolean isTargetMovingDown()
  {
    return this.down;
  }

  /**
   * @return <code>true</code> if the user is telling the camera target to
   *         move forward
   */

  public boolean isTargetMovingForward()
  {
    return this.forward_key || this.forward_cursor;
  }

  /**
   * @return <code>true</code> if the user is telling the camera target to
   *         move left
   */

  public boolean isTargetMovingLeft()
  {
    return this.left_key || this.left_cursor;
  }

  /**
   * @return <code>true</code> if the user is telling the camera target to
   *         move right
   */

  public boolean isTargetMovingRight()
  {
    return this.right_key || this.right_cursor;
  }

  /**
   * @return <code>true</code> if the user is telling the camera target to
   *         move up
   */

  public boolean isTargetMovingUp()
  {
    return this.up;
  }

  /**
   * @return <code>true</code> if the user is telling the camera to zoom in
   */

  public boolean isZoomingIn()
  {
    return this.zoom_in;
  }

  /**
   * @return <code>true</code> if the user is telling the camera to zoom out
   */

  public boolean isZoomingOut()
  {
    return this.zoom_out;
  }

  /**
   * Set the multiplication factor used for continuous forward/backward
   * movement.
   *
   * @param f
   *          The multiplication factor
   */

  public void setContinuousForwardFactor(
    final float f)
  {
    this.forward_factor = f;
  }

  /**
   * Set the multiplication factor used for continuous left/right movement.
   *
   * @param f
   *          The multiplication factor
   */

  public void setContinuousRightwardFactor(
    final float f)
  {
    this.right_factor = f;
  }

  /**
   * Tell the camera to start/stop orbiting (for heading) in a negative
   * direction.
   *
   * @param o
   *          <code>true</code> if the camera should be moving
   */

  public void setOrbitHeadingNegative(
    final boolean o)
  {
    this.orbit_heading_negative = o;
  }

  /**
   * Tell the camera to start/stop orbiting (for heading) in a positive
   * direction.
   *
   * @param o
   *          <code>true</code> if the camera should be moving
   */

  public void setOrbitHeadingPositive(
    final boolean o)
  {
    this.orbit_heading_positive = o;
  }

  /**
   * Tell the camera to start/stop orbiting (for incline) in a negative
   * direction.
   *
   * @param o
   *          <code>true</code> if the camera should be moving
   */

  public void setOrbitInclineNegative(
    final boolean o)
  {
    this.orbit_incline_negative = o;
  }

  /**
   * Tell the camera to start/stop orbiting (for incline) in a positive
   * direction.
   *
   * @param o
   *          <code>true</code> if the camera should be moving
   */

  public void setOrbitInclinePositive(
    final boolean o)
  {
    this.orbit_incline_positive = o;
  }

  /**
   * Tell the camera target to start/stop moving backward.
   *
   * @param in_backward
   *          <code>true</code> if the camera should be moving
   */

  public void setTargetMovingBackwardCursor(
    final boolean in_backward)
  {
    this.backward_cursor = in_backward;
  }

  /**
   * Tell the camera target to start/stop moving backward.
   *
   * @param in_backward
   *          <code>true</code> if the camera should be moving
   */

  public void setTargetMovingBackwardKey(
    final boolean in_backward)
  {
    this.backward_key = in_backward;
  }

  /**
   * Set the amount of continuous forward movement.
   *
   * @param f
   *          The forward movement
   */

  public void setTargetMovingContinuousForward(
    final float f)
  {
    this.forward_continuous = f;
  }

  /**
   * Set the amount of continuous rightward movement.
   *
   * @param f
   *          The rightward movement
   */

  public void setTargetMovingContinuousRight(
    final float f)
  {
    this.right_continuous = f;
  }

  /**
   * Tell the camera target to start/stop moving down.
   *
   * @param in_down
   *          <code>true</code> if the camera should be moving
   */

  public void setTargetMovingDown(
    final boolean in_down)
  {
    this.down = in_down;
  }

  /**
   * Tell the camera target to start/stop moving forward.
   *
   * @param in_forward
   *          <code>true</code> if the camera should be moving
   */

  public void setTargetMovingForwardCursor(
    final boolean in_forward)
  {
    this.forward_cursor = in_forward;
  }

  /**
   * Tell the camera target to start/stop moving forward.
   *
   * @param in_forward
   *          <code>true</code> if the camera should be moving
   */

  public void setTargetMovingForwardKey(
    final boolean in_forward)
  {
    this.forward_key = in_forward;
  }

  /**
   * Tell the camera target to start/stop moving left.
   *
   * @param in_left
   *          <code>true</code> if the camera should be moving
   */

  public void setTargetMovingLeftCursor(
    final boolean in_left)
  {
    this.left_cursor = in_left;
  }

  /**
   * Tell the camera target to start/stop moving left.
   *
   * @param in_left
   *          <code>true</code> if the camera should be moving
   */

  public void setTargetMovingLeftKey(
    final boolean in_left)
  {
    this.left_key = in_left;
  }

  /**
   * Tell the camera target to start/stop moving right.
   *
   * @param in_right
   *          <code>true</code> if the camera should be moving
   */

  public void setTargetMovingRightCursor(
    final boolean in_right)
  {
    this.right_cursor = in_right;
  }

  /**
   * Tell the camera target to start/stop moving right.
   *
   * @param in_right
   *          <code>true</code> if the camera should be moving
   */

  public void setTargetMovingRightKey(
    final boolean in_right)
  {
    this.right_key = in_right;
  }

  /**
   * Tell the camera target to start/stop moving up.
   *
   * @param in_up
   *          <code>true</code> if the camera should be moving
   */

  public void setTargetMovingUp(
    final boolean in_up)
  {
    this.up = in_up;
  }

  /**
   * Tell the camera target to start/stop zooming in.
   *
   * @param in_zoom_in
   *          <code>true</code> if the camera should be moving
   */

  public void setZoomingIn(
    final boolean in_zoom_in)
  {
    this.zoom_in = in_zoom_in;
  }

  /**
   * Tell the camera target to start/stop zooming out.
   *
   * @param in_zoom_out
   *          <code>true</code> if the camera should be moving
   */

  public void setZoomingOut(
    final boolean in_zoom_out)
  {
    this.zoom_out = in_zoom_out;
  }

  /**
   * Retrieve <code>r</code> = {@link #getTargetMovingForwardContinuous()},
   * set the current forward movement to 0.0, and return <code>r</code>.
   *
   * @return The amount of forward movement.
   */

  public float takeTargetMovingForward()
  {
    final float r = this.getTargetMovingForwardContinuous();
    this.forward_continuous = 0.0f;
    return r;
  }

  /**
   * Retrieve <code>r</code> = {@link #getTargetMovingRight()}, set the
   * current rightward movement to 0.0, and return <code>r</code>.
   *
   * @return The amount of rightward movement.
   */

  public float takeTargetMovingRight()
  {
    final float r = this.getTargetMovingRight();
    this.right_continuous = 0.0f;
    return r;
  }
}
