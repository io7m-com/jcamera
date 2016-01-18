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

import com.io7m.jequality.annotations.EqualityStructural;
import com.io7m.jnull.Nullable;

/**
 * Mutable rotation coefficients.
 */

@EqualityStructural
public final class JCameraRotationCoefficients
{
  private volatile float horizontal;
  private volatile float vertical;

  /**
   * Construct new coefficients.
   */

  public JCameraRotationCoefficients()
  {

  }

  @Override
  public boolean equals(
    final @Nullable Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    final JCameraRotationCoefficients other =
      (JCameraRotationCoefficients) obj;

    return
      Float.floatToIntBits(this.horizontal) == Float.floatToIntBits(other.horizontal)
      && Float.floatToIntBits(this.vertical) == Float.floatToIntBits(other.vertical);
  }

  /**
   * @return The horizontal coefficient
   */

  public float getHorizontal()
  {
    return this.horizontal;
  }

  /**
   * Set the horizontal coefficient
   *
   * @param in_horizontal The horizontal coefficient
   */

  public void setHorizontal(
    final float in_horizontal)
  {
    this.horizontal = in_horizontal;
  }

  /**
   * @return The vertical coefficient
   */

  public float getVertical()
  {
    return this.vertical;
  }

  /**
   * Set the vertical coefficient
   *
   * @param in_vertical The vertical coefficient
   */

  public void setVertical(
    final float in_vertical)
  {
    this.vertical = in_vertical;
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(this.horizontal);
    result = (prime * result) + Float.floatToIntBits(this.vertical);
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder b = new StringBuilder();
    b.append("[JCameraRotationCoefficients horizontal=");
    b.append(this.horizontal);
    b.append(", vertical=");
    b.append(this.vertical);
    b.append("]");
    final String r = b.toString();
    assert r != null;
    return r;
  }
}
