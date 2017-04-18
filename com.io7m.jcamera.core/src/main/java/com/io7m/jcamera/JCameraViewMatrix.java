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
import com.io7m.jtensors.core.parameterized.matrices.PMatrices4x4D;
import com.io7m.jtensors.core.parameterized.matrices.PMatrix4x4D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrices4x4D;
import com.io7m.jtensors.core.unparameterized.matrices.Matrix4x4D;
import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
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
   * Construct a view matrix for the given axes.
   *
   * @param position The camera position
   * @param right    The right axis
   * @param up       The up axis
   * @param forward  The forward axis
   *
   * @return A view matrix
   */

  public static Matrix4x4D makeViewMatrix(
    final Vector3D position,
    final Vector3D right,
    final Vector3D up,
    final Vector3D forward)
  {
    final Matrix4x4D m_basis =
      Matrix4x4D.builder()
        .setR0c0(right.x())
        .setR0c1(right.y())
        .setR0c2(right.z())
        .setR0c3(0.0)
        .setR1c0(up.x())
        .setR1c1(up.y())
        .setR1c2(up.z())
        .setR1c3(0.0)
        .setR2c0(-forward.x())
        .setR2c1(-forward.y())
        .setR2c2(-forward.z())
        .setR2c3(0.0)
        .setR3c0(0.0)
        .setR3c1(0.0)
        .setR3c2(0.0)
        .setR3c3(1.0)
        .build();

    final Matrix4x4D m_trans =
      Matrices4x4D.ofTranslation(
        -position.x(),
        -position.y(),
        -position.z());

    return Matrices4x4D.multiply(m_basis, m_trans);
  }

  /**
   * Construct a view matrix for the given axes.
   *
   * @param position The camera position
   * @param right    The right axis
   * @param up       The up axis
   * @param forward  The forward axis
   * @param <T0>     The source coordinate system
   * @param <T1>     The target coordiante system
   *
   * @return A view matrix
   */

  @SuppressWarnings("unchecked")
  public static <T0, T1> PMatrix4x4D<T0, T1> makeViewPMatrix(
    final Vector3D position,
    final Vector3D right,
    final Vector3D up,
    final Vector3D forward)
  {
    final PMatrix4x4D<Object, Object> m_basis =
      PMatrix4x4D.builder()
        .setR0c0(right.x())
        .setR0c1(right.y())
        .setR0c2(right.z())
        .setR0c3(0.0)
        .setR1c0(up.x())
        .setR1c1(up.y())
        .setR1c2(up.z())
        .setR1c3(0.0)
        .setR2c0(-forward.x())
        .setR2c1(-forward.y())
        .setR2c2(-forward.z())
        .setR2c3(0.0)
        .setR3c0(0.0)
        .setR3c1(0.0)
        .setR3c2(0.0)
        .setR3c3(1.0)
        .build();

    final PMatrix4x4D<Object, Object> m_trans =
      PMatrices4x4D.ofTranslation(
        -position.x(),
        -position.y(),
        -position.z());

    return (PMatrix4x4D<T0, T1>) PMatrices4x4D.multiply(m_basis, m_trans);
  }
}
