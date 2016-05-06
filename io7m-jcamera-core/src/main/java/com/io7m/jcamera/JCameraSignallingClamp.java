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

/**
 * A "signalling" clamp operation. That is, a function that can clamp a value to
 * a given inclusive range and sets a flag to indicate whether or not the
 * original value was actually outside of the range.
 */

@EqualityReference
public final class JCameraSignallingClamp
{
  private boolean clamped;
  private float   value;

  /**
   * Construct a new signalling clamp function.
   */

  public JCameraSignallingClamp()
  {

  }

  /**
   * Clamp the value {@code x} to the given inclusive range {@code [min,
   * max]}.
   *
   * @param x   The value
   * @param min The minimum value
   * @param max The maximum value
   */

  public void clamp(
    final float x,
    final float min,
    final float max)
  {
    this.value = x;
    this.clamped = false;

    if (x > max) {
      this.value = max;
      this.clamped = true;
    }
    if (x < min) {
      this.value = min;
      this.clamped = true;
    }
  }

  /**
   * @return The value resulting from the most recent clamp operation.
   */

  public float getValue()
  {
    return this.value;
  }

  /**
   * @return {@code true} if the value passed to the most recent clamp
   * operation was not in the inclusive range.
   */

  public boolean isClamped()
  {
    return this.clamped;
  }
}
