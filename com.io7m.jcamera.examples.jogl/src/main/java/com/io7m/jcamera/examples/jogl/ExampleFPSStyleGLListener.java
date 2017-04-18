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

package com.io7m.jcamera.examples.jogl;

import com.io7m.jcamera.JCameraFPSStyleMouseRegion;
import com.io7m.jcamera.JCameraFPSStyleSnapshot;
import com.io7m.jcamera.JCameraFPSStyleSnapshots;
import com.io7m.jcamera.JCameraScreenOrigin;
import com.io7m.jnull.Nullable;
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

public final class ExampleFPSStyleGLListener implements GLEventListener
{
  private final GLWindow window;
  private final ExampleFPSStyleSimulationType sim;
  private final AtomicReference<JCameraFPSStyleMouseRegion> mouse_region;
  private final ExampleRendererType renderer;
  private long time_then;
  private double time_accum;
  private JCameraFPSStyleSnapshot snap_curr;
  private JCameraFPSStyleSnapshot snap_prev;

  public ExampleFPSStyleGLListener(
    final GLWindow in_window,
    final JCameraFPSStyleSnapshot in_snap,
    final ExampleFPSStyleSimulationType in_sim,
    final AtomicReference<JCameraFPSStyleMouseRegion> in_mouse_region,
    final ExampleRendererType in_renderer)
  {
    this.window = in_window;
    this.sim = in_sim;
    this.mouse_region = in_mouse_region;
    this.renderer = in_renderer;
    this.snap_curr = in_snap;
    this.snap_prev = in_snap;
  }

  /**
   * Initialize the simulation.
   *
   * @param drawable The OpenGL drawable
   */

  @Override
  public void init(
    final @Nullable GLAutoDrawable drawable)
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
    final @Nullable GLAutoDrawable drawable)
  {
    // Nothing.
  }

  @Override
  public void display(
    final @Nullable GLAutoDrawable drawable)
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

    final float sim_delta = this.sim.getDeltaTime();
    while (this.time_accum >= (double) sim_delta) {
      this.snap_prev = this.snap_curr;
      this.snap_curr = this.sim.integrate();
      this.time_accum -= (double) sim_delta;
    }

    /*
     * Determine how far the current time is between the current camera state
     * and the next, and use that value to interpolate between the two saved
     * states.
     */

    final float alpha = (float) (this.time_accum / (double) sim_delta);
    final JCameraFPSStyleSnapshot snap_interpolated =
      JCameraFPSStyleSnapshots.interpolate(
        this.snap_prev,
        this.snap_curr,
        (double) alpha);

    final GL3 g = new DebugGL3(drawable.getGL().getGL3());
    assert g != null;
    g.glClear(GL.GL_COLOR_BUFFER_BIT);

    /*
     * Draw the scene!
     */

    this.renderer.draw(snap_interpolated, Optional.empty());
  }

  @Override
  public void reshape(
    final @Nullable GLAutoDrawable drawable,
    final int x,
    final int y,
    final int width,
    final int height)
  {
    this.mouse_region.set(JCameraFPSStyleMouseRegion.of(
      JCameraScreenOrigin.SCREEN_ORIGIN_TOP_LEFT,
      (double) width,
      (double) height));
    this.renderer.reshape(width, height);
  }
}
