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

import com.io7m.jequality.annotations.EqualityReference;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.parameterized.PMatrixM4x4F;
import com.io7m.jtensors.parameterized.PVectorM3F;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * Functions to produce view matrices.
 */

@EqualityReference public final class JCameraViewMatrix
{
  /**
   * Construct a view matrix from the given position, forward, up, and, right
   * vectors.
   *
   * @param ctx
   *          Preallocated storage
   * @param m
   *          The output matrix
   * @param position
   *          The position
   * @param right
   *          The right vector
   * @param up
   *          The up vector
   * @param forward
   *          The forward vector
   */

  public static void makeViewMatrix(
    final JCameraContext ctx,
    final MatrixM4x4F m,
    final VectorReadable3FType position,
    final VectorReadable3FType right,
    final VectorReadable3FType up,
    final VectorReadable3FType forward)
  {
    /**
     * Calculate basis vectors for rotated coordinate system.
     */

    final MatrixM4x4F r = ctx.getTemporaryMatrixR();
    MatrixM4x4F.setIdentity(r);
    r.set(0, 0, right.getXF());
    r.set(0, 1, right.getYF());
    r.set(0, 2, right.getZF());
    r.set(1, 0, up.getXF());
    r.set(1, 1, up.getYF());
    r.set(1, 2, up.getZF());
    r.set(2, 0, -forward.getXF());
    r.set(2, 1, -forward.getYF());
    r.set(2, 2, -forward.getZF());

    /**
     * Calculate translation matrix.
     */

    final MatrixM4x4F t = ctx.getTemporaryMatrixT();
    final VectorM3F tv = ctx.getTemporaryVector();
    MatrixM4x4F.setIdentity(t);
    tv.set3F(-position.getXF(), -position.getYF(), -position.getZF());
    MatrixM4x4F.makeTranslation3FInto(tv, t);

    /**
     * Produce final transform.
     */

    MatrixM4x4F.multiply(r, t, m);
  }

  /**
   * Construct a view matrix from the given position, forward, up, and, right
   * vectors.
   *
   * @param ctx
   *          Preallocated storage
   * @param m
   *          The output matrix
   * @param position
   *          The position
   * @param right
   *          The right vector
   * @param up
   *          The up vector
   * @param forward
   *          The forward vector
   * @param <T0>
   *          The source coordinate space
   * @param <T1>
   *          The target coordinate space
   */

  @SuppressWarnings("unchecked") public static <T0, T1> void makeViewPMatrix(
    final JCameraContext ctx,
    final PMatrixM4x4F<T0, T1> m,
    final VectorReadable3FType position,
    final VectorReadable3FType right,
    final VectorReadable3FType up,
    final VectorReadable3FType forward)
  {
    /**
     * Calculate basis vectors for rotated coordinate system.
     */

    final PMatrixM4x4F<Object, Object> r =
      (PMatrixM4x4F<Object, Object>) ctx.getTemporaryPMatrixR();
    PMatrixM4x4F.setIdentity(r);
    r.set(0, 0, right.getXF());
    r.set(0, 1, right.getYF());
    r.set(0, 2, right.getZF());
    r.set(1, 0, up.getXF());
    r.set(1, 1, up.getYF());
    r.set(1, 2, up.getZF());
    r.set(2, 0, -forward.getXF());
    r.set(2, 1, -forward.getYF());
    r.set(2, 2, -forward.getZF());

    /**
     * Calculate translation matrix.
     */

    final PMatrixM4x4F<Object, Object> t =
      (PMatrixM4x4F<Object, Object>) ctx.getTemporaryPMatrixT();
    PMatrixM4x4F.setIdentity(t);
    final PVectorM3F<?> tv = ctx.getPTemporaryVector();
    tv.set3F(-position.getXF(), -position.getYF(), -position.getZF());
    PMatrixM4x4F.makeTranslation3FInto(tv, t);

    /**
     * Produce final transform.
     */

    PMatrixM4x4F.multiply(
      (PMatrixM4x4F<Object, T1>) r,
      (PMatrixM4x4F<T0, Object>) t,
      m);
  }

  private JCameraViewMatrix()
  {
    throw new UnreachableCodeException();
  }
}
