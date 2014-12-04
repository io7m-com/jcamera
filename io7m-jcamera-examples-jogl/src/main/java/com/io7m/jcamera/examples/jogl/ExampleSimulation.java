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

import java.util.concurrent.atomic.AtomicBoolean;

import com.io7m.jcamera.JCameraFPSStyle;
import com.io7m.jcamera.JCameraFPSStyleIntegrator;
import com.io7m.jcamera.JCameraFPSStyleIntegratorType;
import com.io7m.jcamera.JCameraFPSStyleSnapshot;
import com.io7m.jcamera.JCameraFPSStyleType;
import com.io7m.jcamera.JCameraInput;

/**
 * The example physical simulation containing just the camera, updated at a
 * fixed time step.
 */

public final class ExampleSimulation implements ExampleSimulationType
{
  private final JCameraFPSStyleType           camera;
  private final AtomicBoolean                 camera_enabled;
  private final JCameraFPSStyleSnapshot       fixed_snapshot;
  private final JCameraInput                  input;
  private final JCameraFPSStyleIntegratorType integrator;
  private final float                         integrator_time_seconds;
  private final ExampleRendererControllerType renderer;

  /**
   * $example: Construct a new simulation.
   *
   * @param in_renderer
   *          The interface to the renderer
   */

  public ExampleSimulation(
    final ExampleRendererControllerType in_renderer)
  {
    this.renderer = in_renderer;
    this.input = JCameraInput.newInput();
    this.camera = JCameraFPSStyle.newCamera();
    final JCameraFPSStyleType camera_fixed = JCameraFPSStyle.newCamera();
    this.fixed_snapshot = camera_fixed.cameraMakeSnapshot();
    this.camera_enabled = new AtomicBoolean(false);

    /**
     * $example: Construct an integrator using the default implementations.
     */

    this.integrator =
      JCameraFPSStyleIntegrator.newIntegrator(this.camera, this.input);

    /**
     * Work out what fraction of a second the given simulation rate is going
     * to require.
     */

    final float rate = 60.0f;
    this.integrator_time_seconds = 1.0f / rate;

    /**
     * $example: Configure the integrator. Use a high drag factor to give
     * quite abrupt stops, and use high rotational acceleration.
     */

    this.integrator.integratorAngularSetDragHorizontal(0.000000001f);
    this.integrator.integratorAngularSetDragVertical(0.000000001f);
    this.integrator
      .integratorAngularSetAccelerationHorizontal((float) ((Math.PI / 12) / this.integrator_time_seconds));
    this.integrator
      .integratorAngularSetAccelerationVertical((float) ((Math.PI / 12) / this.integrator_time_seconds));

    this.integrator
      .integratorLinearSetAcceleration((float) (3.0 / this.integrator_time_seconds));
    this.integrator.integratorLinearSetMaximumSpeed(3.0f);
    this.integrator.integratorLinearSetDrag(0.000000001f);
  }

  /**
   * $example: Integrate the camera.
   *
   * @return A new camera snapshot.
   */

  @Override public JCameraFPSStyleSnapshot integrate()
  {
    /**
     * If the camera is actually enabled, integrate and produce a snapshot,
     * and then tell the renderer/window system that it should warp the
     * pointer back to the center of the screen.
     */

    if (this.cameraIsEnabled()) {
      this.integrator.integrate(this.integrator_time_seconds);
      final JCameraFPSStyleSnapshot snap = this.camera.cameraMakeSnapshot();
      this.renderer.sendWantWarpPointer();
      return snap;
    }

    return this.fixed_snapshot;
  }

  @Override public boolean cameraIsEnabled()
  {
    return this.camera_enabled.get();
  }

  @Override public void cameraSetEnabled(
    final boolean b)
  {
    this.camera_enabled.set(b);
  }

  @Override public float getDeltaTime()
  {
    return this.integrator_time_seconds;
  }

  @Override public JCameraInput getInput()
  {
    return this.input;
  }
}
