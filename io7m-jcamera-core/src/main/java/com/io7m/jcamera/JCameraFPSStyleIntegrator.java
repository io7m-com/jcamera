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

import com.io7m.junreachable.UnreachableCodeException;

/**
 * Aggregation of the {@link JCameraFPSStyleAngularIntegratorType} and
 * {@link JCameraFPSStyleLinearIntegratorType} types.
 */

public final class JCameraFPSStyleIntegrator
{
  /**
   * Return a new integrator for the given camera and input using the default
   * integrator implementations.
   *
   * @param in_camera
   *          The camera
   * @param in_input
   *          The input
   * @return A new integrator
   */

  public static JCameraFPSStyleIntegratorType newIntegrator(
    final JCameraFPSStyleType in_camera,
    final JCameraInput in_input)
  {
    final JCameraFPSStyleAngularIntegratorType ai =
      JCameraFPSStyleAngularIntegrator.newIntegrator(in_camera, in_input);
    final JCameraFPSStyleLinearIntegratorType li =
      JCameraFPSStyleLinearIntegrator.newIntegrator(in_camera, in_input);

    return JCameraFPSStyleIntegrator.newIntegratorWith(ai, li);
  }

  /**
   * Return a new integrator using the given integrator implementations.
   *
   * @param ai
   *          The angular integrator
   * @param li
   *          The linera integrator
   *
   * @return A new integrator
   */

  public static JCameraFPSStyleIntegratorType newIntegratorWith(
    final JCameraFPSStyleAngularIntegratorType ai,
    final JCameraFPSStyleLinearIntegratorType li)
  {
    if (ai.integratorGetCamera() != li.integratorGetCamera()) {
      throw new IllegalArgumentException(
        "Angular integrator camera does not match the linear integrator camera");
    }
    if (ai.integratorGetInput() != li.integratorGetInput()) {
      throw new IllegalArgumentException(
        "Angular integrator input does not match the linear integrator input");
    }

    return new JCameraFPSStyleIntegratorType() {
      @Override public void integrate(
        final float d)
      {
        li.integrate(d);
        ai.integrate(d);
      }

      @Override public void integratorAngularSetAccelerationHorizontal(
        final float a)
      {
        ai.integratorAngularSetAccelerationHorizontal(a);
      }

      @Override public void integratorAngularSetAccelerationVertical(
        final float a)
      {
        ai.integratorAngularSetAccelerationVertical(a);
      }

      @Override public void integratorAngularSetDragHorizontal(
        final float d)
      {
        ai.integratorAngularSetDragHorizontal(d);
      }

      @Override public void integratorAngularSetDragVertical(
        final float d)
      {
        ai.integratorAngularSetDragVertical(d);
      }

      @Override public void integratorAngularSetMaximumSpeedHorizontal(
        final float s)
      {
        ai.integratorAngularSetMaximumSpeedHorizontal(s);
      }

      @Override public void integratorAngularSetMaximumSpeedVertical(
        final float s)
      {
        ai.integratorAngularSetMaximumSpeedVertical(s);
      }

      @Override public JCameraFPSStyleType integratorGetCamera()
      {
        return li.integratorGetCamera();
      }

      @Override public JCameraInput integratorGetInput()
      {
        return ai.integratorGetInput();
      }

      @Override public void integratorLinearSetAcceleration(
        final float a)
      {
        li.integratorLinearSetAcceleration(a);
      }

      @Override public void integratorLinearSetDrag(
        final float f)
      {
        li.integratorLinearSetDrag(f);
      }

      @Override public void integratorLinearSetMaximumSpeed(
        final float s)
      {
        li.integratorLinearSetMaximumSpeed(s);
      }
    };
  }

  private JCameraFPSStyleIntegrator()
  {
    throw new UnreachableCodeException();
  }
}
