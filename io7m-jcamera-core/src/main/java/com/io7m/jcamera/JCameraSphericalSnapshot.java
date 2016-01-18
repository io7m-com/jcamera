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

import com.io7m.jequality.annotations.EqualityStructural;
import com.io7m.jinterp.InterpolationF;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.Matrix4x4FType;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.parameterized.PMatrix4x4FType;

/**
 * An immutable snapshot of a {@link JCameraSphericalType}.
 */

@EqualityStructural
public final class JCameraSphericalSnapshot implements
  JCameraSphericalReadableType,
  JCameraReadableSnapshotType
{
  private final float     angle_heading;
  private final float     angle_incline;
  private final VectorI3F forward;
  private final VectorI3F forward_on_xz;
  private final VectorI3F position;
  private final float     radius;
  private final VectorI3F right;
  private final VectorI3F target_position;
  private final VectorI3F up;
  JCameraSphericalSnapshot(
    final VectorI3F in_forward,
    final VectorI3F in_right,
    final VectorI3F in_up,
    final float in_angle_incline,
    final float in_angle_heading,
    final VectorI3F in_position,
    final float in_radius,
    final VectorI3F in_target_position,
    final VectorI3F in_forward_on_xz)
  {
    this.forward = NullCheck.notNull(in_forward, "Forward");
    this.right = NullCheck.notNull(in_right, "Right");
    this.up = NullCheck.notNull(in_up, "Up");
    this.angle_incline = in_angle_incline;
    this.angle_heading = in_angle_heading;
    this.position = NullCheck.notNull(in_position, "Position");
    this.radius = in_radius;
    this.target_position =
      NullCheck.notNull(in_target_position, "Target position");
    this.forward_on_xz =
      NullCheck.notNull(in_forward_on_xz, "Forward on X/Z");
  }

  /**
   * Linearly interpolate between two camera snapshots.
   *
   * @param x The first snapshot
   * @param y The second snapshot
   * @param a The interpolation value
   *
   * @return A value between {@code x} and {@code y}
   */

  public static JCameraSphericalSnapshot interpolate(
    final JCameraSphericalSnapshot x,
    final JCameraSphericalSnapshot y,
    final float a)
  {
    final VectorI3F in_forward =
      VectorI3F.interpolateLinear(x.forward, y.forward, a);
    final VectorI3F in_right =
      VectorI3F.interpolateLinear(x.right, y.right, a);
    final VectorI3F in_up = VectorI3F.interpolateLinear(x.up, y.up, a);
    final float in_angle_incline =
      InterpolationF.interpolateLinear(x.angle_incline, y.angle_incline, a);
    final float in_angle_heading =
      InterpolationF.interpolateLinear(x.angle_heading, y.angle_heading, a);
    final VectorI3F in_position =
      VectorI3F.interpolateLinear(x.position, y.position, a);
    final float in_radius =
      InterpolationF.interpolateLinear(x.radius, y.radius, a);
    final VectorI3F in_target_position =
      VectorI3F.interpolateLinear(x.target_position, y.target_position, a);
    final VectorI3F in_forward_on_xz =
      VectorI3F.interpolateLinear(x.forward_on_xz, y.forward_on_xz, a);

    return new JCameraSphericalSnapshot(
      in_forward,
      in_right,
      in_up,
      in_angle_incline,
      in_angle_heading,
      in_position,
      in_radius,
      in_target_position,
      in_forward_on_xz);
  }

  @Override
  public float cameraGetAngleHeading()
  {
    return this.angle_heading;
  }

  @Override
  public float cameraGetAngleIncline()
  {
    return this.angle_incline;
  }

  @Override
  public VectorReadable3FType cameraGetForward()
  {
    return this.forward;
  }

  @Override
  public VectorReadable3FType cameraGetForwardProjectedOnXZ()
  {
    return this.forward_on_xz;
  }

  @Override
  public VectorReadable3FType cameraGetPosition()
  {
    return this.position;
  }

  @Override
  public VectorReadable3FType cameraGetRight()
  {
    return this.right;
  }

  @Override
  public VectorReadable3FType cameraGetTargetPosition()
  {
    return this.target_position;
  }

  @Override
  public VectorReadable3FType cameraGetUp()
  {
    return this.up;
  }

  @Override
  public float cameraGetZoom()
  {
    return this.radius;
  }

  @Override
  public JCameraSphericalSnapshot cameraMakeSnapshot()
  {
    return this;
  }

  @Override
  public void cameraMakeViewMatrix(
    final JCameraContext ctx,
    final Matrix4x4FType m)
  {
    JCameraViewMatrix.makeViewMatrix(
      ctx,
      m,
      this.cameraGetPosition(),
      this.cameraGetRight(),
      this.cameraGetUp(),
      this.cameraGetForward());
  }

  @Override
  public <T0, T1> void cameraMakeViewPMatrix(
    final JCameraContext ctx,
    final PMatrix4x4FType<T0, T1> m)
  {
    JCameraViewMatrix.makeViewPMatrix(
      ctx,
      m,
      this.cameraGetPosition(),
      this.cameraGetRight(),
      this.cameraGetUp(),
      this.cameraGetForward());
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
    final JCameraSphericalSnapshot other = (JCameraSphericalSnapshot) obj;
    return (Float.floatToIntBits(this.angle_heading) == Float
      .floatToIntBits(other.angle_heading))
      && (Float.floatToIntBits(this.angle_incline) == Float
      .floatToIntBits(other.angle_incline))
      && (Float.floatToIntBits(this.radius) == Float
      .floatToIntBits(other.radius))
      && (this.forward.equals(other.forward))
      && (this.forward_on_xz.equals(other.forward_on_xz))
      && (this.target_position.equals(other.target_position))
      && (this.position.equals(other.position))
      && (this.right.equals(other.right))
      && (this.up.equals(other.up));
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + Float.floatToIntBits(this.angle_heading);
    result = (prime * result) + Float.floatToIntBits(this.angle_incline);
    result = (prime * result) + Float.floatToIntBits(this.radius);
    result = (prime * result) + this.forward.hashCode();
    result = (prime * result) + this.forward_on_xz.hashCode();
    result = (prime * result) + this.target_position.hashCode();
    result = (prime * result) + this.position.hashCode();
    result = (prime * result) + this.right.hashCode();
    result = (prime * result) + this.up.hashCode();
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder b = new StringBuilder();
    b.append("[JCameraSphericalSnapshot forward=");
    b.append(this.forward);
    b.append(" forward_on_xz=");
    b.append(this.forward_on_xz);
    b.append(" position=");
    b.append(this.position);
    b.append(" right=");
    b.append(this.right);
    b.append(" up=");
    b.append(this.up);
    b.append(" angle_heading=");
    b.append(this.angle_heading);
    b.append(" angle_incline=");
    b.append(this.angle_incline);
    b.append(" radius=");
    b.append(this.radius);
    b.append(" target_position=");
    b.append(this.target_position);
    b.append("]");
    final String r = b.toString();
    assert r != null;
    return r;
  }
}
