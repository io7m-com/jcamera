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

import java.util.concurrent.ExecutorService;

import com.io7m.jcamera.JCameraFPSStyleInput;
import com.io7m.jnull.Nullable;
import com.jogamp.newt.event.InputEvent;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;

/**
 * The key listener used to handle keyboard events.
 */

// CHECKSTYLE_JAVADOC:OFF

@SuppressWarnings("synthetic-access") public final class ExampleFPSStyleKeyListener implements
  KeyListener
{
  private final ExampleFPSStyleSimulationType sim;
  private final ExecutorService               background_workers;
  private final ExampleRendererType           renderer;
  private final JCameraFPSStyleInput          input;
  private final GLWindow                      window;

  public ExampleFPSStyleKeyListener(
    final ExampleFPSStyleSimulationType in_sim,
    final ExecutorService in_background_workers,
    final ExampleRendererType in_renderer,
    final GLWindow in_window)
  {
    this.sim = in_sim;
    this.background_workers = in_background_workers;
    this.renderer = in_renderer;
    this.input = in_sim.getInput();
    this.window = in_window;
  }

  @Override public void keyPressed(
    final @Nullable KeyEvent e)
  {
    assert e != null;

    /**
     * Ignore events that are the result of keyboard auto-repeat. This means
     * there's one single event when a key is pressed, and another when it is
     * released (as opposed to an endless stream of both when the key is held
     * down).
     */

    if ((e.getModifiers() & InputEvent.AUTOREPEAT_MASK) == InputEvent.AUTOREPEAT_MASK) {
      return;
    }

    switch (e.getKeyCode()) {

    /**
     * Standard WASD camera controls, with E and Q moving up and down,
     * respectively.
     */

      case KeyEvent.VK_A:
      {
        this.input.setMovingLeft(true);
        break;
      }
      case KeyEvent.VK_W:
      {
        this.input.setMovingForward(true);
        break;
      }
      case KeyEvent.VK_S:
      {
        this.input.setMovingBackward(true);
        break;
      }
      case KeyEvent.VK_D:
      {
        this.input.setMovingRight(true);
        break;
      }
      case KeyEvent.VK_E:
      {
        this.input.setMovingUp(true);
        break;
      }
      case KeyEvent.VK_Q:
      {
        this.input.setMovingDown(true);
        break;
      }
    }
  }

  @Override public void keyReleased(
    final @Nullable KeyEvent e)
  {
    assert e != null;

    /**
     * Ignore events that are the result of keyboard auto-repeat. This means
     * there's one single event when a key is pressed, and another when it is
     * released (as opposed to an endless stream of both when the key is held
     * down).
     */

    if ((e.getModifiers() & InputEvent.AUTOREPEAT_MASK) == InputEvent.AUTOREPEAT_MASK) {
      return;
    }

    switch (e.getKeyCode()) {

    /**
     * Pressing 'M' enables/disables the camera.
     */

      case KeyEvent.VK_M:
      {
        this.toggleCameraEnabled();
        break;
      }

      /**
       * Pressing 'P' makes the mouse cursor visible/invisible.
       */

      case KeyEvent.VK_P:
      {
        System.out.printf(
          "Making pointer %s\n",
          this.window.isPointerVisible() ? "invisible" : "visible");
        this.window.setPointerVisible(!this.window.isPointerVisible());
        break;
      }

      /**
       * Pressing enter switches between windowed and fullscreen mode. JOGL
       * requires that this be executed on a background thread.
       */

      case KeyEvent.VK_ENTER:
      {
        this.background_workers.execute(new Runnable() {
          @Override public void run()
          {
            final boolean mode =
              !ExampleFPSStyleKeyListener.this.window.isFullscreen();
            ExampleFPSStyleKeyListener.this.window.setFullscreen(mode);
          }
        });
        break;
      }

      /**
       * Standard WASD camera controls, with E and Q moving up and down,
       * respectively.
       */

      case KeyEvent.VK_A:
      {
        this.input.setMovingLeft(false);
        break;
      }
      case KeyEvent.VK_W:
      {
        this.input.setMovingForward(false);
        break;
      }
      case KeyEvent.VK_S:
      {
        this.input.setMovingBackward(false);
        break;
      }
      case KeyEvent.VK_D:
      {
        this.input.setMovingRight(false);
        break;
      }
      case KeyEvent.VK_E:
      {
        this.input.setMovingUp(false);
        break;
      }
      case KeyEvent.VK_Q:
      {
        this.input.setMovingDown(false);
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
      this.input.setRotationHorizontal(0.0F);
      this.input.setRotationVertical(0.0F);
    }

    this.sim.cameraSetEnabled(!enabled);
  }
}
