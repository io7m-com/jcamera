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
import com.io7m.jtensors.parameterized.PMatrixM4x4F;
import com.io7m.jtensors.parameterized.PVectorM3F;

/**
 * Preallocated storage.
 */

@EqualityReference public final class JCameraContext
{
  private final MatrixM4x4F        derived_matrix_r;
  private final MatrixM4x4F        derived_matrix_t;
  private final PMatrixM4x4F<?, ?> derived_pmatrix_r;
  private final PMatrixM4x4F<?, ?> derived_pmatrix_t;
  private final PVectorM3F<?>      ptemporary;
  private final VectorM3F          temporary;

  MatrixM4x4F getTemporaryMatrixR()
  {
    return this.derived_matrix_r;
  }

  MatrixM4x4F getTemporaryMatrixT()
  {
    return this.derived_matrix_t;
  }

  PMatrixM4x4F<?, ?> getTemporaryPMatrixR()
  {
    return this.derived_pmatrix_r;
  }

  PMatrixM4x4F<?, ?> getTemporaryPMatrixT()
  {
    return this.derived_pmatrix_t;
  }

  PVectorM3F<?> getPTemporaryVector()
  {
    return this.ptemporary;
  }

  VectorM3F getTemporaryVector()
  {
    return this.temporary;
  }

  /**
   * Create new temporary storage for calculating matrices.
   */

  public JCameraContext()
  {
    this.derived_matrix_r = new MatrixM4x4F();
    this.derived_matrix_t = new MatrixM4x4F();
    this.derived_pmatrix_r = new PMatrixM4x4F<Object, Object>();
    this.derived_pmatrix_t = new PMatrixM4x4F<Object, Object>();
    this.temporary = new VectorM3F();
    this.ptemporary = new PVectorM3F<Object>();
  }
}
