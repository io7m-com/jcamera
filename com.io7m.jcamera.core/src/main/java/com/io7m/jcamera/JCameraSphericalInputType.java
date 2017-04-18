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

/**
 * The type of inputs for spherical cameras.
 */

public interface JCameraSphericalInputType
{
  /**
   * Add a movement forward to the target position. A negative value decreases
   * the amount of movement forward and increases the amount of movement
   * backward.
   *
   * @param in_forward The amount to move forward
   */

  void addTargetMovingContinuousForward(
    double in_forward);

  /**
   * Add a movement right to the target position. A negative value decreases the
   * amount of movement to the right and increases the amount of movement to the
   * left.
   *
   * @param in_right The amount to move right
   */

  void addTargetMovingContinuousRight(
    double in_right);

  /**
   * @return The multiplication factor used for continuous forward/backward
   * movement.
   */

  double getForwardFactor();

  /**
   * @return The multiplication factor used for continuous right/left movement.
   */

  double getRightFactor();

  /**
   * @return The current forward movement.
   */

  double getTargetMovingForwardContinuous();

  /**
   * @return The current rightward movement.
   */

  double getTargetMovingRight();

  /**
   * @return {@code true} if the user is telling the camera to orbit
   */

  boolean isOrbitingHeadingNegative();

  /**
   * @return {@code true} if the user is telling the camera to orbit
   */

  boolean isOrbitingHeadingPositive();

  /**
   * @return {@code true} if the user is telling the camera to orbit
   */

  boolean isOrbitingInclineNegative();

  /**
   * @return {@code true} if the user is telling the camera to orbit
   */

  boolean isOrbitingInclinePositive();

  /**
   * @return {@code true} if the user is telling the camera target to move
   * backward
   */

  boolean isTargetMovingBackward();

  /**
   * @return {@code true} if the user is telling the camera target to move down
   */

  boolean isTargetMovingDown();

  /**
   * Tell the camera target to start/stop moving down.
   *
   * @param in_down {@code true} if the camera should be moving
   */

  void setTargetMovingDown(
    boolean in_down);

  /**
   * @return {@code true} if the user is telling the camera target to move
   * forward
   */

  boolean isTargetMovingForward();

  /**
   * @return {@code true} if the user is telling the camera target to move left
   */

  boolean isTargetMovingLeft();

  /**
   * @return {@code true} if the user is telling the camera target to move right
   */

  boolean isTargetMovingRight();

  /**
   * @return {@code true} if the user is telling the camera target to move up
   */

  boolean isTargetMovingUp();

  /**
   * Tell the camera target to start/stop moving up.
   *
   * @param in_up {@code true} if the camera should be moving
   */

  void setTargetMovingUp(
    boolean in_up);

  /**
   * @return {@code true} if the user is telling the camera to zoom in
   */

  boolean isZoomingIn();

  /**
   * Tell the camera target to start/stop zooming in.
   *
   * @param in_zoom_in {@code true} if the camera should be moving
   */

  void setZoomingIn(
    boolean in_zoom_in);

  /**
   * @return {@code true} if the user is telling the camera to zoom out
   */

  boolean isZoomingOut();

  /**
   * Tell the camera target to start/stop zooming out.
   *
   * @param in_zoom_out {@code true} if the camera should be moving
   */

  void setZoomingOut(
    boolean in_zoom_out);

  /**
   * Set the multiplication factor used for continuous forward/backward
   * movement.
   *
   * @param f The multiplication factor
   */

  void setContinuousForwardFactor(
    double f);

  /**
   * Set the multiplication factor used for continuous left/right movement.
   *
   * @param f The multiplication factor
   */

  void setContinuousRightwardFactor(
    double f);

  /**
   * Tell the camera to start/stop orbiting (for heading) in a negative
   * direction.
   *
   * @param o {@code true} if the camera should be moving
   */

  void setOrbitHeadingNegative(
    boolean o);

  /**
   * Tell the camera to start/stop orbiting (for heading) in a positive
   * direction.
   *
   * @param o {@code true} if the camera should be moving
   */

  void setOrbitHeadingPositive(
    boolean o);

  /**
   * Tell the camera to start/stop orbiting (for incline) in a negative
   * direction.
   *
   * @param o {@code true} if the camera should be moving
   */

  void setOrbitInclineNegative(
    boolean o);

  /**
   * Tell the camera to start/stop orbiting (for incline) in a positive
   * direction.
   *
   * @param o {@code true} if the camera should be moving
   */

  void setOrbitInclinePositive(
    boolean o);

  /**
   * Tell the camera target to start/stop moving backward.
   *
   * @param in_backward {@code true} if the camera should be moving
   */

  void setTargetMovingBackwardCursor(
    boolean in_backward);

  /**
   * Tell the camera target to start/stop moving backward.
   *
   * @param in_backward {@code true} if the camera should be moving
   */

  void setTargetMovingBackwardKey(
    boolean in_backward);

  /**
   * Set the amount of continuous forward movement.
   *
   * @param f The forward movement
   */

  void setTargetMovingContinuousForward(
    double f);

  /**
   * Set the amount of continuous rightward movement.
   *
   * @param f The rightward movement
   */

  void setTargetMovingContinuousRight(
    double f);

  /**
   * Tell the camera target to start/stop moving forward.
   *
   * @param in_forward {@code true} if the camera should be moving
   */

  void setTargetMovingForwardCursor(
    boolean in_forward);

  /**
   * Tell the camera target to start/stop moving forward.
   *
   * @param in_forward {@code true} if the camera should be moving
   */

  void setTargetMovingForwardKey(
    boolean in_forward);

  /**
   * Tell the camera target to start/stop moving left.
   *
   * @param in_left {@code true} if the camera should be moving
   */

  void setTargetMovingLeftCursor(
    boolean in_left);

  /**
   * Tell the camera target to start/stop moving left.
   *
   * @param in_left {@code true} if the camera should be moving
   */

  void setTargetMovingLeftKey(
    boolean in_left);

  /**
   * Tell the camera target to start/stop moving right.
   *
   * @param in_right {@code true} if the camera should be moving
   */

  void setTargetMovingRightCursor(
    boolean in_right);

  /**
   * Tell the camera target to start/stop moving right.
   *
   * @param in_right {@code true} if the camera should be moving
   */

  void setTargetMovingRightKey(
    boolean in_right);

  /**
   * Retrieve {@code r} = {@link #getTargetMovingForwardContinuous()}, set the
   * current forward movement to 0.0, and return {@code r}.
   *
   * @return The amount of forward movement.
   */

  double takeTargetMovingForward();

  /**
   * Retrieve {@code r} = {@link #getTargetMovingRight()}, set the current
   * rightward movement to 0.0, and return {@code r}.
   *
   * @return The amount of rightward movement.
   */

  double takeTargetMovingRight();
}
