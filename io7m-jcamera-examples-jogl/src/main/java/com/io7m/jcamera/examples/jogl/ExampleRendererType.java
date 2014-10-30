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

import javax.media.opengl.GL3;

import com.io7m.jcamera.JCameraFPSStyleSnapshot;
import com.jogamp.newt.opengl.GLWindow;

/**
 * The interface exposed by the renderer to JOGL.
 */

public interface ExampleRendererType extends ExampleRendererControllerType
{
  /**
   * Initialize the scene, using the given window and OpenGL interface.
   *
   * @param in_window
   *          The window
   * @param in_gl
   *          The OpenGL interface
   * @throws IOException
   *           On I/O errors
   */

  void init(
    GLWindow in_window,
    GL3 in_gl)
    throws IOException;

  /**
   * Draw the scene.
   *
   * @param s
   *          The current camera snapshot
   */

  void draw(
    final JCameraFPSStyleSnapshot s);

  /**
   * Indicate that the screen has been resized.
   *
   * @param width
   *          The new width
   * @param height
   *          The new height
   */

  void reshape(
    int width,
    int height);
}
