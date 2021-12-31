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

package com.io7m.jcamera.examples.jogl;

import com.io7m.jranges.RangeCheck;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4D;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * Functions for producing projection matrices.
 */

public final class ProjectionMatrix
{
  private ProjectionMatrix()
  {
    throw new UnreachableCodeException();
  }

  /**
   * <p>Calculate a matrix that produces a perspective projection. The {@code
   * (x_min, y_min, z_near)} and {@code (x_max, y_max, z_near)} parameters
   * specify the points on the near clipping plane that are mapped to the
   * lower-left and upper-right corners of the window, respectively, assuming
   * that the eye is located at {@code (0, 0, 0)}. The {@code z_far} parameter
   * specifies the location of the far clipping plane.</p>
   *
   * <p>Note that iff {@code z_far &gt;= Double.POSITIVE_INFINITY}, the
   * function produces an "infinite projection matrix", suitable for use in code
   * that deals with shadow volumes.</p>
   *
   * <p>The function assumes a right-handed coordinate system.</p>
   *
   * <p>See
   * <a href="http://http.developer.nvidia.com/GPUGems/gpugems_ch09.html">GPU
   * Gems</a></p>
   *
   * @param x_min  The minimum X clip plane
   * @param x_max  The maximum X clip plane
   * @param y_min  The minimum Y clip plane
   * @param y_max  The maximum Y clip plane
   * @param z_near The near Z clip plane
   * @param z_far  The far Z clip plane
   *
   * @return A frustum projection matrix
   */

  public static Matrix4x4D frustumProjectionRH(
    final double x_min,
    final double x_max,
    final double y_min,
    final double y_max,
    final double z_near,
    final double z_far)
  {
    RangeCheck.checkGreaterEqualDouble(
      z_near,
      "Near Z",
      0.0,
      "Minimum Z distance");
    RangeCheck.checkLessDouble(z_near, "Near Z", z_far, "Far Z");

    final double r2c2;
    final double r2c3;
    if (z_far >= Double.POSITIVE_INFINITY) {
      r2c2 = -1.0;
      r2c3 = -2.0 * z_near;
    } else {
      r2c2 = -((z_far + z_near) / (z_far - z_near));
      r2c3 = -(2.0 * z_far * z_near / (z_far - z_near));
    }

    final double r0c0 = 2.0 * z_near / (x_max - x_min);
    final double r0c2 = (x_max + x_min) / (x_max - x_min);
    final double r1c1 = 2.0 * z_near / (y_max - y_min);
    final double r1c2 = (y_max + y_min) / (y_max - y_min);

    return Matrix4x4D.of(
      r0c0, 0.0, r0c2, 0.0,
      0.0, r1c1, r1c2, 0.0,
      0.0, 0.0, r2c2, r2c3,
      0.0, 0.0, -1.0, 0.0);
  }

  /**
   * <p>Calculate a projection matrix that produces an orthographic projection
   * based on the given clipping plane coordinates.</p>
   *
   * <p>The function assumes a right-handed coordinate system.</p>
   *
   * @param x_min  The left clipping plane coordinate
   * @param x_max  The right clipping plane coordinate
   * @param y_min  The bottom clipping plane coordinate
   * @param y_max  The top clipping plane coordinate
   * @param z_near The near clipping plane coordinate
   * @param z_far  The far clipping plane coordinate
   *
   * @return An orthographic projection matrix
   */

  public static Matrix4x4D orthographicProjectionRH(
    final double x_min,
    final double x_max,
    final double y_min,
    final double y_max,
    final double z_near,
    final double z_far)
  {
    final double rml = x_max - x_min;
    final double rpl = x_max + x_min;
    final double tmb = y_max - y_min;
    final double tpb = y_max + y_min;
    final double fmn = z_far - z_near;
    final double fpn = z_far + z_near;

    final double r0c0 = 2.0 / rml;
    final double r0c3 = -(rpl / rml);
    final double r1c1 = 2.0 / tmb;
    final double r1c3 = -(tpb / tmb);
    final double r2c2 = -2.0 / fmn;
    final double r2c3 = -(fpn / fmn);

    return Matrix4x4D.of(
      r0c0, 0.0, 0.0, r0c3,
      0.0, r1c1, 0.0, r1c3,
      0.0, 0.0, r2c2, r2c3,
      0.0, 0.0, 0.0, 1.0);
  }

  /**
   * <p>Calculate a matrix that will produce a perspective projection based on
   * the given view frustum parameters, the aspect ratio of the viewport and a
   * given horizontal field of view in radians. Note that {@code fov_radians}
   * represents the full horizontal field of view: the angle at the base of the
   * triangle formed by the frustum on the {@code x/z} plane.</p>
   *
   * <p>Note that iff {@code z_far &gt;= Double.POSITIVE_INFINITY}, the
   * function produces an "infinite projection matrix", suitable for use in code
   * that deals with shadow volumes.</p>
   *
   * <p>The function assumes a right-handed coordinate system.</p>
   *
   * <p>See
   * <a href="http://http.developer.nvidia.com/GPUGems/gpugems_ch09.html">GPU
   * Gems</a></p>
   *
   * @param z_near         The near clipping plane coordinate
   * @param z_far          The far clipping plane coordinate
   * @param aspect         The aspect ratio of the viewport; the width divided
   *                       by the height. For example, an aspect ratio of 2.0
   *                       indicates a viewport twice as wide as it is high
   * @param horizontal_fov The horizontal field of view in radians
   *
   * @return A perspective projection matrix
   */

  public static Matrix4x4D perspectiveProjectionRH(
    final double z_near,
    final double z_far,
    final double aspect,
    final double horizontal_fov)
  {
    final double x_max = z_near * StrictMath.tan(horizontal_fov / 2.0);
    final double x_min = -x_max;
    final double y_max = x_max / aspect;
    final double y_min = -y_max;
    return frustumProjectionRH(x_min, x_max, y_min, y_max, z_near, z_far);
  }
}
