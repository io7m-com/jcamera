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
import com.io7m.jcamera.JCameraRotationCoefficients;
import com.io7m.jcamera.JCameraScreenOrigin;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import com.io7m.junreachable.UnreachableCodeException;
import com.jogamp.nativewindow.WindowClosingProtocol;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Trivial camera example.
 */

public final class ExampleFPSStyleMain
{
  private ExampleFPSStyleMain()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Main function.
   *
   * @param args
   *          Command line arguments.
   */

  public static void main(
    final String[] args)
  {
    ExampleTimer.enableHighResolutionTimer();
    final ExecutorService background_workers =
      NullCheck.notNull(Executors.newFixedThreadPool(1));

    /**
     * $example: Construct a new renderer.
     */

    final ExampleRendererType renderer = new ExampleRenderer();

    /**
     * $example: Construct a new simulation and produce an initial snapshot of
     * the camera for later use.
     */

    final ExampleFPSStyleSimulationType sim =
      new ExampleFPSStyleSimulation(renderer);
    final JCameraFPSStyleSnapshot snap = sim.integrate();

    /**
     * $example: Declare a structure to hold mouse rotation coefficients, and
     * a mouse region configured with an origin that matches that of JOGL's
     * windowing system.
     */

    final JCameraRotationCoefficients rotations =
      new JCameraRotationCoefficients();
    final AtomicReference<JCameraFPSStyleMouseRegion> mouse_region =
      new AtomicReference<>(
        JCameraFPSStyleMouseRegion.newRegion(
          JCameraScreenOrigin.SCREEN_ORIGIN_TOP_LEFT,
          640.0F,
          480.0F));

    /**
     * $example: Initialize JOGL and open a window, construct an animator to
     * regularly refresh the screen, and assign GL event listener, mouse
     * listener, and keyboard listener.
     */

    final GLProfile profile = GLProfile.get(GLProfile.GL3);
    final GLCapabilities caps = new GLCapabilities(profile);
    final GLWindow window = GLWindow.create(caps);
    window.setSize(640, 480);
    window.setTitle(ExampleFPSStyleMain.class.getCanonicalName());

    final Animator anim = new Animator();
    anim.add(window);

    window.addGLEventListener(new ExampleFPSStyleGLListener(
      window,
      snap,
      sim,
      mouse_region,
      renderer));

    window.addMouseListener(new ExampleFPSStyleMouseAdapter(
      mouse_region,
      sim,
      rotations));

    window.addKeyListener(new ExampleFPSStyleKeyListener(
      sim,
      background_workers,
      renderer,
      window));

    /**
     * Close the program when the window closes.
     */

    window.addWindowListener(new WindowAdapter() {
      @Override public void windowDestroyed(
        final @Nullable WindowEvent e)
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

    /**
     * Start everything running.
     */

    anim.start();
  }
}
