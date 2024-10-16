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

import com.io7m.jcamera.JCameraFPSStyleInputType;
import com.io7m.jcamera.JCameraFPSStyleMouseRegion;
import com.io7m.jcamera.JCameraRotationCoefficientsMutable;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;

import java.util.concurrent.atomic.AtomicReference;

/**
 * The mouse adapter used to handle mouse events.
 */

// CHECKSTYLE_JAVADOC:OFF

public final class ExampleFPSStyleMouseAdapter extends MouseAdapter
{
  private final AtomicReference<JCameraFPSStyleMouseRegion> mouse_region;
  private final JCameraFPSStyleInputType input;
  private final ExampleFPSStyleSimulationType sim;
  private final JCameraRotationCoefficientsMutable rotations;

  public ExampleFPSStyleMouseAdapter(
    final AtomicReference<JCameraFPSStyleMouseRegion> in_mouse_region,
    final ExampleFPSStyleSimulationType in_sim,
    final JCameraRotationCoefficientsMutable in_rotations)
  {
    this.mouse_region = in_mouse_region;
    this.input = in_sim.getInput();
    this.sim = in_sim;
    this.rotations = in_rotations;
  }

  @Override
  public void mouseMoved(
    final MouseEvent e)
  {
    assert e != null;

    /*
     * If the camera is enabled, get the rotation coefficients for the mouse
     * movement.
     */

    if (this.sim.cameraIsEnabled()) {
      this.rotations.from(
        this.mouse_region.get().coefficients(
          e.getX(),
          e.getY()));
      this.input.addRotationAroundHorizontal(this.rotations.horizontal());
      this.input.addRotationAroundVertical(this.rotations.vertical());
    }
  }
}
