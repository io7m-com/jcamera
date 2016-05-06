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

/**
 * <p>An input for an fps-style camera.</p>
 *
 * <p>It is safe to access values of this type from multiple threads.</p>
 */

@EqualityReference
public final class JCameraFPSStyleInput implements JCameraFPSStyleInputType
{
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
   * @return A new input
   */

  public static JCameraFPSStyleInputType newInput()
  {
    return new JCameraFPSStyleInput();
  }

  @Override
  public void addRotationAroundHorizontal(
    final float r)
  {
    this.rotate_horizontal += r;
  }

  @Override
  public void addRotationAroundVertical(
    final float r)
  {
    this.rotate_vertical += r;
  }

  @Override
  public float getRotationHorizontal()
  {
    return this.rotate_horizontal * this.rotate_horizontal_factor;
  }

  @Override
  public void setRotationHorizontal(
    final float r)
  {
    this.rotate_horizontal = r;
  }

  @Override
  public float getRotationVertical()
  {
    return this.rotate_vertical * this.rotate_vertical_factor;
  }

  @Override
  public void setRotationVertical(
    final float r)
  {
    this.rotate_vertical = r;
  }

  @Override
  public boolean isMovingBackward()
  {
    return this.backward;
  }

  @Override
  public void setMovingBackward(
    final boolean in_backward)
  {
    this.backward = in_backward;
  }

  @Override
  public boolean isMovingDown()
  {
    return this.down;
  }

  @Override
  public void setMovingDown(
    final boolean in_down)
  {
    this.down = in_down;
  }

  @Override
  public boolean isMovingForward()
  {
    return this.forward;
  }

  @Override
  public void setMovingForward(
    final boolean in_forward)
  {
    this.forward = in_forward;
  }

  @Override
  public boolean isMovingLeft()
  {
    return this.left;
  }

  @Override
  public void setMovingLeft(
    final boolean in_left)
  {
    this.left = in_left;
  }

  @Override
  public boolean isMovingRight()
  {
    return this.right;
  }

  @Override
  public void setMovingRight(
    final boolean in_right)
  {
    this.right = in_right;
  }

  @Override
  public boolean isMovingUp()
  {
    return this.up;
  }

  @Override
  public void setMovingUp(
    final boolean in_up)
  {
    this.up = in_up;
  }

  @Override
  public void setRotationHorizontalFactor(
    final float f)
  {
    this.rotate_horizontal_factor =
      (float) RangeCheck.checkGreaterDouble(
        (double) f,
        "Factor",
        0.0,
        "Minimum factor");
  }

  @Override
  public void setRotationVerticalFactor(
    final float f)
  {
    this.rotate_vertical_factor =
      (float) RangeCheck.checkGreaterDouble(
        (double) f,
        "Factor",
        0.0,
        "Minimum factor");
  }

  @Override
  public float takeRotationHorizontal()
  {
    final float r = this.getRotationHorizontal();
    this.rotate_horizontal = 0.0f;
    return r;
  }

  @Override
  public float takeRotationVertical()
  {
    final float r = this.getRotationVertical();
    this.rotate_vertical = 0.0f;
    return r;
  }

  @Override
  public String toString()
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
