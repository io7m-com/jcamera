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

package com.io7m.jcamera.examples.jogl.lab;

import com.io7m.jcamera.JCameraScreenOrigin;
import com.io7m.jcamera.JCameraSpherical;
import com.io7m.jcamera.JCameraSphericalAngularIntegrator;
import com.io7m.jcamera.JCameraSphericalInput;
import com.io7m.jcamera.JCameraSphericalInputType;
import com.io7m.jcamera.JCameraSphericalIntegrator;
import com.io7m.jcamera.JCameraSphericalIntegratorType;
import com.io7m.jcamera.JCameraSphericalLinearIntegratorZoomScaled;
import com.io7m.jcamera.JCameraSphericalMouseRegion;
import com.io7m.jcamera.JCameraSphericalSnapshot;
import com.io7m.jcamera.JCameraSphericalType;
import com.io7m.jcamera.examples.jogl.ExampleRendererType;
import com.io7m.jcamera.examples.jogl.ExampleSphericalGLListener;
import com.io7m.jcamera.examples.jogl.ExampleSphericalKeyListener;
import com.io7m.jcamera.examples.jogl.ExampleSphericalMouseListener;
import com.io7m.jcamera.examples.jogl.ExampleSphericalSimulationType;
import com.io7m.jfunctional.ProcedureType;
import com.io7m.jnull.Nullable;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLEventListener;
import net.java.dev.designgridlayout.DesignGridLayout;
import net.java.dev.designgridlayout.RowGroup;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

// CHECKSTYLE:OFF

