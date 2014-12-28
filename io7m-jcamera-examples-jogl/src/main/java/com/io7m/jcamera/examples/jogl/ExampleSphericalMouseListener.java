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

import java.util.concurrent.atomic.AtomicReference;

import com.io7m.jcamera.JCameraSphericalInput;
import com.io7m.jcamera.JCameraSphericalMouseRegion;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.VectorM2F;
import com.io7m.jtensors.VectorM2I;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;

/**
 * The mouse adapter used to handle mouse events.
 */

// CHECKSTYLE_JAVADOC:OFF

public final class ExampleSphericalMouseListener extends MouseAdapter
{
  private final AtomicReference<JCameraSphericalMouseRegion> mouse_region;
  private final VectorM2F                                    position_normalized;
  private final JCameraSphericalInput                        input;
  private final ExampleRendererType                          renderer;
  private final VectorM2I                                    saved;
  private final Window                                       window;

  public ExampleSphericalMouseListener(
    final Window in_window,
    final AtomicReference<JCameraSphericalMouseRegion> in_mouse_region,
    final JCameraSphericalInput in_input,
    final ExampleRendererType in_renderer)
  {
    this.window = in_window;
    this.mouse_region = in_mouse_region;
    this.input = in_input;
    this.renderer = in_renderer;
    this.saved = new VectorM2I();
    this.position_normalized = new VectorM2F();
  }

  @Override public void mouseMoved(
    final @Nullable MouseEvent e)
  {
    assert e != null;
    this.mouse_region.get().getPosition(
      e.getX(),
      e.getY(),
      this.position_normalized);

    if (this.position_normalized.getXF() <= -0.98f) {
      this.input.setTargetMovingLeftCursor(true);
    } else {
      this.input.setTargetMovingLeftCursor(false);
    }
    if (this.position_normalized.getXF() >= 0.98f) {
      this.input.setTargetMovingRightCursor(true);
    } else {
      this.input.setTargetMovingRightCursor(false);
    }

    if (this.position_normalized.getYF() <= -0.98f) {
      this.input.setTargetMovingBackwardCursor(true);
    } else {
      this.input.setTargetMovingBackwardCursor(false);
    }
    if (this.position_normalized.getYF() >= 0.98f) {
      this.input.setTargetMovingForwardCursor(true);
    } else {
      this.input.setTargetMovingForwardCursor(false);
    }
  }

  @Override public void mousePressed(
    final @Nullable MouseEvent e)
  {
    assert e != null;
    if (e.isButtonDown(2)) {
      this.saved.set2I(e.getX(), e.getY());
      this.input.setTargetMovingContinuousForward(0.0f);
      this.input.setTargetMovingContinuousRight(0.0f);
      this.renderer.sendWantWarpPointer();
    }
  }

  @Override public void mouseReleased(
    final @Nullable MouseEvent e)
  {
    assert e != null;
    if (e.isButtonDown(2)) {
      this.window.warpPointer(this.saved.getXI(), this.saved.getYI());
    }
  }

  @Override public void mouseDragged(
    final @Nullable MouseEvent e)
  {
    assert e != null;
    if (e.isButtonDown(2)) {
      this.mouse_region.get().getPosition(
        e.getX(),
        e.getY(),
        this.position_normalized);
      final float px = -this.position_normalized.getXF();
      final float py = -this.position_normalized.getYF();
      this.input.addTargetMovingContinuousForward(py);
      this.input.addTargetMovingContinuousRight(px);
      this.renderer.sendWantWarpPointer();
    }
  }
}
