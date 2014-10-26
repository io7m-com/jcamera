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

package com.io7m.jcamera.examples.jogl;

import com.io7m.jcamera.JCameraInput;

/**
 * The interface to simulations exposed to JOGL.
 */

public interface ExampleSimulationType
{
  /**
   * @return The camera input
   */

  JCameraInput getInput();

  /**
   * Start the simulation running.
   */

  void start();

  /**
   * Stop the simulation running.
   */

  void stop();

  /**
   * @return <code>true</code> if the camera is enabled.
   */

  boolean cameraIsEnabled();

  /**
   * Enable/disable the camera.
   *
   * @param b
   *          <code>true</code> if the camera should be enabled.
   */

  void cameraSetEnabled(
    boolean b);
}
