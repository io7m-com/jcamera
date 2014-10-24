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
import java.util.concurrent.atomic.AtomicBoolean;

import javax.media.nativewindow.WindowClosingProtocol.WindowClosingMode;
import javax.media.opengl.DebugGL3;
import javax.media.opengl.GL;
import javax.media.opengl.GL3;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.GLProfile;

import com.io7m.jcamera.JCameraFPSStyle;
import com.io7m.jcamera.JCameraFPSStyleIntegrator;
import com.io7m.jcamera.JCameraFPSStyleIntegratorType;
import com.io7m.jcamera.JCameraFPSStyleType;
import com.io7m.jcamera.JCameraInput;
import com.io7m.jcamera.JCameraMouseRegion;
import com.io7m.jcamera.JCameraRotationCoefficients;
import com.io7m.jcamera.JCameraScreenOrigin;
import com.io7m.jnull.Nullable;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.VectorI3F;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.junreachable.UnreachableCodeException;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseAdapter;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.util.FPSAnimator;

/**
 * Trivial camera example.
 */

public final class ExampleMain
{
  private static final int FRAMES_PER_SECOND = 60;

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
    /**
     * @example Construct an input and a camera.
     */

    final JCameraInput input = JCameraInput.newInput();
    final JCameraFPSStyleType camera = JCameraFPSStyle.newCamera();

    /**
     * @example Declare a vector to hold mouse rotation coefficients, a flag
     *          that states whether or not the camera is enabled, a matrix to
     *          hold the view matrix for the scene, and a mouse region
     *          configured with an origin that matches that of JOGL's
     *          windowing system.
     */

    final JCameraRotationCoefficients rotation_coefficients =
      new JCameraRotationCoefficients();
    final AtomicBoolean camera_enabled = new AtomicBoolean();
    final MatrixM4x4F.Context matrix_context = new MatrixM4x4F.Context();
    final MatrixM4x4F view_matrix = new MatrixM4x4F();
    final JCameraMouseRegion mouse_region =
      JCameraMouseRegion.newRegion(
        JCameraScreenOrigin.SCREEN_ORIGIN_TOP_LEFT,
        640,
        480);

    /**
     * @example Construct an integrator using the default implementations.
     */

    final JCameraFPSStyleIntegratorType integrator =
      JCameraFPSStyleIntegrator.newIntegrator(camera, input);

    /**
     * @example Initialize JOGL and open a window.
     */

    final GLProfile profile = GLProfile.get(GLProfile.GL3);
    final GLCapabilities caps = new GLCapabilities(profile);
    final GLWindow window = GLWindow.create(caps);
    window.setSize(640, 480);
    window.setTitle(ExampleMain.class.getCanonicalName());

    /**
     * @example The main OpenGL event listener. The display function is called
     *          at around 60fps and will integrate the camera and then render
     *          the scene.
     */

    window.addGLEventListener(new GLEventListener() {
      private @Nullable ExampleScene scene;
      private int                    frame;

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
            this.scene = new ExampleScene(g);
            this.scene.reshape(window.getWidth(), window.getHeight());
          } catch (final IOException e) {
            throw new GLException(e);
          }
        }

        /**
         * Integrate the camera if it is enabled.
         */

        if (camera_enabled.get()) {
          integrator.integrate(1.0f / ExampleMain.FRAMES_PER_SECOND);
          camera.cameraMakeViewMatrix(matrix_context, view_matrix);
        } else {

          /**
           * Otherwise, produce a view matrix that simulates a simple fixed
           * camera.
           */

          MatrixM4x4F.setIdentity(view_matrix);
          final VectorReadable3FType origin = new VectorI3F(0.0f, 2.0f, 3.0f);
          final VectorReadable3FType target =
            new VectorI3F(0.0f, 0.0f, -3.0f);
          final VectorReadable3FType up = new VectorI3F(0.0f, 1.0f, 0.0f);
          MatrixM4x4F.lookAtWithContext(
            matrix_context,
            origin,
            target,
            up,
            view_matrix);
        }

        /**
         * Draw the scene!
         */

        assert this.scene != null;
        this.scene.draw(view_matrix);

        /**
         * Warp the cursor back to the center of the screen.
         */

        if (camera_enabled.get()) {
          window.warpPointer(
            (int) mouse_region.getCenterX(),
            (int) mouse_region.getCenterY());
        }
      }

      @Override public void reshape(
        final @Nullable GLAutoDrawable drawable,
        final int x,
        final int y,
        final int width,
        final int height)
      {
        /**
         * Update the mouse region if the size of the window has changed.
         */

        mouse_region.setWidth(width);
        mouse_region.setHeight(height);

        if (this.scene != null) {
          this.scene.reshape(width, height);
        }
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

        if (camera_enabled.get()) {
          mouse_region.getCoefficients(
            e.getX(),
            e.getY(),
            rotation_coefficients);

          input.addRotationAroundHorizontal(rotation_coefficients
            .getHorizontal());
          input.addRotationAroundVertical(rotation_coefficients.getVertical());
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

        switch (e.getKeyCode()) {

        /**
         * Standard WASD camera controls, with F and V moving up and down,
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
          case KeyEvent.VK_F:
          {
            input.setMovingUp(true);
            break;
          }
          case KeyEvent.VK_V:
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

        switch (e.getKeyCode()) {

        /**
         * Pressing 'M' enables/disables the camera.
         */

          case KeyEvent.VK_M:
          {
            final boolean enabled = camera_enabled.get();

            if (enabled) {
              System.out.println("Disabling camera");
              window.confinePointer(false);
            } else {
              System.out.println("Enabling camera");
              window.confinePointer(true);
              window.warpPointer(
                (int) mouse_region.getCenterX(),
                (int) mouse_region.getCenterY());
              input.setRotationHorizontal(0);
              input.setRotationVertical(0);
            }

            camera_enabled.set(!enabled);
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
           * Standard WASD camera controls, with F and V moving up and down,
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
          case KeyEvent.VK_F:
          {
            input.setMovingUp(false);
            break;
          }
          case KeyEvent.VK_V:
          {
            input.setMovingDown(false);
            break;
          }
        }
      }
    });

    /**
     * Close the program when the window closes.
     */

    window.addWindowListener(new WindowAdapter() {
      @Override public void windowDestroyed(
        final @Nullable WindowEvent e)
      {
        System.exit(0);
      }
    });

    window.setDefaultCloseOperation(WindowClosingMode.DISPOSE_ON_CLOSE);
    window.setVisible(true);

    /**
     * Tell the screen to refresh frequently.
     */

    final FPSAnimator anim = new FPSAnimator(ExampleMain.FRAMES_PER_SECOND);
    anim.setUpdateFPSFrames(ExampleMain.FRAMES_PER_SECOND, System.err);
    anim.add(window);
    anim.start();
  }
}
