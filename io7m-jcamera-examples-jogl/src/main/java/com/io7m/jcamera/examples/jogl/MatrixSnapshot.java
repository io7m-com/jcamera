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

import com.io7m.jtensors.MatrixM4x4F;
import com.io7m.jtensors.MatrixReadable4x4FType;

/**
 * Immutable matrix snapshots.
 */

public final class MatrixSnapshot
{
  /**
   * Take a snapshot of the given matrix.
   *
   * @param in
   *          The matrix
   * @return A snapshot
   */

  public static MatrixSnapshot pack(
    final MatrixReadable4x4FType in)
  {
    final float[] m = new float[16];
    for (int column = 0; column < 4; ++column) {
      for (int row = 0; row < 4; ++row) {
        m[(column * 4) + row] = in.getRowColumnF(row, column);
      }
    }
    return new MatrixSnapshot(m);
  }

  private final float[] data;

  private MatrixSnapshot(
    final float[] m)
  {
    this.data = m;
  }

  /**
   * Unpack the snapshot into the given matrix.
   *
   * @param out
   *          The output matrix.
   */

  public void unpack(
    final MatrixM4x4F out)
  {
    for (int column = 0; column < 4; ++column) {
      for (int row = 0; row < 4; ++row) {
        final int index = (column * 4) + row;
        out.set(row, column, this.data[index]);
      }
    }
  }
}
