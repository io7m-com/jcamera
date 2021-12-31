/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
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
 * The type of inputs for FPS cameras.
 */

public interface JCameraFPSStyleInputType
{
  /**
   * Add a rotation around the horizontal axis.
   *
   * @param r The rotation amount
   */

  void addRotationAroundHorizontal(
    double r);

  /**
   * Add a rotation around the vertical axis.
   *
   * @param r The rotation amount
   */

  void addRotationAroundVertical(
    double r);

  /**
   * @return The current horizontal rotation coefficient.
   */

  double getRotationHorizontal();

  /**
   * Set the horizontal rotation.
   *
   * @param r The rotation
   */

  void setRotationHorizontal(
    double r);

  /**
   * @return The current vertical rotation coefficient.
   */

  double getRotationVertical();

  /**
   * Set the vertical rotation.
   *
   * @param r The rotation
   */

  void setRotationVertical(
    double r);

  /**
   * @return {@code true} if the user is telling the camera to move backward
   */

  boolean isMovingBackward();

  /**
   * Tell the camera to start/stop moving backward.
   *
   * @param in_backward {@code true} if the camera should be moving
   */

  void setMovingBackward(
    boolean in_backward);

  /**
   * @return {@code true} if the user is telling the camera to move down
   */

  boolean isMovingDown();

  /**
   * Tell the camera to start/stop moving down.
   *
   * @param in_down {@code true} if the camera should be moving
   */

  void setMovingDown(
    boolean in_down);

  /**
   * @return {@code true} if the user is telling the camera to move forward
   */

  boolean isMovingForward();

  /**
   * Tell the camera to start/stop moving forward.
   *
   * @param in_forward {@code true} if the camera should be moving
   */

  void setMovingForward(
    boolean in_forward);

  /**
   * @return {@code true} if the user is telling the camera to move left
   */

  boolean isMovingLeft();

  /**
   * Tell the camera to start/stop moving left.
   *
   * @param in_left {@code true} if the camera should be moving
   */

  void setMovingLeft(
    boolean in_left);

  /**
   * @return {@code true} if the user is telling the camera to move right
   */

  boolean isMovingRight();

  /**
   * Tell the camera to start/stop moving right.
   *
   * @param in_right {@code true} if the camera should be moving
   */

  void setMovingRight(
    boolean in_right);

  /**
   * @return {@code true} if the user is telling the camera to move up
   */

  boolean isMovingUp();

  /**
   * Tell the camera to start/stop moving up.
   *
   * @param in_up {@code true} if the camera should be moving
   */

  void setMovingUp(
    boolean in_up);

  /**
   * Set the horizontal rotation factor.
   *
   * @param f The rotation factor
   */

  void setRotationHorizontalFactor(
    double f);

  /**
   * Set the vertical rotation factor.
   *
   * @param f The rotation factor
   */

  void setRotationVerticalFactor(
    double f);

  /**
   * Return {@link #getRotationHorizontal()}, setting the rotation to 0.0.
   *
   * @return The current horizontal rotation coefficient.
   */

  double takeRotationHorizontal();

  /**
   * Return {@link #getRotationVertical()}, setting the rotation to 0.0.
   *
   * @return The current vertical rotation coefficient.
   */

  double takeRotationVertical();
}
