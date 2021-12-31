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

import com.io7m.jcamera.JCameraScreenOrigin;
import com.io7m.jcamera.JCameraSphericalInputType;
import com.io7m.jcamera.JCameraSphericalMouseRegion;
import com.io7m.jcamera.JCameraSphericalSnapshot;
import com.io7m.junreachable.UnreachableCodeException;
import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Trivial camera example.
 */

public final class ExampleSphericalMain
{
  private ExampleSphericalMain()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Main function.
   *
   * @param args Command line arguments.
   */

  public static void main(
    final String[] args)
  {
    ExampleTimer.enableHighResolutionTimer();
    final ExecutorService background_workers =
      Objects.requireNonNull(Executors.newFixedThreadPool(1));

    /*
     * $example: Construct a new renderer.
     */

    final ExampleRendererType renderer = new ExampleRenderer();

    /*
     * $example: Construct a new simulation, get access to the camera's input,
     * and produce an initial snapshot of the camera for later use.
     */

    final ExampleSphericalSimulationType sim =
      new ExampleSphericalSimulation();
    final JCameraSphericalInputType input = sim.getInput();
    final JCameraSphericalSnapshot snap = sim.integrate();

    /*
     * $example: Declare a mouse region to map screen-space positions to
     * normalized positions.
     */

    final AtomicReference<JCameraSphericalMouseRegion> mouse_region =
      new AtomicReference<>(
        JCameraSphericalMouseRegion.of(
          JCameraScreenOrigin.SCREEN_ORIGIN_TOP_LEFT,
          640.0,
          480.0));

    /*
     * $example: Initialize JOGL and open a window.
     */

    final GLProfile profile = GLProfile.get(GLProfile.GL3);
    final GLCapabilities caps = new GLCapabilities(profile);
    final GLWindow window = GLWindow.create(caps);
    window.setSize(640, 480);
    window.setTitle(ExampleSphericalMain.class.getCanonicalName());

    /*
     * Construct an animator to regularly refresh the screen.
     */

    final Animator anim = new Animator();
    anim.add(window);

    /*
     * $example: Initialize JOGL and open a window, construct an animator to
     * regularly refresh the screen, and assign GL event listener, mouse
     * listener, and keyboard listener.
     */

    window.addGLEventListener(new ExampleSphericalGLListener(
      renderer,
      snap,
      sim,
      mouse_region,
      window));

    window.addKeyListener(new ExampleSphericalKeyListener(
      sim,
      window,
      renderer,
      background_workers));

    window.addMouseListener(new ExampleSphericalMouseListener(
      window,
      mouse_region,
      input,
      renderer));

    /*
     * $example: Close the program when the window closes.
     */

    window.addWindowListener(new WindowAdapter()
    {
      @Override
      public void windowDestroyed(
        final WindowEvent e)
      {
        System.out.println("Stopping animator");
        anim.stop();
        System.out.println("Exiting");
        System.exit(0);
      }
    });

    window.setDefaultCloseOperation(
      WindowClosingProtocol.WindowClosingMode.DISPOSE_ON_CLOSE);
    window.setVisible(true);

    /*
     * Start everything running.
     */

    anim.start();
  }
}
