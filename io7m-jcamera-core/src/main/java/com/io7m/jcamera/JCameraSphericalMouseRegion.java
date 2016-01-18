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
import com.io7m.jtensors.VectorWritable2FType;

/**
 * An immutable rectangular region that maps mouse positions to movements.
 */

@EqualityReference
public final class JCameraSphericalMouseRegion
{
  private final float               center_x;
  private final float               center_y;
  private final float               height;
  private final JCameraScreenOrigin origin;
  private final float               width;
  private JCameraSphericalMouseRegion(
    final JCameraScreenOrigin in_origin,
    final float in_width,
    final float in_height)
  {
    this.origin = NullCheck.notNull(in_origin, "Origin");

    this.height =
      (float) RangeCheck.checkGreaterEqualDouble(
        in_height,
        "Height",
        2.0f,
        "Minimum height");
    this.center_y = this.height / 2.0f;

    this.width =
      (float) RangeCheck.checkGreaterEqualDouble(
        in_width,
        "Width",
        2.0f,
        "Minimum width");
    this.center_x = this.width / 2.0f;
  }

  /**
   * Construct a new mouse region.
   *
   * @param in_origin The screen origin.
   * @param in_width  The region width.
   * @param in_height The region height.
   *
   * @return A new mouse region.
   */

  public static JCameraSphericalMouseRegion newRegion(
    final JCameraScreenOrigin in_origin,
    final float in_width,
    final float in_height)
  {
    return new JCameraSphericalMouseRegion(in_origin, in_width, in_height);
  }

  /**
   * @return The X coordinate of the center of the region
   */

  public float getCenterX()
  {
    return this.center_x;
  }

  /**
   * @return The Y coordinate of the center of the region
   */

  public float getCenterY()
  {
    return this.center_y;
  }

  /**
   * Get the normalized coordinates for the given screen coordinates <code>(x,
   * y)</code>.
   *
   * @param x   The x coordinate
   * @param y   The y coordinate
   * @param out The output vector
   */

  public void getPosition(
    final float x,
    final float y,
    final VectorWritable2FType out)
  {
    NullCheck.notNull(out, "Output");

    final float fx = x;
    final float fy = y;
    final float mx = ((fx - this.center_x) / this.width) * 2.0f;
    final float my = ((fy - this.center_y) / this.height) * 2.0f;

    switch (this.origin) {
      case SCREEN_ORIGIN_BOTTOM_LEFT: {
        out.set2F(mx, my);
        break;
      }
      case SCREEN_ORIGIN_TOP_LEFT: {
        out.set2F(mx, -my);
        break;
      }
    }
  }

  /**
   * @return The height of the region
   */

  public float getHeight()
  {
    return this.height;
  }

  /**
   * @return The current screen origin
   */

  public JCameraScreenOrigin getOrigin()
  {
    return this.origin;
  }

  /**
   * @return The width of the region
   */

  public float getWidth()
  {
    return this.width;
  }
}
