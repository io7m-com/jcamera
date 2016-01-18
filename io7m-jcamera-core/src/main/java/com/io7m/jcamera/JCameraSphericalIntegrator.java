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
import com.io7m.junreachable.UnreachableCodeException;

/**
 * Aggregation of the {@link JCameraSphericalAngularIntegratorType} and {@link
 * JCameraSphericalLinearIntegratorType} types.
 */

@EqualityReference
public final class JCameraSphericalIntegrator
{
  private JCameraSphericalIntegrator()
  {
    throw new UnreachableCodeException();
  }

  /**
   * Return a new integrator for the given camera and input using the default
   * integrator implementations.
   *
   * @param in_camera The camera
   * @param in_input  The input
   *
   * @return A new integrator
   */

  public static JCameraSphericalIntegratorType newIntegrator(
    final JCameraSphericalType in_camera,
    final JCameraSphericalInput in_input)
  {
    final JCameraSphericalAngularIntegratorType ai =
      JCameraSphericalAngularIntegrator.newIntegrator(in_camera, in_input);
    final JCameraSphericalLinearIntegratorType li =
      JCameraSphericalLinearIntegrator.newIntegrator(in_camera, in_input);

    return JCameraSphericalIntegrator.newIntegratorWith(ai, li);
  }

  /**
   * Return a new integrator using the given integrator implementations.
   *
   * @param ai The angular integrator
   * @param li The linera integrator
   *
   * @return A new integrator
   */

  public static JCameraSphericalIntegratorType newIntegratorWith(
    final JCameraSphericalAngularIntegratorType ai,
    final JCameraSphericalLinearIntegratorType li)
  {
    if (ai.integratorGetCamera() != li.integratorGetCamera()) {
      throw new IllegalArgumentException(
        "Angular integrator camera does not match the linear integrator camera");
    }
    if (ai.integratorGetInput() != li.integratorGetInput()) {
      throw new IllegalArgumentException(
        "Angular integrator input does not match the linear integrator input");
    }

    return new JCameraSphericalIntegratorType()
    {
      @Override
      public void integrate(
        final float d)
      {
        li.integrate(d);
        ai.integrate(d);
      }

      @Override
      public void integratorAngularOrbitHeadingSetAcceleration(
        final float a)
      {
        ai.integratorAngularOrbitHeadingSetAcceleration(a);
      }

      @Override
      public void integratorAngularOrbitHeadingSetDrag(
        final float d)
      {
        ai.integratorAngularOrbitHeadingSetDrag(d);
      }

      @Override
      public void integratorAngularOrbitHeadingSetMaximumSpeed(
        final float s)
      {
        ai.integratorAngularOrbitHeadingSetMaximumSpeed(s);
      }

      @Override
      public void integratorAngularOrbitInclineSetAcceleration(
        final float a)
      {
        ai.integratorAngularOrbitInclineSetAcceleration(a);
      }

      @Override
      public void integratorAngularOrbitInclineSetDrag(
        final float d)
      {
        ai.integratorAngularOrbitInclineSetDrag(d);
      }

      @Override
      public void integratorAngularOrbitInclineSetMaximumSpeed(
        final float s)
      {
        ai.integratorAngularOrbitInclineSetMaximumSpeed(s);
      }

      @Override
      public JCameraSphericalReadableType integratorGetCamera()
      {
        return li.integratorGetCamera();
      }

      @Override
      public JCameraSphericalInput integratorGetInput()
      {
        return ai.integratorGetInput();
      }

      @Override
      public void integratorLinearTargetSetAcceleration(
        final float a)
      {
        li.integratorLinearTargetSetAcceleration(a);
      }

      @Override
      public void integratorLinearTargetSetDrag(
        final float f)
      {
        li.integratorLinearTargetSetDrag(f);
      }

      @Override
      public void integratorLinearTargetSetMaximumSpeed(
        final float s)
      {
        li.integratorLinearTargetSetMaximumSpeed(s);
      }

      @Override
      public void integratorLinearZoomSetAcceleration(
        final float a)
      {
        li.integratorLinearZoomSetAcceleration(a);
      }

      @Override
      public void integratorLinearZoomSetDrag(
        final float f)
      {
        li.integratorLinearZoomSetDrag(f);
      }

      @Override
      public void integratorLinearZoomSetMaximumSpeed(
        final float s)
      {
        li.integratorLinearZoomSetMaximumSpeed(s);
      }
    };
  }
}
