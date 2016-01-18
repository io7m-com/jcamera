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
 * The default implementation of {@link JCameraSphericalType}.
 */

@EqualityStructural
public final class JCameraSpherical implements
  JCameraSphericalType
{
  private static final VectorReadable3FType AXIS_Y;

  static {
    AXIS_Y = new VectorI3F(0.0f, 1.0f, 0.0f);
  }

  private final JCameraSignallingClamp clamp;
  private final VectorM3F              derived_forward;
  private final VectorM3F              derived_forward_on_xz;
  private final VectorM3F              derived_position;
  private final VectorM3F              derived_right;
  private final VectorM3F              derived_up;
  private final VectorM3F              input_target_position;
  private final VectorM3F              temporary;
  private       boolean                clamp_incline;
  private       float                  clamp_incline_max;
  private       float                  clamp_incline_min;
  private       boolean                clamp_radius;
  private       float                  clamp_radius_max;
  private       float                  clamp_radius_min;
  private       boolean                derived_current;
  private       float                  input_heading;
  private       float                  input_incline;
  private       float                  input_radius;

  private JCameraSpherical()
  {
    this.input_target_position = new VectorM3F();
    this.input_incline = 0.0f;
    this.input_heading = (float) -(Math.PI / 2.0f);
    this.input_radius = 8.0f;

    this.derived_current = false;
    this.derived_up = new VectorM3F();
    this.derived_right = new VectorM3F();
    this.derived_forward = new VectorM3F();
    this.derived_forward_on_xz = new VectorM3F();
    this.derived_position = new VectorM3F();
    this.temporary = new VectorM3F();

    this.clamp_incline = true;
    this.clamp_incline_max = 1.4f;
    this.clamp_incline_min = 0.0f;

    this.clamp_radius = true;
    this.clamp_radius_min = 1.0f;
    this.clamp_radius_max = Float.MAX_VALUE;

    this.clamp = new JCameraSignallingClamp();
  }

  /**
   * @return A new spherical camera.
   */

  public static JCameraSphericalType newCamera()
  {
    return new JCameraSpherical();
  }

  /**
   * @param c An existing camera
   *
   * @return A new camera based on the given camera.
   */

  public static JCameraSphericalType newCameraFrom(
    final JCameraSphericalReadableType c)
  {
    final JCameraSphericalType r = new JCameraSpherical();
    r.cameraSetAngleHeading(c.cameraGetAngleHeading());
    r.cameraSetAngleIncline(c.cameraGetAngleIncline());
    r.cameraSetTargetPosition(c.cameraGetTargetPosition());
    r.cameraSetZoom(c.cameraGetZoom());
    return r;
  }

  @Override
  public void cameraClampInclineDisable()
  {
    this.clamp_incline = false;
    this.clamp_incline_max = Float.MAX_VALUE;
    this.clamp_incline_min = -Float.MAX_VALUE;
  }

  @Override
  public void cameraClampInclineEnable(
    final float min,
    final float max)
  {
    RangeCheck.checkGreaterDouble(max, "Maximum clamp", min, "Minimum clamp");
    this.clamp_incline = true;
    this.clamp_incline_max = max;
    this.clamp_incline_min = min;
  }

  @Override
  public void cameraClampRadiusDisable()
  {
    this.clamp_incline = false;
    this.clamp_incline_max = Float.MAX_VALUE;
    this.clamp_incline_min = 0.0f;
  }

  @Override
  public void cameraClampRadiusEnable(
    final float min,
    final float max)
  {
    RangeCheck.checkGreaterDouble(max, "Maximum clamp", min, "Minimum clamp");
    RangeCheck.checkGreaterDouble(
      min,
      "Minimum clamp",
      0.0,
      "Minimum clamp range");

    this.clamp_radius = true;
    this.clamp_radius_max = max;
    this.clamp_radius_min = min;
  }

  @Override
  public float cameraGetAngleHeading()
  {
    return this.input_heading;
  }

  @Override
  public float cameraGetAngleIncline()
  {
    return this.input_incline;
  }

  @Override
  public VectorReadable3FType cameraGetForward()
  {
    this.deriveVectors();
    return this.derived_forward;
  }

  @Override
  public VectorReadable3FType cameraGetForwardProjectedOnXZ()
  {
    this.deriveVectors();
    return this.derived_forward_on_xz;
  }

  @Override
  public VectorReadable3FType cameraGetPosition()
  {
    this.deriveVectors();
    return this.derived_position;
  }

  @Override
  public VectorReadable3FType cameraGetRight()
  {
    this.deriveVectors();
    return this.derived_right;
  }

  @Override
  public VectorReadable3FType cameraGetTargetPosition()
  {
    return this.input_target_position;
  }

  @Override
  public VectorReadable3FType cameraGetUp()
  {
    this.deriveVectors();
    return this.derived_up;
  }

  @Override
  public float cameraGetZoom()
  {
    return this.input_radius;
  }

  @Override
  public JCameraSphericalSnapshot cameraMakeSnapshot()
  {
    this.deriveVectors();
    return new JCameraSphericalSnapshot(
      new VectorI3F(this.derived_forward),
      new VectorI3F(this.derived_right),
      new VectorI3F(this.derived_up),
      this.input_incline,
      this.input_heading,
      new VectorI3F(this.derived_position),
      this.input_radius,
      new VectorI3F(this.input_target_position),
      new VectorI3F(this.derived_forward_on_xz));
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
  public void cameraMoveTargetForwardOnXZ(
    final float u)
  {
    this.deriveVectors();
    VectorM3F.addScaledInPlace(
      this.input_target_position,
      this.derived_forward_on_xz,
      u);
    this.derived_current = false;
  }

  @Override
  public void cameraMoveTargetRight(
    final float u)
  {
    this.deriveVectors();
    VectorM3F.addScaledInPlace(
      this.input_target_position,
      this.derived_right,
      u);
    this.derived_current = false;
  }

  @Override
  public void cameraMoveTargetUp(
    final float u)
  {
    VectorM3F.addScaledInPlace(
      this.input_target_position,
      JCameraSpherical.AXIS_Y,
      u);
    this.derived_current = false;
  }

  @Override
  public void cameraOrbitHeading(
    final float r)
  {
    this.derived_current = false;
    this.input_heading += r;
  }

  @Override
  public boolean cameraOrbitIncline(
    final float r)
  {
    this.derived_current = false;
    this.input_incline += r;
    return this.clampIncline();
  }

  @Override
  public void cameraSetAngleHeading(
    final float v)
  {
    this.input_heading = v;
    this.derived_current = false;
  }

  @Override
  public void cameraSetAngleIncline(
    final float h)
  {
    this.input_incline = h;
    this.derived_current = false;
    this.clampIncline();
  }

  @Override
  public void cameraSetTargetPosition(
    final VectorReadable3FType v)
  {
    VectorM3F.copy(
      NullCheck.notNull(v, "Position"),
      this.input_target_position);
    this.derived_current = false;
  }

  @Override
  public void cameraSetTargetPosition3f(
    final float x,
    final float y,
    final float z)
  {
    this.derived_current = false;
    this.input_target_position.set3F(x, y, z);
  }

  @Override
  public void cameraSetZoom(
    final float r)
  {
    this.derived_current = false;
    this.input_radius = r;
    this.clampRadius();
  }

  @Override
  public boolean cameraZoomIn(
    final float r)
  {
    this.derived_current = false;
    this.input_radius -= r;
    return this.clampRadius();
  }

  @Override
  public boolean cameraZoomOut(
    final float r)
  {
    return this.cameraZoomIn(-r);
  }

  private boolean clampIncline()
  {
    if (this.clamp_incline) {
      this.clamp.clamp(
        this.input_incline,
        this.clamp_incline_min,
        this.clamp_incline_max);
      this.input_incline = this.clamp.getValue();
      return this.clamp.isClamped();
    }
    return false;
  }

  private boolean clampRadius()
  {
    if (this.clamp_radius) {
      this.clamp.clamp(
        this.input_radius,
        this.clamp_radius_min,
        this.clamp_radius_max);
      this.input_radius = this.clamp.getValue();
      return this.clamp.isClamped();
    }

    return false;
  }

  private void deriveVectors()
  {
    if (this.derived_current == false) {
      final float i = this.input_incline;
      final float a = this.input_heading;

      /**
       * Derive forward vector and resulting camera position.
       */

      {
        final float x = (float) (Math.cos(a) * Math.cos(i));
        final float y = (float) Math.sin(i);
        final float z = (float) -(Math.cos(i) * Math.sin(a));

        this.temporary.set3F(x, y, z);
        VectorM3F.scaleInPlace(this.temporary, this.input_radius);
        VectorM3F.add(
          this.input_target_position,
          this.temporary,
          this.derived_position);

        this.derived_forward.set3F(x, y, z);
        VectorM3F.scaleInPlace(this.derived_forward, -1.0);
        VectorM3F.normalizeInPlace(this.derived_forward);
      }

      /**
       * Project forward vector onto X/Z plane.
       */

      {
        VectorM3F.copy(this.derived_forward, this.derived_forward_on_xz);
        this.derived_forward_on_xz.setYF(0.0f);
        VectorM3F.normalizeInPlace(this.derived_forward_on_xz);
      }

      /**
       * Derive up vector.
       */

      {
        final float im = (float) (i - Math.toRadians(90.0f));

        final float x = (float) (Math.cos(a) * Math.cos(im));
        final float y = (float) Math.sin(im);
        final float z = (float) -(Math.cos(im) * Math.sin(a));

        this.derived_up.set3F(x, y, z);
        VectorM3F.scaleInPlace(this.derived_up, -1.0);
        VectorM3F.normalizeInPlace(this.derived_up);
      }

      /**
       * Derive right vector (forward * up).
       */

      VectorM3F.crossProduct(
        this.derived_forward,
        this.derived_up,
        this.derived_right);
    }

    this.derived_current = true;
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
    final JCameraSpherical other = (JCameraSpherical) obj;
    return (this.clamp_incline == other.clamp_incline)
      && (Float.floatToIntBits(this.clamp_incline_max) == Float
      .floatToIntBits(other.clamp_incline_max))
      && (Float.floatToIntBits(this.clamp_incline_min) == Float
      .floatToIntBits(other.clamp_incline_min))
      && (this.clamp_radius == other.clamp_radius)
      && (Float.floatToIntBits(this.clamp_radius_max) == Float
      .floatToIntBits(other.clamp_radius_max))
      && (Float.floatToIntBits(this.clamp_radius_min) == Float
      .floatToIntBits(other.clamp_radius_min))
      && this.input_target_position.equals(other.input_target_position)
      && (Float.floatToIntBits(this.input_heading) == Float
      .floatToIntBits(other.input_heading))
      && (Float.floatToIntBits(this.input_incline) == Float
      .floatToIntBits(other.input_incline))
      && (Float.floatToIntBits(this.input_radius) == Float
      .floatToIntBits(other.input_radius));
  }

  @Override
  public int hashCode()
  {
    final int prime = 31;
    int result = 1;
    result = (prime * result) + (this.clamp_incline ? 1231 : 1237);
    result = (prime * result) + Float.floatToIntBits(this.clamp_incline_max);
    result = (prime * result) + Float.floatToIntBits(this.clamp_incline_min);
    result = (prime * result) + (this.clamp_radius ? 1231 : 1237);
    result = (prime * result) + Float.floatToIntBits(this.clamp_radius_max);
    result = (prime * result) + Float.floatToIntBits(this.clamp_radius_min);
    result = (prime * result) + this.input_target_position.hashCode();
    result = (prime * result) + Float.floatToIntBits(this.input_heading);
    result = (prime * result) + Float.floatToIntBits(this.input_incline);
    result = (prime * result) + Float.floatToIntBits(this.input_radius);
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder b = new StringBuilder();
    b.append("[JCameraSpherical clamp_incline=");
    b.append(this.clamp_incline);
    b.append(" clamp_incline_max=");
    b.append(this.clamp_incline_max);
    b.append(" clamp_incline_min=");
    b.append(this.clamp_incline_min);
    b.append(" clamp_radius=");
    b.append(this.clamp_radius);
    b.append(" clamp_radius_max=");
    b.append(this.clamp_radius_max);
    b.append(" clamp_radius_min=");
    b.append(this.clamp_radius_min);
    b.append(" input_focus_position=");
    b.append(this.input_target_position);
    b.append(" input_heading=");
    b.append(this.input_heading);
    b.append(" input_incline=");
    b.append(this.input_incline);
    b.append(" input_radius=");
    b.append(this.input_radius);
    b.append("]");
    final String r = b.toString();
    assert r != null;
    return r;
  }
}
