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

package com.io7m.jcamera;

import com.io7m.jequality.annotations.EqualityReference;
import com.io7m.junreachable.UnreachableCodeException;

import java.util.Objects;

/**
 * Aggregation of the {@link JCameraFPSStyleAngularIntegratorType} and {@link
 * JCameraFPSStyleLinearIntegratorType} types.
 */

@EqualityReference
public final class JCameraFPSStyleIntegrator
{
  private JCameraFPSStyleIntegrator()
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

  public static JCameraFPSStyleIntegratorType newIntegrator(
    final JCameraFPSStyleType in_camera,
    final JCameraFPSStyleInputType in_input)
  {
    final JCameraFPSStyleAngularIntegratorType ai =
      JCameraFPSStyleAngularIntegrator.newIntegrator(in_camera, in_input);
    final JCameraFPSStyleLinearIntegratorType li =
      JCameraFPSStyleLinearIntegrator.newIntegrator(in_camera, in_input);

    return newIntegratorWith(ai, li);
  }

  /**
   * Return a new integrator using the given integrator implementations.
   *
   * @param ai The angular integrator
   * @param li The linera integrator
   *
   * @return A new integrator
   */

  public static JCameraFPSStyleIntegratorType newIntegratorWith(
    final JCameraFPSStyleAngularIntegratorType ai,
    final JCameraFPSStyleLinearIntegratorType li)
  {
    if (ai.integratorGetCamera() != li.integratorGetCamera()) {
      throw new IllegalArgumentException(
        "Angular integrator camera does not match linear integrator camera");
    }
    if (ai.integratorGetInput() != li.integratorGetInput()) {
      throw new IllegalArgumentException(
        "Angular integrator input does not match linear integrator input");
    }

    return new DelegatingIntegrator(li, ai);
  }

  private static final class DelegatingIntegrator
    implements JCameraFPSStyleIntegratorType
  {
    private final JCameraFPSStyleLinearIntegratorType linear;
    private final JCameraFPSStyleAngularIntegratorType angular;

    DelegatingIntegrator(
      final JCameraFPSStyleLinearIntegratorType inLinear,
      final JCameraFPSStyleAngularIntegratorType inAngular)
    {
      this.linear = Objects.requireNonNull(inLinear, "inLinear");
      this.angular = Objects.requireNonNull(inAngular, "inAngular");
    }

    @Override
    public void integrate(
      final double d)
    {
      this.linear.integrate(d);
      this.angular.integrate(d);
    }

    @Override
    public void integratorAngularSetAccelerationHorizontal(
      final double a)
    {
      this.angular.integratorAngularSetAccelerationHorizontal(a);
    }

    @Override
    public void integratorAngularSetAccelerationVertical(
      final double a)
    {
      this.angular.integratorAngularSetAccelerationVertical(a);
    }

    @Override
    public void integratorAngularSetDragHorizontal(
      final double d)
    {
      this.angular.integratorAngularSetDragHorizontal(d);
    }

    @Override
    public void integratorAngularSetDragVertical(
      final double d)
    {
      this.angular.integratorAngularSetDragVertical(d);
    }

    @Override
    public void integratorAngularSetMaximumSpeedHorizontal(
      final double s)
    {
      this.angular.integratorAngularSetMaximumSpeedHorizontal(s);
    }

    @Override
    public void integratorAngularSetMaximumSpeedVertical(
      final double s)
    {
      this.angular.integratorAngularSetMaximumSpeedVertical(s);
    }

    @Override
    public JCameraFPSStyleReadableType integratorGetCamera()
    {
      return this.linear.integratorGetCamera();
    }

    @Override
    public JCameraFPSStyleInputType integratorGetInput()
    {
      return this.angular.integratorGetInput();
    }

    @Override
    public void integratorLinearSetAcceleration(
      final double a)
    {
      this.linear.integratorLinearSetAcceleration(a);
    }

    @Override
    public void integratorLinearSetDrag(
      final double f)
    {
      this.linear.integratorLinearSetDrag(f);
    }

    @Override
    public void integratorLinearSetMaximumSpeed(
      final double s)
    {
      this.linear.integratorLinearSetMaximumSpeed(s);
    }
  }
}
