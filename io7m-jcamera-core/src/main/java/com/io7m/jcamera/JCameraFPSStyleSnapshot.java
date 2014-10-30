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
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorReadable3FType;

/**
 * An immutable snapshot of a {@link JCameraFPSStyle}.
 */

@EqualityStructural public final class JCameraFPSStyleSnapshot implements
  JCameraFPSStyleReadableType
{
  /**
   * Linearly interpolate between two camera snapshots.
   *
   * @param x
   *          The first snapshot
   * @param y
   *          The second snapshot
   * @param a
   *          The interpolation value
   * @return A value between <code>x</code> and <code>y</code>
   */

  public static JCameraFPSStyleSnapshot interpolate(
    final JCameraFPSStyleSnapshot x,
    final JCameraFPSStyleSnapshot y,
    final float a)
  {
    final VectorI3F r_f =
      VectorI3F.interpolateLinear(
        x.cameraGetForward(),
        y.cameraGetForward(),
        a);
    final VectorI3F r_u =
      VectorI3F.interpolateLinear(x.cameraGetUp(), y.cameraGetUp(), a);
    final VectorI3F r_r =
      VectorI3F.interpolateLinear(x.cameraGetRight(), y.cameraGetRight(), a);
    final VectorI3F r_p =
      VectorI3F.interpolateLinear(
        x.cameraGetPosition(),
        y.cameraGetPosition(),
        a);
    final float r_h =
      ((1.0f - a) * x.angle_around_horizontal)
        + (a * y.angle_around_horizontal);
    final float r_v =
      ((1.0f - a) * x.angle_around_vertical) + (a * y.angle_around_vertical);
    return new JCameraFPSStyleSnapshot(r_f, r_r, r_u, r_h, r_v, r_p);
  }

  private final float     angle_around_horizontal;
  private final float     angle_around_vertical;
  private final VectorI3F forward;
  private final VectorI3F position;
  private final VectorI3F right;

  private final VectorI3F up;

  JCameraFPSStyleSnapshot(
    final VectorI3F in_forward,
    final VectorI3F in_right,
    final VectorI3F in_up,
    final float in_angle_around_horizontal,
    final float in_angle_around_vertical,
    final VectorI3F in_position)
  {
    this.forward = NullCheck.notNull(in_forward, "Forward");
    this.right = NullCheck.notNull(in_right, "Right");
    this.up = NullCheck.notNull(in_up, "Up");
    this.angle_around_horizontal = in_angle_around_horizontal;
    this.angle_around_vertical = in_angle_around_vertical;
    this.position = NullCheck.notNull(in_position, "Position");
  }

  @Override public float cameraGetAngleAroundHorizontal()
  {
    return this.angle_around_horizontal;
  }

  @Override public float cameraGetAngleAroundVertical()
  {
    return this.angle_around_vertical;
  }

  @Override public VectorReadable3FType cameraGetForward()
  {
    return this.forward;
  }

  @Override public VectorReadable3FType cameraGetPosition()
  {
    return this.position;
  }

  @Override public VectorReadable3FType cameraGetRight()
  {
    return this.right;
  }

  @Override public VectorReadable3FType cameraGetUp()
  {
    return this.up;
  }

  @Override public boolean equals(
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
    final JCameraFPSStyleSnapshot other = (JCameraFPSStyleSnapshot) obj;
    return (Float.floatToIntBits(this.angle_around_horizontal) == Float
      .floatToIntBits(other.angle_around_horizontal))
      && (Float.floatToIntBits(this.angle_around_vertical) == Float
        .floatToIntBits(other.angle_around_vertical))
      && this.forward.equals(other.forward)
      && this.position.equals(other.position)
      && this.right.equals(other.right)
      && this.up.equals(other.up);
  }

  @Override public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result =
      (prime * result) + Float.floatToIntBits(this.angle_around_horizontal);
    result =
      (prime * result) + Float.floatToIntBits(this.angle_around_vertical);
    result = (prime * result) + this.forward.hashCode();
    result = (prime * result) + this.position.hashCode();
    result = (prime * result) + this.right.hashCode();
    result = (prime * result) + this.up.hashCode();
    return result;
  }
}
