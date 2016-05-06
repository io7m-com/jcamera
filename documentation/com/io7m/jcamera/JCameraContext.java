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
import com.io7m.jtensors.MatrixHeapArrayM4x4F;
import com.io7m.jtensors.Vector3FType;
import com.io7m.jtensors.VectorM3F;
import com.io7m.jtensors.parameterized.PMatrix4x4FType;
import com.io7m.jtensors.parameterized.PMatrixHeapArrayM4x4F;
import com.io7m.jtensors.parameterized.PVector3FType;
import com.io7m.jtensors.parameterized.PVectorM3F;

/**
 * Preallocated storage.
 */

@EqualityReference
public final class JCameraContext
{
  private final Matrix4x4FType        derived_matrix_r;
  private final Matrix4x4FType        derived_matrix_t;
  private final PMatrix4x4FType<?, ?> derived_pmatrix_r;
  private final PMatrix4x4FType<?, ?> derived_pmatrix_t;
  private final PVector3FType<?>      ptemporary;
  private final Vector3FType          temporary;

  /**
   * Create new temporary storage for calculating matrices.
   */

  public JCameraContext()
  {
    this.derived_matrix_r = MatrixHeapArrayM4x4F.newMatrix();
    this.derived_matrix_t = MatrixHeapArrayM4x4F.newMatrix();
    this.derived_pmatrix_r = PMatrixHeapArrayM4x4F.newMatrix();
    this.derived_pmatrix_t = PMatrixHeapArrayM4x4F.newMatrix();
    this.temporary = new VectorM3F();
    this.ptemporary = new PVectorM3F<Object>();
  }

  PVector3FType<?> getPTemporaryVector()
  {
    return this.ptemporary;
  }

  Matrix4x4FType getTemporaryMatrixR()
  {
    return this.derived_matrix_r;
  }

  Matrix4x4FType getTemporaryMatrixT()
  {
    return this.derived_matrix_t;
  }

  PMatrix4x4FType<?, ?> getTemporaryPMatrixR()
  {
    return this.derived_pmatrix_r;
  }

  PMatrix4x4FType<?, ?> getTemporaryPMatrixT()
  {
    return this.derived_pmatrix_t;
  }

  Vector3FType getTemporaryVector()
  {
    return this.temporary;
  }
}
