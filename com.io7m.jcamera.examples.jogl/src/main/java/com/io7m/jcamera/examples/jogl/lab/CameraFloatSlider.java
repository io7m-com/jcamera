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

import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.RowGroup;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.util.Objects;
import java.util.function.Consumer;

final class CameraFloatSlider
  implements CameraUIControlsType
{
  private final JTextField field;
  private final RowGroup group;
  private final JLabel label;
  private final double maximum;
  private final double minimum;
  private final JSlider slider;
  private double current;
  private Consumer<Double> on_change;

  CameraFloatSlider(
    final String in_label,
    final double in_minimum,
    final double in_maximum)
  {
    this.label = new JLabel(Objects.requireNonNull(
      in_label,
      "ForwardLabel"));
    this.group = new RowGroup();

    this.maximum = in_maximum;
    this.minimum = in_minimum;

    this.field = new JTextField(Double.toString(in_minimum));
    this.slider = new JSlider(SwingConstants.HORIZONTAL);
    this.slider.setMinimum(0);
    this.slider.setMaximum(100);
    this.slider.setValue(0);

    this.slider.addChangeListener(ev -> {
      final int slider_current = CameraFloatSlider.this.slider.getValue();
      this.current = convertFromSlider(slider_current, in_minimum, in_maximum);
      this.refreshText();
      this.callListener();
    });

    this.field.setEditable(false);
  }

  private static double convertFromSlider(
    final int x,
    final double min,
    final double max)
  {
    final double factor = (double) x / 100.0;
    return (factor * (max - min)) + min;
  }

  private static int convertToSlider(
    final double f,
    final double min,
    final double max)
  {
    return (int) (((f - min) / (max - min)) * 100.0);
  }

  public void setOnChangeListener(
    final Consumer<Double> p)
  {
    this.on_change = Objects.requireNonNull(p, "Procedure");
  }

  @Override
  public void controlsAddToLayout(
    final DesignGridLayout layout)
  {
    layout
      .row()
      .group(this.group)
      .grid(this.label)
      .add(this.slider, 3)
      .add(this.field);
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

  public double getCurrent()
  {
    return this.current;
  }

  public void setCurrent(
    final double e)
  {
    this.slider.setValue(convertToSlider(e, this.minimum, this.maximum));
    this.current = e;
    this.refreshText();
    this.callListener();
  }

  public JTextField getField()
  {
    return this.field;
  }

  public JLabel getLabel()
  {
    return this.label;
  }

  public double getMaximum()
  {
    return this.maximum;
  }

  public double getMinimum()
  {
    return this.minimum;
  }

  private void refreshText()
  {
    final String ctext = String.format("%.6f", Double.valueOf(this.current));
    this.field.setText(ctext);
  }

  private void callListener()
  {
    final Consumer<Double> proc = this.on_change;
    if (proc != null) {
      final double x = this.current;
      proc.accept(Double.valueOf(x));
    }
  }
}
