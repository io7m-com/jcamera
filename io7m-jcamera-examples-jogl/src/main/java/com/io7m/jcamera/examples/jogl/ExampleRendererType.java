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

import com.io7m.jcamera.JCameraReadableSnapshotType;
import com.io7m.jfunctional.OptionType;
import com.io7m.jtensors.VectorReadable3FType;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL3;

import java.io.IOException;

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
   *          A camera snapshot
   * @param target
   *          An optional target to be drawn
   */

  void draw(
    final JCameraReadableSnapshotType s,
    final OptionType<VectorReadable3FType> target);

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
