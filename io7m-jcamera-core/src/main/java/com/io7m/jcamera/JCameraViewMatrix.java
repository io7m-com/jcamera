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

package com.io7m.jcamera;

import com.io7m.jequality.annotations.EqualityReference;
import com.io7m.jtensors.Matrix4x4FType;
import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.Vector3FType;
import com.io7m.jtensors.VectorReadable3FType;
import com.io7m.jtensors.parameterized.PMatrix4x4FType;
import com.io7m.jtensors.parameterized.PMatrixM4x4F;
import com.io7m.jtensors.parameterized.PVector3FType;
import com.io7m.junreachable.UnreachableCodeException;

/**
 * Functions to produce view matrices.
 */

@EqualityReference
public final class JCameraViewMatrix
{
  private JCameraViewMatrix()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Construct a view matrix from the given position, forward, up, and, right
   * vectors.
   *
   * @param ctx      Preallocated storage
   * @param m        The output matrix
   * @param position The position
   * @param right    The right vector
   * @param up       The up vector
   * @param forward  The forward vector
   */

  public static void makeViewMatrix(
    final JCameraContext ctx,
    final Matrix4x4FType m,
    final VectorReadable3FType position,
    final VectorReadable3FType right,
    final VectorReadable3FType up,
    final VectorReadable3FType forward)
  {
    /**
     * Calculate basis vectors for rotated coordinate system.
     */

    final Matrix4x4FType r = ctx.getTemporaryMatrixR();

    r.setR0C0F(right.getXF());
    r.setR0C1F(right.getYF());
    r.setR0C2F(right.getZF());
    r.setR0C3F(0.0f);

    r.setR1C0F(up.getXF());
    r.setR1C1F(up.getYF());
    r.setR1C2F(up.getZF());
    r.setR1C3F(0.0f);

    r.setR2C0F(-forward.getXF());
    r.setR2C1F(-forward.getYF());
    r.setR2C2F(-forward.getZF());
    r.setR2C3F(0.0f);

    r.setR3C0F(0.0f);
    r.setR3C1F(0.0f);
    r.setR3C2F(0.0f);
    r.setR3C3F(1.0f);

    /**
     * Calculate translation matrix.
     */

    final Matrix4x4FType t = ctx.getTemporaryMatrixT();
    final Vector3FType tv = ctx.getTemporaryVector();
    MatrixM4x4F.setIdentity(t);
    tv.set3F(-position.getXF(), -position.getYF(), -position.getZF());
    MatrixM4x4F.makeTranslation3F(tv, t);

    /**
     * Produce final transform.
     */

    MatrixM4x4F.multiply(r, t, m);
  }

  /**
   * Construct a view matrix from the given position, forward, up, and, right
   * vectors.
   *
   * @param ctx      Preallocated storage
   * @param m        The output matrix
   * @param position The position
   * @param right    The right vector
   * @param up       The up vector
   * @param forward  The forward vector
   * @param <T0>     The source coordinate space
   * @param <T1>     The target coordinate space
   */

  @SuppressWarnings("unchecked")
  public static <T0, T1> void makeViewPMatrix(
    final JCameraContext ctx,
    final PMatrix4x4FType<T0, T1> m,
    final VectorReadable3FType position,
    final VectorReadable3FType right,
    final VectorReadable3FType up,
    final VectorReadable3FType forward)
  {
    /**
     * Calculate basis vectors for rotated coordinate system.
     */

    final PMatrix4x4FType<Object, Object> r =
      (PMatrix4x4FType<Object, Object>) ctx.getTemporaryPMatrixR();

    r.setR0C0F(right.getXF());
    r.setR0C1F(right.getYF());
    r.setR0C2F(right.getZF());
    r.setR0C3F(0.0f);

    r.setR1C0F(up.getXF());
    r.setR1C1F(up.getYF());
    r.setR1C2F(up.getZF());
    r.setR1C3F(0.0f);

    r.setR2C0F(-forward.getXF());
    r.setR2C1F(-forward.getYF());
    r.setR2C2F(-forward.getZF());
    r.setR2C3F(0.0f);

    r.setR3C0F(0.0f);
    r.setR3C1F(0.0f);
    r.setR3C2F(0.0f);
    r.setR3C3F(1.0f);

    /**
     * Calculate translation matrix.
     */

    final PMatrix4x4FType<Object, Object> t =
      (PMatrix4x4FType<Object, Object>) ctx.getTemporaryPMatrixT();
    MatrixM4x4F.setIdentity(t);
    final PVector3FType<?> tv = ctx.getPTemporaryVector();
    tv.set3F(-position.getXF(), -position.getYF(), -position.getZF());
    MatrixM4x4F.makeTranslation3F(tv, t);

    /**
     * Produce final transform.
     */

    PMatrixM4x4F.multiply(
      (PMatrix4x4FType<Object, T1>) r,
      (PMatrix4x4FType<T0, Object>) t,
      m);
  }
}
