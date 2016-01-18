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
import com.io7m.jranges.RangeCheck;
import com.io7m.jtensors.Matrix4x4FType;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.parameterized.PMatrix4x4FType;

/**
 * The default implementation of {@link JCameraFPSStyleType}.
 */

@EqualityStructural
public final class JCameraFPSStyle implements
  JCameraFPSStyleType
{
  private final JCameraSignallingClamp clamp;
  private final VectorM3F              derived_forward;
  private final VectorM3F              derived_right;
  private final VectorM3F              derived_up;
  private final VectorM3F              input_position;
  private final VectorM3F              temporary;
  private       boolean                clamp_horizontal;
  private       float                  clamp_horizontal_max;
  private       float                  clamp_horizontal_min;
  private       boolean                derived_current;
  private       float                  input_angle_around_horizontal;
  private       float                  input_angle_around_vertical;
  private JCameraFPSStyle()
  {
    this.input_position = new VectorM3F();
    this.input_angle_around_horizontal = 0.0f;
    this.input_angle_around_vertical = (float) Math.PI / 2.0f;

    this.derived_current = false;
    this.derived_up = new VectorM3F();
    this.derived_right = new VectorM3F();
    this.derived_forward = new VectorM3F();
    this.temporary = new VectorM3F();

    this.clamp_horizontal = true;
    this.clamp_horizontal_max = (float) (Math.PI / 64.0f) * 31.0f;
    this.clamp_horizontal_min = -this.clamp_horizontal_max;

    this.clamp = new JCameraSignallingClamp();
  }

  /**
   * @return A new FPS camera
   */

  public static JCameraFPSStyleType newCamera()
  {
    return new JCameraFPSStyle();
  }

  /**
   * @param c An existing camera
   *
   * @return A new FPS camera based on the given camera.
   */

  public static JCameraFPSStyleType newCameraFrom(
    final JCameraFPSStyleReadableType c)
  {
    final JCameraFPSStyle r = new JCameraFPSStyle();
    r.cameraSetAngleAroundHorizontal(c.cameraGetAngleAroundHorizontal());
    r.cameraSetAngleAroundVertical(c.cameraGetAngleAroundVertical());
    r.cameraSetPosition(c.cameraGetPosition());
    return r;
  }

  @Override
  public void cameraClampHorizontalDisable()
  {
    this.clamp_horizontal = false;
    this.clamp_horizontal_max = Float.MAX_VALUE;
    this.clamp_horizontal_min = -Float.MAX_VALUE;
  }

  @Override
  public void cameraClampHorizontalEnable(
    final float min,
    final float max)
  {
    RangeCheck.checkGreaterDouble(max, "Maximum clamp", min, "Minimum clamp");
    this.clamp_horizontal = true;
    this.clamp_horizontal_max = max;
    this.clamp_horizontal_min = min;
  }

  @Override
  public float cameraGetAngleAroundHorizontal()
  {
    return this.input_angle_around_horizontal;
  }

  @Override
  public float cameraGetAngleAroundVertical()
  {
    return this.input_angle_around_vertical;
  }

  @Override
  public VectorReadable3FType cameraGetForward()
  {
    this.deriveVectors();
    return this.derived_forward;
  }

  @Override
  public VectorReadable3FType cameraGetPosition()
  {
    return this.input_position;
  }

  @Override
  public VectorReadable3FType cameraGetRight()
  {
    this.deriveVectors();
    return this.derived_right;
  }

  @Override
  public VectorReadable3FType cameraGetUp()
  {
    this.deriveVectors();
    return this.derived_up;
  }

  @Override
  public JCameraFPSStyleSnapshot cameraMakeSnapshot()
  {
    this.deriveVectors();
    return new JCameraFPSStyleSnapshot(
      new VectorI3F(this.derived_forward),
      new VectorI3F(this.derived_right),
      new VectorI3F(this.derived_up),
      this.input_angle_around_horizontal,
      this.input_angle_around_vertical,
      new VectorI3F(this.input_position));
  }

  @Override
  public void cameraMakeViewMatrix(
    final JCameraContext ctx,
    final Matrix4x4FType m)
  {
    this.deriveVectors();
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
    this.deriveVectors();
    JCameraViewMatrix.makeViewPMatrix(
      ctx,
      m,
      this.cameraGetPosition(),
      this.cameraGetRight(),
      this.cameraGetUp(),
      this.cameraGetForward());
  }

  @Override
  public void cameraMoveForward(
    final float u)
  {
    this.deriveVectors();
    VectorM3F.scale(this.derived_forward, u, this.temporary);
    VectorM3F.addInPlace(this.input_position, this.temporary);
  }

  @Override
  public void cameraMoveRight(
    final float u)
  {
    this.deriveVectors();
    VectorM3F.scale(this.derived_right, u, this.temporary);
    VectorM3F.addInPlace(this.input_position, this.temporary);
  }

  @Override
  public void cameraMoveUp(
    final float u)
  {
    this.deriveVectors();
    VectorM3F.scale(this.derived_up, u, this.temporary);
    VectorM3F.addInPlace(this.input_position, this.temporary);
  }

  @Override
  public boolean cameraRotateAroundHorizontal(
    final float r)
  {
    this.derived_current = false;
    this.input_angle_around_horizontal += r;
    return this.clampHorizontal();
  }

  @Override
  public void cameraRotateAroundVertical(
    final float r)
  {
    this.derived_current = false;
    this.input_angle_around_vertical += r;
  }

  @Override
  public void cameraSetAngleAroundHorizontal(
    final float h)
  {
    this.derived_current = false;
    this.input_angle_around_horizontal = h;
    this.clampHorizontal();
  }

  @Override
  public void cameraSetAngleAroundVertical(
    final float v)
  {
    this.input_angle_around_vertical = v;
  }

  /**
   * Set the position of the camera.
   *
   * @param v The position.
   */

  @Override
  public void cameraSetPosition(
    final VectorReadable3FType v)
  {
    VectorM3F.copy(NullCheck.notNull(v, "Input"), this.input_position);
  }

  /**
   * Set the position of the camera.
   *
   * @param x The x coordinate.
   * @param y The y coordinate.
   * @param z The z coordinate.
   */

  @Override
  public void cameraSetPosition3f(
    final float x,
    final float y,
    final float z)
  {
    this.input_position.set3F(x, y, z);
  }

  private boolean clampHorizontal()
  {
    if (this.clamp_horizontal) {
      this.clamp.clamp(
        this.input_angle_around_horizontal,
        this.clamp_horizontal_min,
        this.clamp_horizontal_max);
      this.input_angle_around_horizontal = this.clamp.getValue();
      return this.clamp.isClamped();
    }

    return false;
  }

  /**
   * Derive the forward, right, and up vectors based on the current state of the
   * camera.
   */

  private void deriveVectors()
  {
    if (this.derived_current == false) {
      final float v = this.input_angle_around_vertical;
      final float h = this.input_angle_around_horizontal;

      {
        final float x = (float) (Math.cos(h) * Math.cos(v));
        final float y = (float) Math.sin(h);
        final float z = (float) (Math.cos(h) * Math.sin(v));
        this.derived_forward.set3F(x, y, -z);
        VectorM3F.normalizeInPlace(this.derived_forward);
      }

      {
        final double po2 = Math.PI / 2.0f;
        final double vr = v - po2;
        final float x = (float) (Math.cos(h) * Math.cos(vr));
        final float y = 0.0f;
        final float z = (float) (Math.cos(h) * Math.sin(vr));
        this.derived_right.set3F(x, y, -z);
        VectorM3F.normalizeInPlace(this.derived_right);
      }

      VectorM3F.crossProduct(
        this.derived_right,
        this.derived_forward,
        this.derived_up);

      this.derived_current = true;
    }
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
    final JCameraFPSStyle other = (JCameraFPSStyle) obj;
    return (this.clamp_horizontal == other.clamp_horizontal)
      && (Float.floatToIntBits(this.clamp_horizontal_max) == Float
      .floatToIntBits(other.clamp_horizontal_max))
      && (Float.floatToIntBits(this.clamp_horizontal_min) == Float
      .floatToIntBits(other.clamp_horizontal_min))
      && (Float.floatToIntBits(this.input_angle_around_horizontal) == Float
      .floatToIntBits(other.input_angle_around_horizontal))
      && (Float.floatToIntBits(this.input_angle_around_vertical) == Float
      .floatToIntBits(other.input_angle_around_vertical))
      && this.input_position.equals(other.input_position);
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + (this.clamp_horizontal ? 1231 : 1237);
    result =
      (prime * result) + Float.floatToIntBits(this.clamp_horizontal_max);
    result =
      (prime * result) + Float.floatToIntBits(this.clamp_horizontal_min);
    result =
      (prime * result)
        + Float.floatToIntBits(this.input_angle_around_horizontal);
    result =
      (prime * result)
        + Float.floatToIntBits(this.input_angle_around_vertical);
    result = (prime * result) + this.input_position.hashCode();
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder b = new StringBuilder();
    b.append("[JCameraFPSStyle input_angle_around_horizontal=");
    b.append(this.input_angle_around_horizontal);
    b.append(" input_angle_around_vertical=");
    b.append(this.input_angle_around_vertical);
    b.append(" input_position=");
    b.append(this.input_position);
    b.append("]");
    final String r = b.toString();
    assert r != null;
    return r;
  }
}
