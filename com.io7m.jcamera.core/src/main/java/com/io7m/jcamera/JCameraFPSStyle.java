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
import com.io7m.jranges.RangeCheck;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix4x4D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors3D;

import java.util.Objects;

/**
 * The default implementation of {@link JCameraFPSStyleType}.
 */

@EqualityStructural
public final class JCameraFPSStyle implements JCameraFPSStyleType
{
  private final JCameraSignallingClamp clamp;
  private Vector3D derived_forward;
  private Vector3D derived_right;
  private Vector3D derived_up;
  private Vector3D input_position;
  private boolean clamp_horizontal;
  private double clamp_horizontal_max;
  private double clamp_horizontal_min;
  private boolean derived_current;
  private double input_angle_around_horizontal;
  private double input_angle_around_vertical;

  private JCameraFPSStyle()
  {
    this.input_position = Vectors3D.zero();
    this.input_angle_around_horizontal = 0.0;
    this.input_angle_around_vertical = Math.PI / 2.0;

    this.derived_current = false;
    this.derived_up = Vectors3D.zero();
    this.derived_right = Vectors3D.zero();
    this.derived_forward = Vectors3D.zero();

    this.clamp_horizontal = true;
    this.clamp_horizontal_max = Math.PI / 64.0 * 31.0;
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
    this.clamp_horizontal_max = Double.MAX_VALUE;
    this.clamp_horizontal_min = -Double.MAX_VALUE;
  }

  @Override
  public void cameraClampHorizontalEnable(
    final double min,
    final double max)
  {
    RangeCheck.checkGreaterDouble(
      max, "Maximum clamp",
      min, "Minimum clamp");
    this.clamp_horizontal = true;
    this.clamp_horizontal_max = max;
    this.clamp_horizontal_min = min;
  }

  @Override
  public double cameraGetAngleAroundHorizontal()
  {
    return this.input_angle_around_horizontal;
  }

  @Override
  public double cameraGetAngleAroundVertical()
  {
    return this.input_angle_around_vertical;
  }

  @Override
  public Vector3D cameraGetForward()
  {
    this.deriveVectors();
    return this.derived_forward;
  }

  @Override
  public Vector3D cameraGetPosition()
  {
    return this.input_position;
  }

  @Override
  public Vector3D cameraGetRight()
  {
    this.deriveVectors();
    return this.derived_right;
  }

  @Override
  public Vector3D cameraGetUp()
  {
    this.deriveVectors();
    return this.derived_up;
  }

  @Override
  public Matrix4x4D cameraMakeViewMatrix()
  {
    this.deriveVectors();
    return JCameraViewMatrix.makeViewMatrix(
      this.cameraGetPosition(),
      this.cameraGetRight(),
      this.cameraGetUp(),
      this.cameraGetForward());
  }

  @Override
  public <T0, T1> PMatrix4x4D<T0, T1> cameraMakeViewPMatrix()
  {
    this.deriveVectors();
    return JCameraViewMatrix.makeViewPMatrix(
      this.cameraGetPosition(),
      this.cameraGetRight(),
      this.cameraGetUp(),
      this.cameraGetForward());
  }

  @Override
  public void cameraMoveForward(
    final double u)
  {
    this.deriveVectors();
    this.input_position =
      Vectors3D.add(
        this.input_position,
        Vectors3D.scale(this.derived_forward, u));
  }

  @Override
  public void cameraMoveRight(
    final double u)
  {
    this.deriveVectors();
    this.input_position =
      Vectors3D.add(
        this.input_position,
        Vectors3D.scale(this.derived_right, u));
  }

  @Override
  public void cameraMoveUp(
    final double u)
  {
    this.deriveVectors();
    this.input_position =
      Vectors3D.add(
        this.input_position,
        Vectors3D.scale(this.derived_up, u));
  }

  @Override
  public boolean cameraRotateAroundHorizontal(
    final double r)
  {
    this.derived_current = false;
    this.input_angle_around_horizontal += r;
    return this.clampHorizontal();
  }

  @Override
  public void cameraRotateAroundVertical(
    final double r)
  {
    this.derived_current = false;
    this.input_angle_around_vertical += r;
  }

  @Override
  public void cameraSetAngleAroundHorizontal(
    final double h)
  {
    this.derived_current = false;
    this.input_angle_around_horizontal = h;
    this.clampHorizontal();
  }

  @Override
  public void cameraSetAngleAroundVertical(
    final double v)
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
    final Vector3D v)
  {
    this.input_position = Objects.requireNonNull(v, "Input");
  }

  /**
   * Set the position of the camera.
   *
   * @param x The x coordinate.
   * @param y The y coordinate.
   * @param z The z coordinate.
   */

  @Override
  public void cameraSetPosition3(
    final double x,
    final double y,
    final double z)
  {
    this.input_position = Vector3D.of(x, y, z);
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
    if (!this.derived_current) {
      final double v = this.input_angle_around_vertical;
      final double h = this.input_angle_around_horizontal;

      {
        final double x = Math.cos(h) * Math.cos(v);
        final double y = Math.sin(h);
        final double z = Math.cos(h) * Math.sin(v);
        this.derived_forward = Vectors3D.normalize(Vector3D.of(x, y, -z));
      }

      {
        final double po2 = Math.PI / 2.0;
        final double vr = v - po2;
        final double x = Math.cos(h) * Math.cos(vr);
        final double y = 0.0;
        final double z = Math.cos(h) * Math.sin(vr);
        this.derived_right = Vectors3D.normalize(Vector3D.of(x, y, -z));
      }

      this.derived_up =
        Vectors3D.crossProduct(this.derived_right, this.derived_forward);
      this.derived_current = true;
    }
  }

  @Override
  public boolean equals(final Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    final JCameraFPSStyle that = (JCameraFPSStyle) o;
    return this.clamp_horizontal == that.clamp_horizontal
      && Double.compare(
      that.clamp_horizontal_max,
      this.clamp_horizontal_max) == 0
      && Double.compare(
      that.clamp_horizontal_min,
      this.clamp_horizontal_min) == 0
      && Double.compare(
      that.input_angle_around_horizontal,
      this.input_angle_around_horizontal) == 0
      && Double.compare(
      that.input_angle_around_vertical,
      this.input_angle_around_vertical) == 0
      && this.input_position.equals(that.input_position);
  }

  @Override
  public int hashCode()
  {
    int result;
    long temp;
    result = this.input_position.hashCode();
    result = 31 * result + (this.clamp_horizontal ? 1 : 0);
    temp = Double.doubleToLongBits(this.clamp_horizontal_max);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.clamp_horizontal_min);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.input_angle_around_horizontal);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.input_angle_around_vertical);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder b = new StringBuilder(128);
    b.append("[JCameraFPSStyle input_angle_around_horizontal=");
    b.append(this.input_angle_around_horizontal);
    b.append(" input_angle_around_vertical=");
    b.append(this.input_angle_around_vertical);
    b.append(" input_position=");
    b.append(this.input_position);
    b.append("]");
    return b.toString();
  }
}
