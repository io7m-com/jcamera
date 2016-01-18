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

package com.io7m.jcamera.examples.jogl.lab;

import com.io7m.jfunctional.ProcedureType;
import com.io7m.jnull.NullCheck;
import com.io7m.jnull.Nullable;
import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.RowGroup;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings({ "boxing", "synthetic-access" }) final class CameraFloatSlider implements
  CameraUIControlsType
{
  private static float convertFromSlider(
    final int x,
    final float min,
    final float max)
  {
    final float factor = (float) x / 100.0f;
    return (factor * (max - min)) + min;
  }

  private static int convertToSlider(
    final float f,
    final float min,
    final float max)
  {
    return (int) (((f - min) / (max - min)) * 100.0F);
  }

  private float                          current;
  private final JTextField               field;
  private final RowGroup                 group;
  private final JLabel                   label;
  private final float                    maximum;
  private final float                    minimum;
  private final JSlider                  slider;
  private @Nullable ProcedureType<Float> on_change;

  public void setOnChangeListener(
    final ProcedureType<Float> p)
  {
    this.on_change = NullCheck.notNull(p, "Procedure");
  }

  CameraFloatSlider(
    final String in_label,
    final float in_minimum,
    final float in_maximum)
  {
    this.label = new JLabel(NullCheck.notNull(in_label, "ForwardLabel"));
    this.group = new RowGroup();

    this.maximum = in_maximum;
    this.minimum = in_minimum;

    this.field = new JTextField(Float.toString(in_minimum));
    this.slider = new JSlider(SwingConstants.HORIZONTAL);
    this.slider.setMinimum(0);
    this.slider.setMaximum(100);
    this.slider.setValue(0);

    this.slider.addChangeListener(new ChangeListener() {
      @Override public void stateChanged(
        final @Nullable ChangeEvent ev)
      {
        final int slider_current = CameraFloatSlider.this.slider.getValue();
        CameraFloatSlider.this.current =
          CameraFloatSlider.convertFromSlider(
            slider_current,
            in_minimum,
            in_maximum);
        CameraFloatSlider.this.refreshText();
        CameraFloatSlider.this.callListener();
      }
    });

    this.field.setEditable(false);
  }

  @Override public void controlsAddToLayout(
    final DesignGridLayout layout)
  {
    layout
      .row()
      .group(this.group)
      .grid(this.label)
      .add(this.slider, 3)
      .add(this.field);
  }

  @Override public void controlsHide()
  {
    this.group.hide();
  }

  @Override public void controlsShow()
  {
    this.group.forceShow();
  }

  public float getCurrent()
  {
    return this.current;
  }

  public JTextField getField()
  {
    return this.field;
  }

  public JLabel getLabel()
  {
    return this.label;
  }

  public float getMaximum()
  {
    return this.maximum;
  }

  public float getMinimum()
  {
    return this.minimum;
  }

  private void refreshText()
  {
    final String ctext = String.format("%.6f", this.current);
    this.field.setText(ctext);
  }

  public void setCurrent(
    final float e)
  {
    this.slider.setValue(CameraFloatSlider.convertToSlider(
      e,
      this.minimum,
      this.maximum));
    this.current = e;
    this.refreshText();
    this.callListener();
  }

  private void callListener()
  {
    final ProcedureType<Float> proc = CameraFloatSlider.this.on_change;
    if (proc != null) {
      final float x = CameraFloatSlider.this.current;
      proc.call(x);
    }
  }
}
