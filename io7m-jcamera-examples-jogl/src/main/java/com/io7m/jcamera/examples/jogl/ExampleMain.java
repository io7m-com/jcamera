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

import javax.media.nativewindow.WindowClosingProtocol.WindowClosingMode;
import javax.media.opengl.DebugGL3;
import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.GLProfile;

import com.io7m.jcamera.JCameraInput;
import com.io7m.jcamera.JCameraMouseRegion;
import com.io7m.jcamera.JCameraRotationCoefficients;
import com.io7m.jcamera.JCameraScreenOrigin;
import com.io7m.jnull.Nullable;
import com.io7m.junreachable.UnreachableCodeException;
import com.jogamp.newt.event.InputEvent;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.Animator;

/**
 * Trivial camera example.
 */

public final class ExampleMain
{
  private ExampleMain()
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

    /**
     * @example Construct a new renderer.
     */

    final ExampleRenderer renderer = new ExampleRenderer();

    /**
     * @example Construct a new simulation and get access to the camera's
     *          input.
     */

    final ExampleSimulationType sim = new ExampleSimulation(renderer);
    final JCameraInput input = sim.getInput();

    /**
     * @example Declare a structure to hold mouse rotation coefficients, and a
     *          mouse region configured with an origin that matches that of
     *          JOGL's windowing system.
     */

    final JCameraRotationCoefficients rotations =
      new JCameraRotationCoefficients();
    final JCameraMouseRegion mouse_region =
      JCameraMouseRegion.newRegion(
        JCameraScreenOrigin.SCREEN_ORIGIN_TOP_LEFT,
        640,
        480);

    /**
     * @example Initialize JOGL and open a window.
     */

    final GLProfile profile = GLProfile.get(GLProfile.GL3);
    final GLCapabilities caps = new GLCapabilities(profile);
    final GLWindow window = GLWindow.create(caps);
    window.setSize(640, 480);
    window.setTitle(ExampleMain.class.getCanonicalName());

    /**
     * Construct an animator to regularly refresh the screen.
     */

    final Animator anim = new Animator();
    anim.add(window);

    /**
     * @example The main OpenGL event listener. The display function is called
     *          repeatedly and will integrate the camera and then render the
     *          scene.
     */

    window.addGLEventListener(new GLEventListener() {
      private int frame;

      @Override public void init(
        final @Nullable GLAutoDrawable drawable)
      {
        // Nothing
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
        ++this.frame;

        final GL3 g = new DebugGL3(drawable.getGL().getGL3());
        assert g != null;
        g.glClear(GL.GL_COLOR_BUFFER_BIT);

        if (this.frame == 1) {
          return;
        }

        if (this.frame == 2) {
          try {
            renderer.init(window, g);
            renderer.reshape(window.getWidth(), window.getHeight());
          } catch (final IOException e) {
            throw new GLException(e);
          }
        }

        /**
         * Draw the scene!
         */

        renderer.draw();
      }

      @Override public void reshape(
        final @Nullable GLAutoDrawable drawable,
        final int x,
        final int y,
        final int width,
        final int height)
      {
        mouse_region.setWidth(width);
        mouse_region.setHeight(height);
        renderer.reshape(width, height);
      }
    });

    /**
     * @example Mouse event listener, called asynchronously whenever the mouse
     *          moves.
     */

    window.addMouseListener(new MouseAdapter() {
      @Override public void mouseMoved(
        final @Nullable MouseEvent e)
      {
        assert e != null;

        /**
         * If the camera is enabled, get the rotation coefficients for the
         * mouse movement.
         */

        if (sim.cameraIsEnabled()) {
          mouse_region.getCoefficients(e.getX(), e.getY(), rotations);
          input.addRotationAroundHorizontal(rotations.getHorizontal());
          input.addRotationAroundVertical(rotations.getVertical());
        }
      }
    });

    /**
     * @example Keyboard event listener. Called asynchronously whenever the
     *          user presses or releases a key on the keyboard.
     */

    window.addKeyListener(new KeyListener() {
      @Override public void keyPressed(
        final @Nullable KeyEvent e)
      {
        assert e != null;

        /**
         * Ignore events that are the result of keyboard auto-repeat. This
         * means there's one single event when a key is pressed, and another
         * when it is released (as opposed to an endless stream of both when
         * the key is held down).
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
            input.setMovingLeft(true);
            break;
          }
          case KeyEvent.VK_W:
          {
            input.setMovingForward(true);
            break;
          }
          case KeyEvent.VK_S:
          {
            input.setMovingBackward(true);
            break;
          }
          case KeyEvent.VK_D:
          {
            input.setMovingRight(true);
            break;
          }
          case KeyEvent.VK_E:
          {
            input.setMovingUp(true);
            break;
          }
          case KeyEvent.VK_Q:
          {
            input.setMovingDown(true);
            break;
          }
        }
      }

      @Override public void keyReleased(
        final @Nullable KeyEvent e)
      {
        assert e != null;

        /**
         * Ignore events that are the result of keyboard auto-repeat. This
         * means there's one single event when a key is pressed, and another
         * when it is released (as opposed to an endless stream of both when
         * the key is held down).
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
            final boolean enabled = sim.cameraIsEnabled();

            if (enabled) {
              System.out.println("Disabling camera");
              window.confinePointer(false);
            } else {
              System.out.println("Enabling camera");
              window.confinePointer(true);
              renderer.setWantWarpPointer();
              input.setRotationHorizontal(0);
              input.setRotationVertical(0);
            }

            sim.cameraSetEnabled(!enabled);
            break;
          }

          /**
           * Pressing 'P' makes the mouse cursor visible/invisible.
           */

          case KeyEvent.VK_P:
          {
            System.out.printf(
              "Making pointer %s\n",
              window.isPointerVisible() ? "invisible" : "visible");
            window.setPointerVisible(!window.isPointerVisible());
            break;
          }

          /**
           * Pressing F switches between windowed and fullscreen mode.
           */

          case KeyEvent.VK_F:
          {
            window.setFullscreen(!window.isFullscreen());
            break;
          }

          /**
           * Standard WASD camera controls, with E and Q moving up and down,
           * respectively.
           */

          case KeyEvent.VK_A:
          {
            input.setMovingLeft(false);
            break;
          }
          case KeyEvent.VK_W:
          {
            input.setMovingForward(false);
            break;
          }
          case KeyEvent.VK_S:
          {
            input.setMovingBackward(false);
            break;
          }
          case KeyEvent.VK_D:
          {
            input.setMovingRight(false);
            break;
          }
          case KeyEvent.VK_E:
          {
            input.setMovingUp(false);
            break;
          }
          case KeyEvent.VK_Q:
          {
            input.setMovingDown(false);
            break;
          }
        }
      }
    });

    /**
     * @example Close the program when the window closes.
     */

    window.addWindowListener(new WindowAdapter() {
      @Override public void windowDestroyed(
        final @Nullable WindowEvent e)
      {
        System.out.println("Stopping animator");
        anim.stop();
        System.out.println("Stopping simulation");
        sim.stop();
        System.out.println("Exiting");
        System.exit(0);
      }
    });

    window.setDefaultCloseOperation(WindowClosingMode.DISPOSE_ON_CLOSE);
    window.setVisible(true);

    /**
     * Start everything running.
     */

    sim.start();
    anim.start();
  }
}
