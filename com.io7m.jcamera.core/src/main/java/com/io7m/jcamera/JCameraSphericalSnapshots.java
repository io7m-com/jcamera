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

import com.io7m.jinterp.InterpolationD;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import com.io7m.jtensors.core.unparameterized.vectors.Vectors3D;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * Functions to derive immutable snapshots of cameras.
 */

public final class JCameraSphericalSnapshots
{
  private JCameraSphericalSnapshots()
  {
    throw new UnreachableCodeException();
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
    final double a)
  {
    final Vector3D in_forward =
      Vectors3D.interpolateLinear(
        x.cameraGetForward(),
        y.cameraGetForward(),
        a);
    final Vector3D in_right =
      Vectors3D.interpolateLinear(
        x.cameraGetRight(),
        y.cameraGetRight(),
        a);
    final Vector3D in_up =
      Vectors3D.interpolateLinear(
        x.cameraGetUp(),
        y.cameraGetUp(),
        a);
    final double in_angle_incline =
      InterpolationD.interpolateLinear(
        x.cameraGetAngleIncline(),
        y.cameraGetAngleIncline(),
        a);
    final double in_angle_heading =
      InterpolationD.interpolateLinear(
        x.cameraGetAngleHeading(),
        y.cameraGetAngleHeading(),
        a);
    final Vector3D in_position =
      Vectors3D.interpolateLinear(
        x.cameraGetPosition(),
        y.cameraGetPosition(),
        a);
    final double in_radius =
      InterpolationD.interpolateLinear(
        x.cameraGetZoom(),
        y.cameraGetZoom(),
        a);
    final Vector3D in_target_position =
      Vectors3D.interpolateLinear(
        x.cameraGetTargetPosition(),
        y.cameraGetTargetPosition(),
        a);
    final Vector3D in_forward_on_xz =
      Vectors3D.interpolateLinear(
        x.cameraGetForwardProjectedOnXZ(),
        y.cameraGetForwardProjectedOnXZ(),
        a);

    return JCameraSphericalSnapshot.builder()
      .setCameraGetRight(in_right)
      .setCameraGetUp(in_up)
      .setCameraGetForward(in_forward)
      .setCameraGetAngleHeading(in_angle_heading)
      .setCameraGetAngleIncline(in_angle_incline)
      .setCameraGetForwardProjectedOnXZ(in_forward_on_xz)
      .setCameraGetTargetPosition(in_target_position)
      .setCameraGetPosition(in_position)
      .setCameraGetZoom(in_radius)
      .build();
  }

  /**
   * @param camera A camera
   *
   * @return A snapshot of the given camera
   */

  public static JCameraSphericalSnapshot of(
    final JCameraSphericalReadableType camera)
  {
    return JCameraSphericalSnapshot.builder()
      .setCameraGetRight(camera.cameraGetRight())
      .setCameraGetUp(camera.cameraGetUp())
      .setCameraGetForward(camera.cameraGetForward())
      .setCameraGetPosition(camera.cameraGetPosition())
      .setCameraGetTargetPosition(camera.cameraGetTargetPosition())
      .setCameraGetZoom(camera.cameraGetZoom())
      .setCameraGetAngleIncline(camera.cameraGetAngleIncline())
      .setCameraGetAngleHeading(camera.cameraGetAngleHeading())
      .setCameraGetForwardProjectedOnXZ(camera.cameraGetForwardProjectedOnXZ())
      .build();
  }
}
