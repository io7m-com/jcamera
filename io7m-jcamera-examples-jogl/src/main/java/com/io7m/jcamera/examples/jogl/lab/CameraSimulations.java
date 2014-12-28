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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import javax.swing.JComboBox;

import com.io7m.jcamera.examples.jogl.ExampleFPSStyleSimulation;
import com.io7m.jcamera.examples.jogl.ExampleFPSStyleSimulationType;
import com.io7m.jcamera.examples.jogl.ExampleRendererType;
import com.io7m.jnull.NullCheck;
import com.io7m.junreachable.UnreachableCodeException;
import com.jogamp.newt.opengl.GLWindow;

final class CameraSimulations
{
  static JComboBox<String> newSelector(
    final Map<String, CameraSimulationType> s)
  {
    final JComboBox<String> c = new JComboBox<String>();
    for (final String k : s.keySet()) {
      c.addItem(k);
    }
    return c;
  }

  static Map<String, CameraSimulationType> newSimulations(
    final ExecutorService in_background_workers,
    final ExampleRendererType in_renderer,
    final GLWindow in_window)
  {
    final Map<String, CameraSimulationType> m =
      new HashMap<String, CameraSimulationType>();

    {
      final CameraSimulationSpherical sim_spherical =
        new CameraSimulationSpherical(
          in_window,
          in_renderer,
          in_background_workers);
      m.put("Spherical", sim_spherical);
    }

    {
      final ExampleFPSStyleSimulationType in_sim_fps =
        new ExampleFPSStyleSimulation(in_renderer);
      final CameraSimulationFPSStyle sim_fps =
        new CameraSimulationFPSStyle(
          in_sim_fps,
          in_background_workers,
          in_renderer,
          in_window);
      m.put("FPS Style", sim_fps);
    }

    return NullCheck.notNull(Collections.unmodifiableMap(m));
  }

  private CameraSimulations()
  {
    throw new UnreachableCodeException();
  }
}
