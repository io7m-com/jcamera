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

package com.io7m.jcamera.examples.jogl;

import com.io7m.jcamera.JCameraSphericalInputType;
import com.io7m.jcamera.JCameraSphericalSnapshot;
import com.io7m.jcamera.JCameraSphericalType;

/**
 * The interface to simulations (with spherical cameras) exposed to JOGL.
 */

public interface ExampleSphericalSimulationType
{
  /**
   * @return The camera input
   */

  JCameraSphericalInputType getInput();

  /**
   * @return The simulation delta time
   */

  double getDeltaTime();

  /**
   * @return A new camera snapshot
   */

  JCameraSphericalSnapshot integrate();

  /**
   * @return {@code true} if the camera is enabled.
   */

  boolean cameraIsEnabled();

  /**
   * Enable/disable the camera.
   *
   * @param b {@code true} if the camera should be enabled.
   */

  void cameraSetEnabled(
    boolean b);

  /**
   * @return The camera
   */

  JCameraSphericalType getCamera();
}
