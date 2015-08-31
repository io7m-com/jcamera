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
import com.io7m.jranges.RangeCheck;

/**
 * <p>
 * An input for an fps-style camera.
 * </p>
 * <p>
 * It is safe to access values of this type from multiple threads.
 * </p>
 */

@EqualityReference public final class JCameraFPSStyleInput
{
  /**
   * @return A new input
   */

  public static JCameraFPSStyleInput newInput()
  {
    return new JCameraFPSStyleInput();
  }

  private volatile boolean backward;
  private volatile boolean down;
  private volatile boolean forward;
  private volatile boolean left;
  private volatile boolean right;
  private volatile float   rotate_horizontal;
  private volatile float   rotate_horizontal_factor;
  private volatile float   rotate_vertical;
  private volatile float   rotate_vertical_factor;
  private volatile boolean up;

  private JCameraFPSStyleInput()
  {
    this.rotate_horizontal = 0.0f;
    this.rotate_vertical = 0.0f;
    this.rotate_horizontal_factor = 1.0f;
    this.rotate_vertical_factor = 1.0f;
  }

  /**
   * Add a rotation around the horizontal axis.
   *
   * @param r
   *          The rotation amount
   */

  public void addRotationAroundHorizontal(
    final float r)
  {
    this.rotate_horizontal += r;
  }

  /**
   * Add a rotation around the vertical axis.
   *
   * @param r
   *          The rotation amount
   */

  public void addRotationAroundVertical(
    final float r)
  {
    this.rotate_vertical += r;
  }

  /**
   * @return The current horizontal rotation coefficient.
   */

  public float getRotationHorizontal()
  {
    return this.rotate_horizontal * this.rotate_horizontal_factor;
  }

  /**
   * @return The current vertical rotation coefficient.
   */

  public float getRotationVertical()
  {
    return this.rotate_vertical * this.rotate_vertical_factor;
  }

  /**
   * @return <code>true</code> if the user is telling the camera to move
   *         backward
   */

  public boolean isMovingBackward()
  {
    return this.backward;
  }

  /**
   * @return <code>true</code> if the user is telling the camera to move down
   */

  public boolean isMovingDown()
  {
    return this.down;
  }

  /**
   * @return <code>true</code> if the user is telling the camera to move
   *         forward
   */

  public boolean isMovingForward()
  {
    return this.forward;
  }

  /**
   * @return <code>true</code> if the user is telling the camera to move left
   */

  public boolean isMovingLeft()
  {
    return this.left;
  }

  /**
   * @return <code>true</code> if the user is telling the camera to move right
   */

  public boolean isMovingRight()
  {
    return this.right;
  }

  /**
   * @return <code>true</code> if the user is telling the camera to move up
   */

  public boolean isMovingUp()
  {
    return this.up;
  }

  /**
   * Tell the camera to start/stop moving backward.
   *
   * @param in_backward
   *          <code>true</code> if the camera should be moving
   */

  public void setMovingBackward(
    final boolean in_backward)
  {
    this.backward = in_backward;
  }

  /**
   * Tell the camera to start/stop moving down.
   *
   * @param in_down
   *          <code>true</code> if the camera should be moving
   */

  public void setMovingDown(
    final boolean in_down)
  {
    this.down = in_down;
  }

  /**
   * Tell the camera to start/stop moving forward.
   *
   * @param in_forward
   *          <code>true</code> if the camera should be moving
   */

  public void setMovingForward(
    final boolean in_forward)
  {
    this.forward = in_forward;
  }

  /**
   * Tell the camera to start/stop moving left.
   *
   * @param in_left
   *          <code>true</code> if the camera should be moving
   */

  public void setMovingLeft(
    final boolean in_left)
  {
    this.left = in_left;
  }

  /**
   * Tell the camera to start/stop moving right.
   *
   * @param in_right
   *          <code>true</code> if the camera should be moving
   */

  public void setMovingRight(
    final boolean in_right)
  {
    this.right = in_right;
  }

  /**
   * Tell the camera to start/stop moving up.
   *
   * @param in_up
   *          <code>true</code> if the camera should be moving
   */

  public void setMovingUp(
    final boolean in_up)
  {
    this.up = in_up;
  }

  /**
   * Set the horizontal rotation.
   *
   * @param r
   *          The rotation
   */

  public void setRotationHorizontal(
    final float r)
  {
    this.rotate_horizontal = r;
  }

  /**
   * Set the horizontal rotation factor.
   *
   * @param f
   *          The rotation factor
   */

  public void setRotationHorizontalFactor(
    final float f)
  {
    this.rotate_horizontal_factor =
      (float) RangeCheck.checkGreaterDouble(
        f,
        "Factor",
        0.0f,
        "Minimum factor");
  }

  /**
   * Set the vertical rotation.
   *
   * @param r
   *          The rotation
   */

  public void setRotationVertical(
    final float r)
  {
    this.rotate_vertical = r;
  }

  /**
   * Set the vertical rotation factor.
   *
   * @param f
   *          The rotation factor
   */

  public void setRotationVerticalFactor(
    final float f)
  {
    this.rotate_vertical_factor =
      (float) RangeCheck.checkGreaterDouble(
        f,
        "Factor",
        0.0f,
        "Minimum factor");
  }

  /**
   * Return {@link #getRotationHorizontal()}, setting the rotation to 0.0.
   *
   * @return The current horizontal rotation coefficient.
   */

  public float takeRotationHorizontal()
  {
    final float r = this.getRotationHorizontal();
    this.rotate_horizontal = 0.0f;
    return r;
  }

  /**
   * Return {@link #getRotationVertical()}, setting the rotation to 0.0.
   *
   * @return The current vertical rotation coefficient.
   */

  public float takeRotationVertical()
  {
    final float r = this.getRotationVertical();
    this.rotate_vertical = 0.0f;
    return r;
  }

  @Override public String toString()
  {
    final StringBuilder b = new StringBuilder();
    b.append("[JCameraFPSStyleInput backward=");
    b.append(this.backward);
    b.append(", down=");
    b.append(this.down);
    b.append(", forward=");
    b.append(this.forward);
    b.append(", left=");
    b.append(this.left);
    b.append(", right=");
    b.append(this.right);
    b.append(", rotate_horizontal=");
    b.append(this.rotate_horizontal);
    b.append(", rotate_vertical=");
    b.append(this.rotate_vertical);
    b.append(", up=");
    b.append(this.up);
    b.append(", rotate_horizontal_factor=");
    b.append(this.rotate_horizontal_factor);
    b.append(", rotate_vertical_factor=");
    b.append(this.rotate_vertical_factor);
    b.append("]");
    final String r = b.toString();
    assert r != null;
    return r;
  }
}
