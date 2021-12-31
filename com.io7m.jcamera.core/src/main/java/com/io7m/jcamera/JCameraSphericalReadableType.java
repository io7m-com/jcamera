/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;

/**
 * Readable interface to {@link JCameraSphericalType}.
 */

public interface JCameraSphericalReadableType extends JCameraReadableType
{
  /**
   * @return The angle around the vertical axis.
   */

  double cameraGetAngleHeading();

  /**
   * @return The angle around the horizontal axis.
   */

  double cameraGetAngleIncline();

  /**
   * @return The forward direction for the camera.
   */

  Vector3D cameraGetForward();

  /**
   * @return The forward direction for the camera, projected onto the X/Z plane.
   */

  Vector3D cameraGetForwardProjectedOnXZ();

  /**
   * @return The position of the camera.
   */

  Vector3D cameraGetPosition();

  /**
   * @return The right direction for the camera.
   */

  Vector3D cameraGetRight();

  /**
   * @return The position of the camera target.
   */

  Vector3D cameraGetTargetPosition();

  /**
   * @return The up direction for the camera.
   */

  Vector3D cameraGetUp();

  /**
   * @return The zoom (or <i>radius</i>) of the camera.
   */

  double cameraGetZoom();
}
