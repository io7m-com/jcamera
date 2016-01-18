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

package com.io7m.jcamera;

import com.io7m.jtensors.Matrix4x4FType;
import com.io7m.jtensors.parameterized.PMatrix4x4FType;

/**
 * The type of (readable) cameras.
 */

public interface JCameraReadableType
{
  /**
   * Construct a view matrix for the camera, using preallocated storage in
   * <code>ctx</code> and writing the result to <code>m</code>.
   *
   * @param ctx
   *          Preallocated storage
   * @param m
   *          The output matrix
   */

  void cameraMakeViewMatrix(
    final JCameraContext ctx,
    final Matrix4x4FType m);

  /**
   * Construct a view matrix for the camera, using preallocated storage in
   * <code>ctx</code> and writing the result to <code>m</code>.
   *
   * @param ctx
   *          Preallocated storage
   * @param m
   *          The output matrix
   * @param <T0>
   *          The source coordinate system
   * @param <T1>
   *          The target coordiante system
   */

  <T0, T1> void cameraMakeViewPMatrix(
    final JCameraContext ctx,
    final PMatrix4x4FType<T0, T1> m);
}
