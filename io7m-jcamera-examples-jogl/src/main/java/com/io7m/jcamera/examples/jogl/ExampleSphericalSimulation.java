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

import com.io7m.jcamera.JCameraSpherical;
import com.io7m.jcamera.JCameraSphericalAngularIntegrator;
import com.io7m.jcamera.JCameraSphericalInput;
import com.io7m.jcamera.JCameraSphericalIntegrator;
import com.io7m.jcamera.JCameraSphericalIntegratorType;
import com.io7m.jcamera.JCameraSphericalLinearIntegratorZoomScaled;
import com.io7m.jcamera.JCameraSphericalSnapshot;
import com.io7m.jcamera.JCameraSphericalType;

/**
 * The example physical simulation containing just the fps-style camera,
 * updated at a fixed time step.
 */

public final class ExampleSphericalSimulation implements
  ExampleSphericalSimulationType
{
  private final JCameraSphericalType           camera;
  private final AtomicBoolean                  camera_enabled;
  private final JCameraSphericalSnapshot       fixed_snapshot;
  private final JCameraSphericalInput          input;
  private final JCameraSphericalIntegratorType integrator;
  private final float                          integrator_time_seconds;

  /**
   * $example: Construct a new simulation.
   */

  public ExampleSphericalSimulation()
  {
    this.input = JCameraSphericalInput.newInput();
    this.camera = JCameraSpherical.newCamera();
    final JCameraSphericalType camera_fixed = JCameraSpherical.newCamera();
    this.fixed_snapshot = camera_fixed.cameraMakeSnapshot();
    this.camera_enabled = new AtomicBoolean(false);

    /**
     * $example: Construct an integrator using the implementation that
     * provides linear velocity scaling based on the current zoom level.
     */

    this.integrator =
      JCameraSphericalIntegrator.newIntegratorWith(
        JCameraSphericalAngularIntegrator.newIntegrator(
          this.camera,
          this.input),
        JCameraSphericalLinearIntegratorZoomScaled.newIntegrator(
          this.camera,
          this.input));

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

    this.input.setContinuousForwardFactor(1.0f);
    this.input.setContinuousRightwardFactor(1.0f);

    this.integrator.integratorAngularOrbitHeadingSetDrag(0.000000001f);
    this.integrator.integratorAngularOrbitInclineSetDrag(0.000000001f);

    this.integrator
      .integratorAngularOrbitHeadingSetAcceleration(1.0f / this.integrator_time_seconds);
    this.integrator
      .integratorAngularOrbitInclineSetAcceleration(1.0f / this.integrator_time_seconds);

    this.integrator
      .integratorLinearTargetSetAcceleration((float) (3.0 / (double) this
        .integrator_time_seconds));
    this.integrator.integratorLinearTargetSetMaximumSpeed(3.0f);
    this.integrator.integratorLinearTargetSetDrag(0.000000001f);

    this.integrator
      .integratorLinearZoomSetAcceleration((float) (3.0 / (double) this
        .integrator_time_seconds));
    this.integrator.integratorLinearZoomSetMaximumSpeed(3.0f);
    this.integrator.integratorLinearZoomSetDrag(0.000000001f);
  }

  /**
   * $example: Integrate the camera.
   *
   * @return A new camera snapshot.
   */

  @Override public JCameraSphericalSnapshot integrate()
  {
    /**
     * If the camera is actually enabled, integrate and produce a snapshot.
     */

    if (this.cameraIsEnabled()) {
      this.integrator.integrate(this.integrator_time_seconds);
      final JCameraSphericalSnapshot snap = this.camera.cameraMakeSnapshot();
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

  @Override public JCameraSphericalType getCamera()
  {
    return this.camera;
  }

  @Override public float getDeltaTime()
  {
    return this.integrator_time_seconds;
  }

  @Override public JCameraSphericalInput getInput()
  {
    return this.input;
  }
}
