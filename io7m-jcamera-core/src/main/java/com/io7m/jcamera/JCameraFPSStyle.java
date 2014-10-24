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

import com.io7m.jnull.NullCheck;
import com.io7m.jranges.RangeCheck;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorReadable3FType;

/**
 * The default implementation of {@link JCameraFPSStyleType}.
 */

public final class JCameraFPSStyle implements JCameraFPSStyleType
{
  /**
   * @return A new FPS camera
   */

  public static JCameraFPSStyleType newCamera()
  {
    return new JCameraFPSStyle();
  }

  private boolean           clamp_horizontal;
  private float             clamp_horizontal_max;
  private float             clamp_horizontal_min;
  private boolean           derived_current;
  private final VectorM3F   derived_forward;
  private final MatrixM4x4F derived_matrix_r;
  private final MatrixM4x4F derived_matrix_t;
  private final VectorM3F   derived_right;
  private final VectorM3F   derived_up;
  private float             input_angle_around_horizontal;
  private float             input_angle_around_vertical;
  private final VectorM3F   input_position;
  private final VectorM3F   temporary;

  private JCameraFPSStyle()
  {
    this.input_position = new VectorM3F();
    this.input_angle_around_horizontal = 0.0f;
    this.input_angle_around_vertical = (float) Math.PI / 2.0f;

    this.derived_current = false;
    this.derived_up = new VectorM3F();
    this.derived_right = new VectorM3F();
    this.derived_forward = new VectorM3F();
    this.derived_matrix_r = new MatrixM4x4F();
    this.derived_matrix_t = new MatrixM4x4F();
    this.temporary = new VectorM3F();

    this.clamp_horizontal = true;
    this.clamp_horizontal_max = (float) (Math.PI / 64.0f) * 31.0f;
    this.clamp_horizontal_min = -this.clamp_horizontal_max;
  }

  @Override public void cameraClampHorizontalDisable()
  {
    this.clamp_horizontal = false;
    this.clamp_horizontal_max = Float.MAX_VALUE;
    this.clamp_horizontal_min = -Float.MAX_VALUE;
  }

  @Override public void cameraClampHorizontalEnable(
    final float min,
    final float max)
  {
    RangeCheck.checkGreaterDouble(max, "Maximum clamp", min, "Minimum clamp");
    this.clamp_horizontal = true;
    this.clamp_horizontal_max = max;
    this.clamp_horizontal_min = min;
  }

  @Override public VectorReadable3FType cameraGetForward()
  {
    this.deriveVectors();
    return this.derived_forward;
  }

  @Override public VectorReadable3FType cameraGetPosition()
  {
    return this.input_position;
  }

  @Override public VectorReadable3FType cameraGetRight()
  {
    this.deriveVectors();
    return this.derived_right;
  }

  @Override public VectorReadable3FType cameraGetUp()
  {
    this.deriveVectors();
    return this.derived_up;
  }

  @Override public void cameraMakeViewMatrix(
    final MatrixM4x4F.Context context,
    final MatrixM4x4F m)
  {
    NullCheck.notNull(context, "Context");
    NullCheck.notNull(m, "Matrix");

    this.deriveVectors();

    /**
     * Calculate basis vectors for rotated coordinate system.
     */

    final MatrixM4x4F r = this.derived_matrix_r;
    MatrixM4x4F.setIdentity(r);
    r.set(0, 0, this.derived_right.getXF());
    r.set(0, 1, this.derived_right.getYF());
    r.set(0, 2, this.derived_right.getZF());
    r.set(1, 0, this.derived_up.getXF());
    r.set(1, 1, this.derived_up.getYF());
    r.set(1, 2, this.derived_up.getZF());
    r.set(2, 0, -this.derived_forward.getXF());
    r.set(2, 1, -this.derived_forward.getYF());
    r.set(2, 2, -this.derived_forward.getZF());

    /**
     * Calculate translation matrix.
     */

    final MatrixM4x4F t = this.derived_matrix_t;
    MatrixM4x4F.setIdentity(t);
    this.temporary.set3F(
      -this.input_position.getXF(),
      -this.input_position.getYF(),
      -this.input_position.getZF());
    MatrixM4x4F.makeTranslation3F(this.temporary, this.derived_matrix_t);

    /**
     * Produce final transform.
     */

    MatrixM4x4F.multiply(this.derived_matrix_r, this.derived_matrix_t, m);
  }

  @Override public void cameraMoveForward(
    final float u)
  {
    this.deriveVectors();
    VectorM3F.scale(this.derived_forward, u, this.temporary);
    VectorM3F.addInPlace(this.input_position, this.temporary);
  }

  @Override public void cameraMoveRight(
    final float u)
  {
    this.deriveVectors();
    VectorM3F.scale(this.derived_right, u, this.temporary);
    VectorM3F.addInPlace(this.input_position, this.temporary);
  }

  @Override public void cameraMoveUp(
    final float u)
  {
    this.deriveVectors();
    VectorM3F.scale(this.derived_up, u, this.temporary);
    VectorM3F.addInPlace(this.input_position, this.temporary);
  }

  @Override public boolean cameraRotateAroundHorizontal(
    final float r)
  {
    this.derived_current = false;
    this.input_angle_around_horizontal += r;

    if (this.clamp_horizontal) {
      if (this.input_angle_around_horizontal > this.clamp_horizontal_max) {
        this.input_angle_around_horizontal = this.clamp_horizontal_max;
        return true;
      }
      if (this.input_angle_around_horizontal < this.clamp_horizontal_min) {
        this.input_angle_around_horizontal = this.clamp_horizontal_min;
        return true;
      }
    }

    return false;
  }

  @Override public void cameraRotateAroundVertical(
    final float r)
  {
    this.derived_current = false;
    this.input_angle_around_vertical += r;
  }

  /**
   * Set the position of the camera.
   *
   * @param v
   *          The position.
   */

  @Override public void cameraSetPosition(
    final VectorReadable3FType v)
  {
    VectorM3F.copy(NullCheck.notNull(v, "Input"), this.input_position);
  }

  /**
   * Set the position of the camera.
   *
   * @param x
   *          The x coordinate.
   * @param y
   *          The y coordinate.
   * @param z
   *          The z coordinate.
   */

  @Override public void cameraSetPosition3f(
    final float x,
    final float y,
    final float z)
  {
    this.input_position.set3F(x, y, z);
  }

  /**
   * Derive the forward, right, and up vectors based on the current state of
   * the camera.
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

  @Override public VectorReadable3FType getPosition()
  {
    return this.input_position;
  }

  @Override public String toString()
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
