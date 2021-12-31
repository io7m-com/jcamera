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
import com.io7m.jcamera.JCameraSphericalMouseRegion;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector2I;
import com.jogamp.newt.Window;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * The mouse adapter used to handle mouse events.
 */

// CHECKSTYLE_JAVADOC:OFF

public final class ExampleSphericalMouseListener extends MouseAdapter
{
  private final AtomicReference<JCameraSphericalMouseRegion> mouse_region;
  private final JCameraSphericalInputType input;
  private final ExampleRendererType renderer;
  private final Window window;
  private Vector2D position_normalized;
  private Vector2I saved;

  public ExampleSphericalMouseListener(
    final Window in_window,
    final AtomicReference<JCameraSphericalMouseRegion> in_mouse_region,
    final JCameraSphericalInputType in_input,
    final ExampleRendererType in_renderer)
  {
    this.window = in_window;
    this.mouse_region = in_mouse_region;
    this.input = in_input;
    this.renderer = in_renderer;
    this.saved = Vector2I.of(0, 0);
    this.position_normalized = Vector2D.of(0.0, 0.0);
  }

  @Override
  public void mouseMoved(
    final MouseEvent e)
  {
    assert e != null;

    this.position_normalized =
      this.mouse_region.get().position(e.getX(), e.getY());

    this.input.setTargetMovingLeftCursor(this.position_normalized.x() <= -0.98);
    this.input.setTargetMovingRightCursor(this.position_normalized.x() >= 0.98);

    this.input.setTargetMovingBackwardCursor(this.position_normalized.y() <= -0.98);
    this.input.setTargetMovingForwardCursor(this.position_normalized.y() >= 0.98);
  }

  @Override
  public void mousePressed(
    final MouseEvent e)
  {
    assert e != null;
    if (e.isButtonDown(2)) {
      this.saved = Vector2I.of(e.getX(), e.getY());
      this.input.setTargetMovingContinuousForward(0.0);
      this.input.setTargetMovingContinuousRight(0.0);
      this.renderer.sendWantWarpPointer();
    }
  }

  @Override
  public void mouseReleased(
    final MouseEvent e)
  {
    assert e != null;
    if (e.isButtonDown(2)) {
      this.window.warpPointer(this.saved.x(), this.saved.y());
    }
  }

  @Override
  public void mouseDragged(
    final MouseEvent e)
  {
    assert e != null;
    if (e.isButtonDown(2)) {
      this.position_normalized =
        this.mouse_region.get().position(e.getX(), e.getY());

      final double px = -this.position_normalized.x();
      final double py = -this.position_normalized.y();
      this.input.addTargetMovingContinuousForward(py);
      this.input.addTargetMovingContinuousRight(px);
      this.renderer.sendWantWarpPointer();
    }
  }
}
