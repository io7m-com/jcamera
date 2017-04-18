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

public final class JCameraFPSStyleSnapshots
{
  private JCameraFPSStyleSnapshots()
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

  public static JCameraFPSStyleSnapshot interpolate(
    final JCameraFPSStyleSnapshot x,
    final JCameraFPSStyleSnapshot y,
    final double a)
  {
    final Vector3D r_f =
      Vectors3D.interpolateLinear(
        x.cameraGetForward(),
        y.cameraGetForward(),
        a);

    final Vector3D r_u =
      Vectors3D.interpolateLinear(x.cameraGetUp(), y.cameraGetUp(), a);
    final Vector3D r_r =
      Vectors3D.interpolateLinear(x.cameraGetRight(), y.cameraGetRight(), a);
    final Vector3D r_p =
      Vectors3D.interpolateLinear(
        x.cameraGetPosition(),
        y.cameraGetPosition(),
        a);

    final double r_h =
      InterpolationD.interpolateLinear(
        x.cameraGetAngleAroundHorizontal(),
        y.cameraGetAngleAroundHorizontal(),
        a);
    final double r_v =
      InterpolationD.interpolateLinear(
        x.cameraGetAngleAroundVertical(),
        y.cameraGetAngleAroundVertical(),
        a);

    return JCameraFPSStyleSnapshot.builder()
      .setCameraGetRight(r_r)
      .setCameraGetUp(r_u)
      .setCameraGetForward(r_f)
      .setCameraGetAngleAroundHorizontal(r_h)
      .setCameraGetAngleAroundVertical(r_v)
      .setCameraGetPosition(r_p)
      .build();
  }

  /**
   * @param c A camera
   *
   * @return A snapshot of the given camera
   */

  public static JCameraFPSStyleSnapshot of(
    final JCameraFPSStyleReadableType c)
  {
    return JCameraFPSStyleSnapshot.builder()
      .setCameraGetRight(c.cameraGetRight())
      .setCameraGetUp(c.cameraGetUp())
      .setCameraGetForward(c.cameraGetForward())
      .setCameraGetPosition(c.cameraGetPosition())
      .setCameraGetAngleAroundHorizontal(c.cameraGetAngleAroundHorizontal())
      .setCameraGetAngleAroundVertical(c.cameraGetAngleAroundVertical())
      .build();
  }
}
