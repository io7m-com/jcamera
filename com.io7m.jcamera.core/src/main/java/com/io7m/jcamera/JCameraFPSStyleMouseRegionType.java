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

import com.io7m.jranges.RangeCheck;
import com.io7m.junreachable.UnreachableCodeException;
import org.immutables.value.Value;

/**
 * An immutable rectangular region that maps mouse positions to movements.
 */

@JCameraImmutableStyleType
@Value.Immutable
public interface JCameraFPSStyleMouseRegionType
{
  /**
   * @return The current screen origin
   */

  @Value.Parameter(order = 0)
  JCameraScreenOrigin origin();

  /**
   * @return The width of the region
   */

  @Value.Parameter(order = 1)
  double width();

  /**
   * @return The height of the region
   */

  @Value.Parameter(order = 2)
  double height();

  /**
   * @return The X coordinate of the center of the region
   */

  @Value.Derived
  default double centerX()
  {
    return this.width() / 2.0;
  }

  /**
   * @return The Y coordinate of the center of the region
   */

  @Value.Derived
  default double centerY()
  {
    return this.height() / 2.0;
  }

  /**
   * Return the region-space coefficients for the screen-space position {@code
   * (x, y)}.
   *
   * @param x The x coordinate
   * @param y The y coordinate
   *
   * @return Rotation coefficients for the given screen-space position
   */

  default JCameraRotationCoefficients coefficients(
    final double x,
    final double y)
  {
    final double rot_v = ((x - this.centerX()) / this.width()) * 2.0;
    final double rot_h = ((y - this.centerY()) / this.height()) * 2.0;

    switch (this.origin()) {
      case SCREEN_ORIGIN_BOTTOM_LEFT: {
        return JCameraRotationCoefficients.builder()
          .setHorizontal(rot_h)
          .setVertical(-rot_v)
          .build();
      }
      case SCREEN_ORIGIN_TOP_LEFT: {
        return JCameraRotationCoefficients.builder()
          .setHorizontal(-rot_h)
          .setVertical(-rot_v)
          .build();
      }
    }

    throw new UnreachableCodeException();
  }

  /**
   * Check preconditions for the type.
   */

  @Value.Check
  default void checkPreconditions()
  {
    RangeCheck.checkGreaterEqualDouble(
      this.height(),
      "Height",
      2.0,
      "Minimum height");

    RangeCheck.checkGreaterEqualDouble(
      this.width(),
      "Width",
      2.0,
      "Minimum width");
  }
}
