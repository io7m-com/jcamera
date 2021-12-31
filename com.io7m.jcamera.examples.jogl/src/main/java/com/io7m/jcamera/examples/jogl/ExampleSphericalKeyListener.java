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

import com.io7m.jcamera.JCameraSphericalInputType;
import com.jogamp.newt.event.InputEvent;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;

import java.util.concurrent.ExecutorService;

/**
 * The key listener used to handle keyboard events.
 */

// CHECKSTYLE_JAVADOC:OFF

@SuppressWarnings("synthetic-access")
public final class ExampleSphericalKeyListener implements
  KeyListener
{
  private final ExecutorService background_workers;
  private final JCameraSphericalInputType input;
  private final ExampleRendererType renderer;
  private final ExampleSphericalSimulationType sim;
  private final GLWindow window;

  public ExampleSphericalKeyListener(
    final ExampleSphericalSimulationType in_sim,
    final GLWindow in_window,
    final ExampleRendererType in_renderer,
    final ExecutorService in_background_workers)
  {
    this.input = in_sim.getInput();
    this.sim = in_sim;
    this.window = in_window;
    this.renderer = in_renderer;
    this.background_workers = in_background_workers;
  }

  @Override
  public void keyPressed(
    final KeyEvent e)
  {
    assert e != null;

    /*
     * Ignore events that are the result of keyboard auto-repeat. This means
     * there's one single event when a key is pressed, and another when it is
     * released (as opposed to an endless stream of both when the key is held
     * down).
     */

    if ((e.getModifiers() & InputEvent.AUTOREPEAT_MASK) == InputEvent.AUTOREPEAT_MASK) {
      return;
    }

    switch (e.getKeyCode()) {

      /*
       * Standard WASD camera controls.
       */

      case KeyEvent.VK_A: {
        this.input.setTargetMovingLeftKey(true);
        break;
      }
      case KeyEvent.VK_W: {
        this.input.setTargetMovingForwardKey(true);
        break;
      }
      case KeyEvent.VK_S: {
        this.input.setTargetMovingBackwardKey(true);
        break;
      }
      case KeyEvent.VK_D: {
        this.input.setTargetMovingRightKey(true);
        break;
      }

      case KeyEvent.VK_X: {
        this.input.setTargetMovingUp(true);
        break;
      }
      case KeyEvent.VK_Z: {
        this.input.setTargetMovingDown(true);
        break;
      }

      case KeyEvent.VK_F: {
        this.input.setOrbitInclinePositive(true);
        break;
      }
      case KeyEvent.VK_V: {
        this.input.setOrbitInclineNegative(true);
        break;
      }

      case KeyEvent.VK_Q: {
        this.input.setOrbitHeadingNegative(true);
        break;
      }
      case KeyEvent.VK_E: {
        this.input.setOrbitHeadingPositive(true);
        break;
      }

      case KeyEvent.VK_G: {
        this.input.setZoomingIn(true);
        break;
      }
      case KeyEvent.VK_B: {
        this.input.setZoomingOut(true);
        break;
      }
    }
  }

  @Override
  public void keyReleased(
    final KeyEvent e)
  {
    assert e != null;

    /*
     * Ignore events that are the result of keyboard auto-repeat. This means
     * there's one single event when a key is pressed, and another when it is
     * released (as opposed to an endless stream of both when the key is held
     * down).
     */

    if ((e.getModifiers() & InputEvent.AUTOREPEAT_MASK) == InputEvent.AUTOREPEAT_MASK) {
      return;
    }

    switch (e.getKeyCode()) {

      /*
       * Pressing 'M' enables/disables the camera.
       */

      case KeyEvent.VK_M: {
        this.toggleCameraEnabled();
        break;
      }

      /*
       * Pressing 'P' makes the mouse cursor visible/invisible.
       */

      case KeyEvent.VK_P: {
        System.out.printf(
          "Making pointer %s\n",
          this.window.isPointerVisible() ? "invisible" : "visible");
        this.window.setPointerVisible(!this.window.isPointerVisible());
        break;
      }

      /*
       * Pressing enter switches between windowed and fullscreen mode. JOGL
       * requires that this be executed on a background thread.
       */

      case KeyEvent.VK_ENTER: {
        this.background_workers.execute(new Runnable()
        {
          @Override
          public void run()
          {
            final boolean mode =
              !ExampleSphericalKeyListener.this.window.isFullscreen();
            ExampleSphericalKeyListener.this.window.setFullscreen(mode);
          }
        });
        break;
      }

      /*
       * Standard WASD camera controls, with E and Q moving up and down,
       * respectively.
       */

      case KeyEvent.VK_A: {
        this.input.setTargetMovingLeftKey(false);
        break;
      }
      case KeyEvent.VK_W: {
        this.input.setTargetMovingForwardKey(false);
        break;
      }
      case KeyEvent.VK_S: {
        this.input.setTargetMovingBackwardKey(false);
        break;
      }
      case KeyEvent.VK_D: {
        this.input.setTargetMovingRightKey(false);
        break;
      }

      case KeyEvent.VK_X: {
        this.input.setTargetMovingUp(false);
        break;
      }
      case KeyEvent.VK_Z: {
        this.input.setTargetMovingDown(false);
        break;
      }

      case KeyEvent.VK_F: {
        this.input.setOrbitInclinePositive(false);
        break;
      }
      case KeyEvent.VK_V: {
        this.input.setOrbitInclineNegative(false);
        break;
      }

      case KeyEvent.VK_Q: {
        this.input.setOrbitHeadingNegative(false);
        break;
      }
      case KeyEvent.VK_E: {
        this.input.setOrbitHeadingPositive(false);
        break;
      }

      case KeyEvent.VK_G: {
        this.input.setZoomingIn(false);
        break;
      }
      case KeyEvent.VK_B: {
        this.input.setZoomingOut(false);
        break;
      }
    }
  }

  public void toggleCameraEnabled()
  {
    final boolean enabled = this.sim.cameraIsEnabled();

    if (enabled) {
      System.out.println("Disabling camera");
      this.window.confinePointer(false);
    } else {
      System.out.println("Enabling camera");
      this.window.confinePointer(true);
      this.renderer.sendWantWarpPointer();
      this.input.setTargetMovingContinuousRight(0.0);
      this.input.setTargetMovingContinuousForward(0.0);
    }

    this.sim.cameraSetEnabled(!enabled);
  }
}
