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

import com.io7m.jcamera.JCameraScreenOrigin;
import com.io7m.jcamera.JCameraSphericalMouseRegion;
import com.io7m.jcamera.JCameraSphericalSnapshot;
import com.io7m.jcamera.JCameraSphericalSnapshots;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.DebugGL3;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The GL event listener used to handle rendering and driving of the
 * simulation.
 */

// CHECKSTYLE_JAVADOC:OFF

public final class ExampleSphericalGLListener implements GLEventListener
{
  private final ExampleRendererType renderer;
  private final ExampleSphericalSimulationType sim;
  private final AtomicReference<JCameraSphericalMouseRegion> mouse_region;
  private final GLWindow window;
  private long time_then;
  private double time_accum;
  private JCameraSphericalSnapshot snap_curr;
  private JCameraSphericalSnapshot snap_prev;

  public ExampleSphericalGLListener(
    final ExampleRendererType in_renderer,
    final JCameraSphericalSnapshot in_snap,
    final ExampleSphericalSimulationType in_sim,
    final AtomicReference<JCameraSphericalMouseRegion> in_mouse_region,
    final GLWindow in_window)
  {
    this.renderer = in_renderer;
    this.sim = in_sim;
    this.mouse_region = in_mouse_region;
    this.window = in_window;
    this.snap_curr = in_snap;
    this.snap_prev = in_snap;
  }

  @Override
  public void init(
    final GLAutoDrawable drawable)
  {
    try {
      assert drawable != null;

      final GL3 g = new DebugGL3(drawable.getGL().getGL3());
      assert g != null;

      this.time_then = System.nanoTime();
      this.renderer.init(this.window, g);
      this.renderer.reshape(this.window.getWidth(), this.window.getHeight());
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void dispose(
    final GLAutoDrawable drawable)
  {
    // Nothing.
  }

  @Override
  public void display(
    final GLAutoDrawable drawable)
  {
    assert drawable != null;

    /*
     * Integrate the camera as many times as necessary for each rendering
     * frame interval.
     */

    final long time_now = System.nanoTime();
    final long time_diff = time_now - this.time_then;
    final double time_diff_s = (double) time_diff / 1000000000.0;
    this.time_accum = this.time_accum + time_diff_s;
    this.time_then = time_now;

    final double sim_delta = this.sim.getDeltaTime();
    while (this.time_accum >= sim_delta) {
      this.snap_prev = this.snap_curr;
      this.snap_curr = this.sim.integrate();
      this.time_accum -= sim_delta;
    }

    /*
     * Determine how far the current time is between the current camera state
     * and the next, and use that value to interpolate between the two saved
     * states.
     */

    final double alpha = this.time_accum / sim_delta;
    final JCameraSphericalSnapshot snap_interpolated =
      JCameraSphericalSnapshots.interpolate(
        this.snap_prev,
        this.snap_curr,
        alpha);

    final GL3 g = new DebugGL3(drawable.getGL().getGL3());
    assert g != null;
    g.glClear(GL.GL_COLOR_BUFFER_BIT);

    /*
     * Draw the scene!
     */

    final Vector3D target = snap_interpolated.cameraGetTargetPosition();
    this.renderer.draw(snap_interpolated, Optional.of(target));
  }

  @Override
  public void reshape(
    final GLAutoDrawable drawable,
    final int x,
    final int y,
    final int width,
    final int height)
  {
    this.mouse_region.set(JCameraSphericalMouseRegion.of(
      JCameraScreenOrigin.SCREEN_ORIGIN_TOP_LEFT,
      width,
      height));
    this.renderer.reshape(width, height);
  }
}
