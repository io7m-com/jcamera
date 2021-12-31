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

package com.io7m.jcamera.examples.jogl.lab;

import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.RowGroup;

import javax.swing.JLabel;

final class CameraVector3Field implements CameraUIControlsType
{
  private final CameraFloatField x;
  private final CameraFloatField y;
  private final CameraFloatField z;
  private final RowGroup group;
  private final String title;

  CameraVector3Field(
    final String in_title)
  {
    this.x = new CameraFloatField();
    this.y = new CameraFloatField();
    this.z = new CameraFloatField();
    this.group = new RowGroup();
    this.title = in_title;
  }

  void setValue(
    final Vector3D v)
  {
    this.x.setValue(v.x());
    this.y.setValue(v.y());
    this.z.setValue(v.z());
  }

  @Override
  public void controlsAddToLayout(
    final DesignGridLayout dg)
  {
    dg
      .row()
      .group(this.group)
      .grid(new JLabel(this.title))
      .add(this.x)
      .add(this.y)
      .add(this.z);
  }

  @Override
  public void controlsHide()
  {
    this.group.hide();
  }

  @Override
  public void controlsShow()
  {
    this.group.forceShow();
  }
}
