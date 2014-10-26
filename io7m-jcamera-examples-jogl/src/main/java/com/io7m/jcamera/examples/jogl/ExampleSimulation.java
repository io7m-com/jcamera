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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.io7m.jcamera.JCameraFPSStyle;
import com.io7m.jcamera.JCameraFPSStyleIntegrator;
import com.io7m.jcamera.JCameraFPSStyleIntegratorType;
import com.io7m.jcamera.JCameraFPSStyleType;
import com.io7m.jcamera.JCameraInput;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.MatrixM4x4F.Context;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorReadable3FType;

/**
 * The example physical simulation containing just the camera, updated at a
 * fixed time step.
 */

public final class ExampleSimulation implements
  Runnable,
  ExampleSimulationType
{
  private final JCameraFPSStyleType           camera;
  private final AtomicBoolean                 camera_enabled;
  private final AtomicBoolean                 camera_running;
  private final JCameraInput                  input;
  private final JCameraFPSStyleIntegratorType integrator;
  private final float                         integrator_time_nanos;
  private final float                         integrator_time_seconds;
  private final MatrixM4x4F                   matrix;
  private final Context                       matrix_context;
  private final ExampleRendererControllerType renderer;
  private @Nullable ScheduledFuture<?>        running_task;
  private final ScheduledExecutorService      scheduler;

  @Override public JCameraInput getInput()
  {
    return this.input;
  }

  /**
   * Construct a new simulation.
   *
   * @param in_renderer
   *          The interface to the renderer
   */

  public ExampleSimulation(
    final ExampleRendererControllerType in_renderer)
  {
    /**
     * Construct a scheduler to run the simulation at a fixed time step, some
     * preallocated storage that the package uses during the generation of
     * matrices, and save a reference to the renderer.
     */

    this.scheduler = NullCheck.notNull(Executors.newScheduledThreadPool(1));
    this.matrix_context = new MatrixM4x4F.Context();
    this.matrix = new MatrixM4x4F();
    this.renderer = in_renderer;

    /**
     * @example Allocate a new camera and input, and a couple of flags that
     *          indicate if the camera is actually in use, and that the
     *          simulation is running.
     */

    this.input = JCameraInput.newInput();
    this.camera = JCameraFPSStyle.newCamera();
    this.camera_enabled = new AtomicBoolean(false);
    this.camera_running = new AtomicBoolean(false);

    /**
     * @example Construct an integrator using the default implementations.
     */

    this.integrator =
      JCameraFPSStyleIntegrator.newIntegrator(this.camera, this.input);

    /**
     * @example Work out what fraction of a second the given simulation rate
     *          is going to require, and what the equivalent period is in
     *          nanoseconds to pass to the scheduler.
     */

    final float rate = 60.0f;
    this.integrator_time_seconds = 1.0f / rate;
    this.integrator_time_nanos = this.integrator_time_seconds * 1000000000.0f;
  }

  /**
   * @example Run the simulation for the delta time, and produce a view
   *          matrix.
   */

  @Override public void run()
  {
    /**
     * If the camera is actually enabled, integrate and produce a view matrix,
     * and then tell the renderer/window system that it should warp the
     * pointer back to the center of the screen.
     */

    if (this.cameraIsEnabled()) {
      this.integrator.integrate(this.integrator_time_seconds);
      this.camera.cameraMakeViewMatrix(this.matrix_context, this.matrix);
      this.renderer.setWantWarpPointer();
    } else {

      /**
       * Otherwise, produce a view matrix that simulates a simple fixed
       * camera.
       */

      MatrixM4x4F.setIdentity(this.matrix);
      final VectorReadable3FType origin = new VectorI3F(0.0f, 2.0f, 3.0f);
      final VectorReadable3FType target = new VectorI3F(0.0f, 0.0f, -3.0f);
      final VectorReadable3FType up = new VectorI3F(0.0f, 1.0f, 0.0f);
      MatrixM4x4F.lookAtWithContext(
        this.matrix_context,
        origin,
        target,
        up,
        this.matrix);
    }

    /**
     * Send whatever view matrix was created to the renderer.
     */

    final MatrixSnapshot snap = MatrixSnapshot.pack(this.matrix);
    this.renderer.setViewMatrix(snap);
  }

  /**
   * @example Start the simulation running.
   */

  @Override public void start()
  {
    if (this.camera_running.compareAndSet(false, true)) {
      System.out.println("Starting simulation");

      this.running_task =
        this.scheduler.scheduleAtFixedRate(
          this,
          0,
          (long) this.integrator_time_nanos,
          TimeUnit.NANOSECONDS);
      return;
    }

    throw new IllegalStateException("Simulation already running");
  }

  /**
   * @example Stop the simulation running.
   */

  @Override public void stop()
  {
    if (this.camera_running.compareAndSet(true, false)) {
      System.out.println("Stopping simulation");

      assert this.running_task != null;
      this.running_task.cancel(true);
      this.running_task = null;
      return;
    }

    throw new IllegalStateException("Simulation not running");
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
}