final class CameraSimulationSpherical implements
  CameraSimulationType
{
  private enum IntegratorSelection
  {
    INTEGRATOR_SCALED("Zoom-scaled"),
    INTEGRATOR_UNSCALED("Unscaled");

    private final String name;

    IntegratorSelection(
      final String in_name)
    {
      this.name = in_name;
    }

    @Override public String toString()
    {
      return this.name;
    }
  }

  private final JCameraSphericalType                         camera;
  private final AtomicBoolean                                camera_enabled;
  private final CameraVector3Field                           camera_pos;
  private final JCameraSphericalSnapshot                     fixed_snapshot;
  private final ExampleSphericalGLListener                   gl_listener;
  private final RowGroup                                     group;
  private final CameraFloatField                             heading;
  private final CameraFloatSlider                            heading_acceleration;
  private final CameraFloatSlider                            heading_drag;
  private final CameraFloatSlider                            heading_maximum;
  private final CameraFloatField                             incline;
  private final CameraFloatSlider                            incline_acceleration;
  private final CameraFloatSlider                            incline_drag;
  private final CameraFloatSlider                            incline_maximum;
  private final JCameraSphericalInputType                    input;
  private       JCameraSphericalIntegratorType               integrator;
  private final JCameraSphericalIntegratorType               integrator_scaled;
  private final float                                        integrator_time_seconds;
  private final JCameraSphericalIntegratorType               integrator_unscaled;
  private final ExampleSphericalKeyListener                  key_listener;
  private final ExampleSphericalMouseListener                mouse_listener;
  private final AtomicReference<JCameraSphericalMouseRegion> mouse_region;
  private final JComboBox<IntegratorSelection>               selected_integrator;
  private final ExampleSphericalSimulationType               sim;
  private final CameraFloatSlider                            target_acceleration;
  private final CameraFloatSlider  target_drag;
  private final CameraFloatSlider  target_maximum;
  private final CameraVector3Field target_pos;
  private final CameraFloatField   zoom;
  private final CameraFloatSlider  zoom_acceleration;
  private final CameraFloatSlider  zoom_drag;
  private final CameraFloatSlider  zoom_maximum;
  private final CameraFloatSlider  drag_sensitivity;

  CameraSimulationSpherical(
    final GLWindow in_window,
    final ExampleRendererType in_renderer,
    final ExecutorService in_background_workers)
  {
    this.input = JCameraSphericalInput.newInput();
    this.camera = JCameraSpherical.newCamera();
    final JCameraSphericalType camera_fixed = JCameraSpherical.newCamera();
    this.fixed_snapshot = camera_fixed.cameraMakeSnapshot();
    this.camera_enabled = new AtomicBoolean(false);

    this.integrator_scaled =
      JCameraSphericalIntegrator.newIntegratorWith(
        JCameraSphericalAngularIntegrator.newIntegrator(
          this.camera,
          this.input),
        JCameraSphericalLinearIntegratorZoomScaled.newIntegrator(
          this.camera,
          this.input));

    this.camera.cameraClampInclineDisable();

    this.integrator_unscaled =
      JCameraSphericalIntegrator.newIntegrator(this.camera, this.input);

    this.integrator = this.integrator_scaled;

    final float rate = 60.0f;
    this.integrator_time_seconds = 1.0f / rate;

    final ExampleSphericalSimulationType in_sim =
      new ExampleSphericalSimulationType() {
        @Override public boolean cameraIsEnabled()
        {
          return CameraSimulationSpherical.this.camera_enabled.get();
        }

        @Override public void cameraSetEnabled(
          final boolean b)
        {
          CameraSimulationSpherical.this.camera_enabled.set(b);
        }

        @Override public JCameraSphericalType getCamera()
        {
          return CameraSimulationSpherical.this.camera;
        }

        @Override public float getDeltaTime()
        {
          return CameraSimulationSpherical.this.integrator_time_seconds;
        }

        @Override public JCameraSphericalInputType getInput()
        {
          return CameraSimulationSpherical.this.input;
        }

        @Override public JCameraSphericalSnapshot integrate()
        {
          if (this.cameraIsEnabled()) {
            CameraSimulationSpherical.this.integrator
              .integrate(CameraSimulationSpherical.this.integrator_time_seconds);
            final JCameraSphericalSnapshot snap =
              CameraSimulationSpherical.this.camera.cameraMakeSnapshot();
            return snap;
          }

          return CameraSimulationSpherical.this.fixed_snapshot;
        }
      };
    this.sim = in_sim;

    this.mouse_region =
      new AtomicReference<>(
        JCameraSphericalMouseRegion.newRegion(
          JCameraScreenOrigin.SCREEN_ORIGIN_BOTTOM_LEFT,
          (float) in_window.getWidth(),
          (float) in_window.getHeight()));

    this.key_listener =
      new ExampleSphericalKeyListener(
        in_sim,
        in_window,
        in_renderer,
        in_background_workers);

    final JCameraSphericalSnapshot in_snap = in_sim.integrate();

    this.gl_listener =
      new ExampleSphericalGLListener(
        in_renderer,
        in_snap,
        in_sim,
        this.mouse_region,
        in_window);

    this.mouse_listener =
      new ExampleSphericalMouseListener(
        in_window,
        this.mouse_region,
        in_sim.getInput(),
        in_renderer);

    this.camera_pos = new CameraVector3Field("Camera");
    this.target_pos = new CameraVector3Field("Target (w,a,s,d,z,x)");
    this.incline = new CameraFloatField();
    this.heading = new CameraFloatField();
    this.zoom = new CameraFloatField();

    this.drag_sensitivity =
      new CameraFloatSlider("Mouse drag sensitivity", 0.5f, 3.0f);
    this.drag_sensitivity.setOnChangeListener(new ProcedureType<Float>() {
      @Override public void call(
        final Float x)
      {
        CameraSimulationSpherical.this.input.setContinuousForwardFactor(x.floatValue());
        CameraSimulationSpherical.this.input.setContinuousRightwardFactor(x.floatValue());
      }
    });
    this.drag_sensitivity.setCurrent(1.0f);

    this.heading_drag =
      new CameraFloatSlider("Heading drag", 0.000001f, 1.0f);
    this.heading_drag.setOnChangeListener(new ProcedureType<Float>() {
      @Override public void call(
        final Float x)
      {
        CameraSimulationSpherical.this.integrator
          .integratorAngularOrbitHeadingSetDrag(x.floatValue());
      }
    });
    this.heading_drag.setCurrent(this.heading_drag.getMinimum());

    this.heading_acceleration =
      new CameraFloatSlider("Heading acceleration", 0.01f, 3.0f);
    this.heading_acceleration.setOnChangeListener(new ProcedureType<Float>() {
      @Override public void call(
        final Float x)
      {
        CameraSimulationSpherical.this.integrator
          .integratorAngularOrbitHeadingSetAcceleration(x.floatValue()
            / CameraSimulationSpherical.this.integrator_time_seconds);
      }
    });
    this.heading_acceleration.setCurrent(1.0f);

    this.heading_maximum =
      new CameraFloatSlider("Heading maximum speed", 0.001f, 0.2f);
    this.heading_maximum.setOnChangeListener(new ProcedureType<Float>() {
      @Override public void call(
        final Float x)
      {
        CameraSimulationSpherical.this.integrator
          .integratorAngularOrbitHeadingSetMaximumSpeed(x.floatValue()
            / CameraSimulationSpherical.this.integrator_time_seconds);
      }
    });
    this.heading_maximum.setCurrent(this.heading_maximum.getMaximum());

    this.incline_drag =
      new CameraFloatSlider("Incline drag", 0.000001f, 1.0f);
    this.incline_drag.setOnChangeListener(new ProcedureType<Float>() {
      @Override public void call(
        final Float x)
      {
        CameraSimulationSpherical.this.integrator
          .integratorAngularOrbitInclineSetDrag(x.floatValue());
      }
    });
    this.incline_drag.setCurrent(this.incline_drag.getMinimum());

    this.incline_acceleration =
      new CameraFloatSlider("Incline acceleration", 0.01f, 1.0f);
    this.incline_acceleration.setOnChangeListener(new ProcedureType<Float>() {
      @Override public void call(
        final Float x)
      {
        CameraSimulationSpherical.this.integrator
          .integratorAngularOrbitInclineSetAcceleration(x.floatValue()
            / CameraSimulationSpherical.this.integrator_time_seconds);
      }
    });
    this.incline_acceleration.setCurrent(this.incline_acceleration
      .getMaximum());

    this.incline_maximum =
      new CameraFloatSlider("Incline maximum speed", 0.001f, 0.2f);
    this.incline_maximum.setOnChangeListener(new ProcedureType<Float>() {
      @Override public void call(
        final Float x)
      {
        CameraSimulationSpherical.this.integrator
          .integratorAngularOrbitInclineSetMaximumSpeed(x.floatValue()
            / CameraSimulationSpherical.this.integrator_time_seconds);
      }
    });
    this.incline_maximum.setCurrent(this.incline_maximum.getMaximum());

    this.zoom_drag = new CameraFloatSlider("Zoom drag", 0.000001f, 1.0f);
    this.zoom_drag.setOnChangeListener(new ProcedureType<Float>() {
      @Override public void call(
        final Float x)
      {
        CameraSimulationSpherical.this.integrator
          .integratorLinearZoomSetDrag(x.floatValue());
      }
    });
    this.zoom_drag.setCurrent(this.zoom_drag.getMinimum());

    this.zoom_acceleration =
      new CameraFloatSlider("Zoom acceleration", 0.01f, 1.0f);
    this.zoom_acceleration.setOnChangeListener(new ProcedureType<Float>() {
      @Override public void call(
        final Float x)
      {
        CameraSimulationSpherical.this.integrator
          .integratorLinearZoomSetAcceleration(x.floatValue()
            / CameraSimulationSpherical.this.integrator_time_seconds);
      }
    });
    this.zoom_acceleration.setCurrent(this.zoom_acceleration.getMaximum());

    this.zoom_maximum =
      new CameraFloatSlider("Zoom maximum speed", 0.001f, 0.2f);
    this.zoom_maximum.setOnChangeListener(new ProcedureType<Float>() {
      @Override public void call(
        final Float x)
      {
        CameraSimulationSpherical.this.integrator
          .integratorLinearZoomSetMaximumSpeed(x.floatValue()
            / CameraSimulationSpherical.this.integrator_time_seconds);
      }
    });
    this.zoom_maximum.setCurrent(this.zoom_maximum.getMaximum());

    this.target_drag = new CameraFloatSlider("Target drag", 0.000001f, 1.0f);
    this.target_drag.setOnChangeListener(new ProcedureType<Float>() {
      @Override public void call(
        final Float x)
      {
        CameraSimulationSpherical.this.integrator
          .integratorLinearTargetSetDrag(x.floatValue());
      }
    });
    this.target_drag.setCurrent(this.target_drag.getMinimum());

    this.target_acceleration =
      new CameraFloatSlider("Target acceleration", 0.01f, 1.0f);
    this.target_acceleration.setOnChangeListener(new ProcedureType<Float>() {
      @Override public void call(
        final Float x)
      {
        CameraSimulationSpherical.this.integrator
          .integratorLinearTargetSetAcceleration(x.floatValue()
            / CameraSimulationSpherical.this.integrator_time_seconds);
      }
    });
    this.target_acceleration
      .setCurrent(this.target_acceleration.getMaximum());

    this.target_maximum =
      new CameraFloatSlider("Target maximum speed", 0.001f, 0.2f);
    this.target_maximum.setOnChangeListener(new ProcedureType<Float>() {
      @Override public void call(
        final Float x)
      {
        CameraSimulationSpherical.this.integrator
          .integratorLinearTargetSetMaximumSpeed(x.floatValue()
            / CameraSimulationSpherical.this.integrator_time_seconds);
      }
    });
    this.target_maximum.setCurrent(this.target_maximum.getMaximum());

    this.group = new RowGroup();

    this.selected_integrator = new JComboBox<>();
    for (final IntegratorSelection v : IntegratorSelection.values()) {
      this.selected_integrator.addItem(v);
    }
    this.selected_integrator.addActionListener(new ActionListener() {
      @Override public void actionPerformed(
        final @Nullable ActionEvent e)
      {
        final IntegratorSelection s =
          (IntegratorSelection) CameraSimulationSpherical.this.selected_integrator
            .getSelectedItem();
        switch (s) {
          case INTEGRATOR_SCALED:
          {
            CameraSimulationSpherical.this.integrator =
              CameraSimulationSpherical.this.integrator_scaled;
            break;
          }
          case INTEGRATOR_UNSCALED:
          {
            CameraSimulationSpherical.this.integrator =
              CameraSimulationSpherical.this.integrator_unscaled;
            break;
          }
        }

        CameraSimulationSpherical.this.updateIntegrator();
      }
    });
  }

  @Override public <A, E extends Exception> A acceptSimulationType(
    final CameraSimulationVisitorType<A, E> v)
    throws E
  {
    return v.spherical(this);
  }

  @Override public boolean cameraIsEnabled()
  {
    return this.sim.cameraIsEnabled();
  }

  @Override public void cameraSetEnabled(
    final boolean b)
  {
    this.key_listener.toggleCameraEnabled();
  }

  @Override public void controlsAddToLayout(
    final DesignGridLayout dg)
  {
    dg
      .row()
      .group(this.group)
      .grid(new JLabel("Integrator"))
      .add(this.selected_integrator);

    this.camera_pos.controlsAddToLayout(dg);
    this.target_pos.controlsAddToLayout(dg);

    this.drag_sensitivity.controlsAddToLayout(dg);

    dg
      .row()
      .group(this.group)
      .grid(new JLabel("Incline (f,v)"))
      .add(this.incline);
    dg
      .row()
      .group(this.group)
      .grid(new JLabel("Heading (q,e)"))
      .add(this.heading);
    dg.row().group(this.group).grid(new JLabel("Zoom (g,b)")).add(this.zoom);

    this.incline_drag.controlsAddToLayout(dg);
    this.incline_acceleration.controlsAddToLayout(dg);
    this.incline_maximum.controlsAddToLayout(dg);
    this.heading_drag.controlsAddToLayout(dg);
    this.heading_acceleration.controlsAddToLayout(dg);
    this.heading_maximum.controlsAddToLayout(dg);
    this.zoom_drag.controlsAddToLayout(dg);
    this.zoom_acceleration.controlsAddToLayout(dg);
    this.zoom_maximum.controlsAddToLayout(dg);
    this.target_drag.controlsAddToLayout(dg);
    this.target_acceleration.controlsAddToLayout(dg);
    this.target_maximum.controlsAddToLayout(dg);
  }

  @Override public void controlsHide()
  {
    this.camera_pos.controlsHide();
    this.target_pos.controlsHide();
    this.drag_sensitivity.controlsHide();
    this.incline_drag.controlsHide();
    this.incline_acceleration.controlsHide();
    this.incline_maximum.controlsHide();
    this.heading_drag.controlsHide();
    this.heading_acceleration.controlsHide();
    this.heading_maximum.controlsHide();
    this.zoom_drag.controlsHide();
    this.zoom_acceleration.controlsHide();
    this.zoom_maximum.controlsHide();
    this.target_drag.controlsHide();
    this.target_acceleration.controlsHide();
    this.target_maximum.controlsHide();
    this.group.hide();
  }

  @Override public void controlsShow()
  {
    this.camera_pos.controlsShow();
    this.target_pos.controlsShow();
    this.drag_sensitivity.controlsShow();
    this.incline_drag.controlsShow();
    this.incline_acceleration.controlsShow();
    this.incline_maximum.controlsShow();
    this.heading_drag.controlsShow();
    this.heading_acceleration.controlsShow();
    this.heading_maximum.controlsShow();
    this.zoom_drag.controlsShow();
    this.zoom_acceleration.controlsShow();
    this.zoom_maximum.controlsShow();
    this.target_drag.controlsShow();
    this.target_acceleration.controlsShow();
    this.target_maximum.controlsShow();
    this.group.forceShow();
  }

  @Override public GLEventListener getGLEventListener()
  {
    return this.gl_listener;
  }

  @Override public KeyListener getKeyListener()
  {
    return this.key_listener;
  }

  @Override public MouseListener getMouseListener()
  {
    return this.mouse_listener;
  }

  void updateIntegrator()
  {
    this.heading_drag.setCurrent(this.heading_drag.getCurrent());
    this.heading_maximum.setCurrent(this.heading_maximum.getCurrent());
    this.heading_acceleration.setCurrent(this.heading_acceleration
      .getCurrent());

    this.incline_drag.setCurrent(this.incline_drag.getCurrent());
    this.incline_maximum.setCurrent(this.incline_maximum.getCurrent());
    this.incline_acceleration.setCurrent(this.incline_acceleration
      .getCurrent());

    this.zoom_drag.setCurrent(this.zoom_drag.getCurrent());
    this.zoom_maximum.setCurrent(this.zoom_maximum.getCurrent());
    this.zoom_acceleration.setCurrent(this.zoom_acceleration.getCurrent());

    this.target_drag.setCurrent(this.target_drag.getCurrent());
    this.target_maximum.setCurrent(this.target_maximum.getCurrent());
    this.target_acceleration
      .setCurrent(this.target_acceleration.getCurrent());
  }

  @Override public void updatePeriodic()
  {
    final JCameraSphericalType c = this.sim.getCamera();

    this.camera_pos.setValue(c.cameraGetPosition());
    this.target_pos.setValue(c.cameraGetTargetPosition());
    this.heading.setValue(c.cameraGetAngleHeading());
    this.incline.setValue(c.cameraGetAngleIncline());
    this.zoom.setValue(c.cameraGetZoom());
  }
}
