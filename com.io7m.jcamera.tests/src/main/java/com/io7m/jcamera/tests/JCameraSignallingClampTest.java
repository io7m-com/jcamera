/*
 * Copyright © 2021 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

package com.io7m.jcamera.tests;

import com.io7m.jcamera.JCameraSignallingClamp;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class JCameraSignallingClampTest
{
  @Test
  public void testClampMaximum()
  {
    final JCameraSignallingClamp sc = new JCameraSignallingClamp();
    sc.clamp(2.0, 0.0, 1.0);
    assertEquals(1.0, sc.getValue(), 0.0);
    assertTrue(sc.isClamped());
  }

  @Test
  public void testClampMinimum()
  {
    final JCameraSignallingClamp sc = new JCameraSignallingClamp();
    sc.clamp(-1.0, 0.0, 1.0);
    assertEquals(0.0, sc.getValue(), 0.0);
    assertTrue(sc.isClamped());
  }

  @Test
  public void testNoClamp()
  {
    final JCameraSignallingClamp sc = new JCameraSignallingClamp();
    sc.clamp(0.5, 0.0, 1.0);
    assertEquals(0.5, sc.getValue(), 0.0);
    assertFalse(sc.isClamped());
  }
}
