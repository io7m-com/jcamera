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
import com.io7m.jnull.NullCheck;
import com.io7m.jranges.RangeCheck;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix4x4D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors3D;

/**
 * The default implementation of {@link JCameraSphericalType}.
 */

@EqualityStructural
public final class JCameraSpherical implements
  JCameraSphericalType
{
  private static final Vector3D AXIS_Y;

  static {
    AXIS_Y = Vector3D.of(0.0, 1.0, 0.0);
  }

  private final JCameraSignallingClamp clamp;
  private Vector3D derived_forward;
  private Vector3D derived_forward_on_xz;
  private Vector3D derived_position;
  private Vector3D derived_right;
  private Vector3D derived_up;
  private Vector3D input_target_position;
  private boolean clamp_incline;
  private double clamp_incline_max;
  private double clamp_incline_min;
  private boolean clamp_radius;
  private double clamp_radius_max;
  private double clamp_radius_min;
  private boolean derived_current;
  private double input_heading;
  private double input_incline;
  private double input_radius;

  private JCameraSpherical()
  {
    this.input_target_position = Vectors3D.zero();
    this.input_incline = 0.0;
    this.input_heading = -(Math.PI / 2.0);
    this.input_radius = 8.0;

    this.derived_current = false;
    this.derived_up = Vectors3D.zero();
    this.derived_right = Vectors3D.zero();
    this.derived_forward = Vectors3D.zero();
    this.derived_forward_on_xz = Vectors3D.zero();
    this.derived_position = Vectors3D.zero();

    this.clamp_incline = true;
    this.clamp_incline_max = 1.4;
    this.clamp_incline_min = 0.0;

    this.clamp_radius = true;
    this.clamp_radius_min = 1.0;
    this.clamp_radius_max = Double.MAX_VALUE;

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
    this.clamp_incline_max = Double.MAX_VALUE;
    this.clamp_incline_min = -Double.MAX_VALUE;
  }

  @Override
  public void cameraClampInclineEnable(
    final double min,
    final double max)
  {
    RangeCheck.checkGreaterDouble(
      max, "Maximum clamp",
      min, "Minimum clamp");
    this.clamp_incline = true;
    this.clamp_incline_max = max;
    this.clamp_incline_min = min;
  }

  @Override
  public void cameraClampRadiusDisable()
  {
    this.clamp_incline = false;
    this.clamp_incline_max = Double.MAX_VALUE;
    this.clamp_incline_min = 0.0;
  }

  @Override
  public void cameraClampRadiusEnable(
    final double min,
    final double max)
  {
    RangeCheck.checkGreaterDouble(
      max, "Maximum clamp",
      min, "Minimum clamp");
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
  public double cameraGetAngleHeading()
  {
    return this.input_heading;
  }

  @Override
  public double cameraGetAngleIncline()
  {
    return this.input_incline;
  }

  @Override
  public Vector3D cameraGetForward()
  {
    this.deriveVectors();
    return this.derived_forward;
  }

  @Override
  public Vector3D cameraGetForwardProjectedOnXZ()
  {
    this.deriveVectors();
    return this.derived_forward_on_xz;
  }

  @Override
  public Vector3D cameraGetPosition()
  {
    this.deriveVectors();
    return this.derived_position;
  }

  @Override
  public Vector3D cameraGetRight()
  {
    this.deriveVectors();
    return this.derived_right;
  }

  @Override
  public Vector3D cameraGetTargetPosition()
  {
    return this.input_target_position;
  }

  @Override
  public Vector3D cameraGetUp()
  {
    this.deriveVectors();
    return this.derived_up;
  }

  @Override
  public double cameraGetZoom()
  {
    return this.input_radius;
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
  public void cameraMoveTargetForwardOnXZ(
    final double u)
  {
    this.deriveVectors();
    this.input_target_position =
      Vectors3D.addScaled(
        this.input_target_position, this.derived_forward_on_xz, u);
    this.derived_current = false;
  }

  @Override
  public void cameraMoveTargetRight(
    final double u)
  {
    this.deriveVectors();
    this.input_target_position =
      Vectors3D.addScaled(
        this.input_target_position, this.derived_right, u);
    this.derived_current = false;
  }

  @Override
  public void cameraMoveTargetUp(
    final double u)
  {
    this.deriveVectors();
    this.input_target_position =
      Vectors3D.addScaled(
        this.input_target_position, JCameraSpherical.AXIS_Y, u);
    this.derived_current = false;
  }

  @Override
  public void cameraOrbitHeading(
    final double r)
  {
    this.derived_current = false;
    this.input_heading += r;
  }

  @Override
  public boolean cameraOrbitIncline(
    final double r)
  {
    this.derived_current = false;
    this.input_incline += r;
    return this.clampIncline();
  }

  @Override
  public void cameraSetAngleHeading(
    final double v)
  {
    this.input_heading = v;
    this.derived_current = false;
  }

  @Override
  public void cameraSetAngleIncline(
    final double h)
  {
    this.input_incline = h;
    this.derived_current = false;
    this.clampIncline();
  }

  @Override
  public void cameraSetTargetPosition(
    final Vector3D v)
  {
    this.input_target_position = NullCheck.notNull(v, "Position");
    this.derived_current = false;
  }

  @Override
  public void cameraSetTargetPosition3(
    final double x,
    final double y,
    final double z)
  {
    this.input_target_position = Vector3D.of(x, y, z);
    this.derived_current = false;
  }

  @Override
  public void cameraSetZoom(
    final double r)
  {
    this.derived_current = false;
    this.input_radius = r;
    this.clampRadius();
  }

  @Override
  public boolean cameraZoomIn(
    final double r)
  {
    this.derived_current = false;
    this.input_radius -= r;
    return this.clampRadius();
  }

  @Override
  public boolean cameraZoomOut(
    final double r)
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
    if (!this.derived_current) {
      final double i = this.input_incline;
      final double a = this.input_heading;

      /*
       * Derive forward vector and resulting camera position.
       */

      {
        final double x = Math.cos(a) * Math.cos(i);
        final double y = Math.sin(i);
        final double z = -(Math.cos(i) * Math.sin(a));

        this.derived_position =
          Vectors3D.add(
            this.input_target_position,
            Vectors3D.scale(Vector3D.of(x, y, z), this.input_radius));

        this.derived_forward =
          Vectors3D.normalize(Vectors3D.scale(Vector3D.of(x, y, z), -1.0));
      }

      /*
       * Project forward vector onto X/Z plane.
       */

      {
        this.derived_forward_on_xz =
          Vectors3D.normalize(
            Vector3D.of(
              this.derived_forward.x(),
              0.0,
              this.derived_forward.z()));
      }

      /*
       * Derive up vector.
       */

      {
        final double im = i - Math.toRadians(90.0);
        final double x = Math.cos(a) * Math.cos(im);
        final double y = Math.sin(im);
        final double z = -(Math.cos(im) * Math.sin(a));
        this.derived_up =
          Vectors3D.normalize(Vectors3D.scale(Vector3D.of(x, y, z), -1.0));
      }

      /*
       * Derive right vector (forward * up).
       */

      this.derived_right =
        Vectors3D.crossProduct(this.derived_forward, this.derived_up);
    }

    this.derived_current = true;
  }

  @Override
  public boolean equals(
    final Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }

    final JCameraSpherical that = (JCameraSpherical) o;
    return this.clamp_incline == that.clamp_incline && Double.compare(
      that.clamp_incline_max,
      this.clamp_incline_max) == 0 && Double.compare(
      that.clamp_incline_min,
      this.clamp_incline_min) == 0 && this.clamp_radius == that.clamp_radius && Double.compare(
      that.clamp_radius_max,
      this.clamp_radius_max) == 0 && Double.compare(
      that.clamp_radius_min,
      this.clamp_radius_min) == 0 && Double.compare(
      that.input_heading,
      this.input_heading) == 0 && Double.compare(
      that.input_incline,
      this.input_incline) == 0 && Double.compare(
      that.input_radius,
      this.input_radius) == 0 && this.input_target_position.equals(that.input_target_position);
  }

  @Override
  public int hashCode()
  {
    int result;
    long temp;
    result = this.input_target_position.hashCode();
    result = 31 * result + (this.clamp_incline ? 1 : 0);
    temp = Double.doubleToLongBits(this.clamp_incline_max);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.clamp_incline_min);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    result = 31 * result + (this.clamp_radius ? 1 : 0);
    temp = Double.doubleToLongBits(this.clamp_radius_max);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.clamp_radius_min);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.input_heading);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.input_incline);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    temp = Double.doubleToLongBits(this.input_radius);
    result = 31 * result + (int) (temp ^ (temp >>> 32));
    return result;
  }

  @Override
  public String toString()
  {
    final StringBuilder b = new StringBuilder(128);
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
    return b.toString();
  }
}
