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

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import javax.media.opengl.DebugGL3;
import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;

import com.io7m.jcamera.JCameraScreenOrigin;
import com.io7m.jcamera.JCameraSphericalMouseRegion;
import com.io7m.jcamera.JCameraSphericalSnapshot;
import com.io7m.jfunctional.Option;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorReadable3FType;
import com.jogamp.newt.opengl.GLWindow;

/**
 * The GL event listener used to handle rendering and driving of the
 * simulation.
 */

// CHECKSTYLE_JAVADOC:OFF

public final class ExampleSphericalGLListener implements GLEventListener
{
  private final ExampleRendererType                          renderer;
  private final ExampleSphericalSimulationType               sim;
  private final AtomicReference<JCameraSphericalMouseRegion> mouse_region;
  private final GLWindow                                     window;
  private long                                               time_then;
  private double                                             time_accum;
  private JCameraSphericalSnapshot                           snap_curr;
  private JCameraSphericalSnapshot                           snap_prev;

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

  @Override public void init(
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

  @Override public void dispose(
    final @Nullable GLAutoDrawable drawable)
  {
    // Nothing.
  }

  @Override public void display(
    final @Nullable GLAutoDrawable drawable)
  {
    assert drawable != null;

    /**
     * Integrate the camera as many times as necessary for each rendering
     * frame interval.
     */

    final long time_now = System.nanoTime();
    final long time_diff = time_now - this.time_then;
    final double time_diff_s = time_diff / 1000000000.0;
    this.time_accum = this.time_accum + time_diff_s;
    this.time_then = time_now;

    final float sim_delta = this.sim.getDeltaTime();
    while (this.time_accum >= sim_delta) {
      this.snap_prev = this.snap_curr;
      this.snap_curr = this.sim.integrate();
      this.time_accum -= sim_delta;
    }

    /**
     * Determine how far the current time is between the current camera state
     * and the next, and use that value to interpolate between the two saved
     * states.
     */

    final float alpha = (float) (this.time_accum / sim_delta);
    final JCameraSphericalSnapshot snap_interpolated =
      JCameraSphericalSnapshot.interpolate(
        this.snap_prev,
        this.snap_curr,
        alpha);

    final GL3 g = new DebugGL3(drawable.getGL().getGL3());
    assert g != null;
    g.glClear(GL.GL_COLOR_BUFFER_BIT);

    /**
     * Draw the scene!
     */

    final VectorReadable3FType target =
      snap_interpolated.cameraGetTargetPosition();
    this.renderer.draw(snap_interpolated, Option.some(target));
  }

  @Override public void reshape(
    final @Nullable GLAutoDrawable drawable,
    final int x,
    final int y,
    final int width,
    final int height)
  {
    this.mouse_region.set(JCameraSphericalMouseRegion.newRegion(
      JCameraScreenOrigin.SCREEN_ORIGIN_TOP_LEFT,
      width,
      height));
    this.renderer.reshape(width, height);
  }
}
