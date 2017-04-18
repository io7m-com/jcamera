package com.io7m.jcamera;

import java.util.Objects;
import javax.annotation.Generated;

/**
 * A modifiable implementation of the {@link JCameraRotationCoefficientsType JCameraRotationCoefficientsType} type.
 * <p>Use the {@link #create()} static factory methods to create new instances.
 * Use the {@link #toImmutable()} method to convert to canonical immutable instances.
 * <p><em>JCameraRotationCoefficientsMutable is not thread-safe</em>
 * @see JCameraRotationCoefficients
 */
@SuppressWarnings({"all"})
@Generated({"Modifiables.generator", "JCameraRotationCoefficientsType"})
public final class JCameraRotationCoefficientsMutable
    implements JCameraRotationCoefficientsType {
  private static final long OPT_BIT_HORIZONTAL = 0x1L;
  private static final long OPT_BIT_VERTICAL = 0x2L;
  private long optBits;

  private double horizontal;
  private double vertical;

  private JCameraRotationCoefficientsMutable() {}

  /**
   * Construct a modifiable instance of {@code JCameraRotationCoefficientsType}.
   * @param horizontal The value for the {@link JCameraRotationCoefficientsType#horizontal() horizontal} attribute 
   * @param vertical The value for the {@link JCameraRotationCoefficientsType#vertical() vertical} attribute 
   * @return A new modifiable instance
   */
  public static JCameraRotationCoefficientsMutable create(double horizontal, double vertical) {
    return new JCameraRotationCoefficientsMutable()
        .setHorizontal(horizontal)
        .setVertical(vertical);
  }

  /**
   * Construct a modifiable instance of {@code JCameraRotationCoefficientsType}.
   * @return A new modifiable instance
   */
  public static JCameraRotationCoefficientsMutable create() {
    return new JCameraRotationCoefficientsMutable();
  }

  /**
   * @return The horizontal coefficient
   */
  @Override
  public final double horizontal() {
    return horizontalIsSet()
        ? horizontal
        : JCameraRotationCoefficientsType.super.horizontal();
  }

  /**
   * @return The vertical coefficient
   */
  @Override
  public final double vertical() {
    return verticalIsSet()
        ? vertical
        : JCameraRotationCoefficientsType.super.vertical();
  }

  /**
   * Clears the object by setting all attributes to their initial values.
   * @return {@code this} for use in a chained invocation
   */
  public JCameraRotationCoefficientsMutable clear() {
    optBits = 0;
    horizontal = 0;
    vertical = 0;
    return this;
  }

  /**
   * Fill this modifiable instance with attribute values from the provided {@link JCameraRotationCoefficientsType} instance.
   * Regular attribute values will be overridden, i.e. replaced with ones of an instance.
   * Any of the instance's absent optional values will not be copied (will not override current values).
   * @param instance The instance from which to copy values
   * @return {@code this} for use in a chained invocation
   */
  public JCameraRotationCoefficientsMutable from(JCameraRotationCoefficientsType instance) {
    Objects.requireNonNull(instance, "instance");
    setHorizontal(instance.horizontal());
    setVertical(instance.vertical());
    return this;
  }

  /**
   * Assigns a value to the {@link JCameraRotationCoefficientsType#horizontal() horizontal} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link JCameraRotationCoefficientsType#horizontal() horizontal}.</em>
   * @param horizontal The value for horizontal
   * @return {@code this} for use in a chained invocation
   */
  public JCameraRotationCoefficientsMutable setHorizontal(double horizontal) {
    this.horizontal = horizontal;
    optBits |= OPT_BIT_HORIZONTAL;
    return this;
  }

  /**
   * Assigns a value to the {@link JCameraRotationCoefficientsType#vertical() vertical} attribute.
   * <p><em>If not set, this attribute will have a default value returned by the initializer of {@link JCameraRotationCoefficientsType#vertical() vertical}.</em>
   * @param vertical The value for vertical
   * @return {@code this} for use in a chained invocation
   */
  public JCameraRotationCoefficientsMutable setVertical(double vertical) {
    this.vertical = vertical;
    optBits |= OPT_BIT_VERTICAL;
    return this;
  }

  /**
   * Returns {@code true} if the default attribute {@link JCameraRotationCoefficientsType#horizontal() horizontal} is set.
   * @return {@code true} if set
   */
  public final boolean horizontalIsSet() {
    return (optBits & OPT_BIT_HORIZONTAL) != 0;
  }

  /**
   * Returns {@code true} if the default attribute {@link JCameraRotationCoefficientsType#vertical() vertical} is set.
   * @return {@code true} if set
   */
  public final boolean verticalIsSet() {
    return (optBits & OPT_BIT_VERTICAL) != 0;
  }

  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  public final JCameraRotationCoefficientsMutable unsetHorizontal() {
    optBits |= 0;
    horizontal = 0;
    return this;
  }
  /**
   * Reset an attribute to its initial value.
   * @return {@code this} for use in a chained invocation
   */
  public final JCameraRotationCoefficientsMutable unsetVertical() {
    optBits |= 0;
    vertical = 0;
    return this;
  }

  /**
   * Returns {@code true} if all required attributes are set, indicating that the object is initialized.
   * @return {@code true} if set
   */
  public final boolean isInitialized() {
    return true;
  }

  /**
   * Converts to {@link JCameraRotationCoefficients JCameraRotationCoefficients}.
   * @return An immutable instance of JCameraRotationCoefficients
   */
  public final JCameraRotationCoefficients toImmutable() {
    return JCameraRotationCoefficients.copyOf(this);
  }

  /**
   * This instance is equal to all instances of {@code JCameraRotationCoefficientsMutable} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    if (!(another instanceof JCameraRotationCoefficientsMutable)) return false;
    JCameraRotationCoefficientsMutable other = (JCameraRotationCoefficientsMutable) another;
    return equalTo(other);
  }

  private boolean equalTo(JCameraRotationCoefficientsMutable another) {
    double horizontal = horizontal();
    double vertical = vertical();
    return Double.doubleToLongBits(horizontal) == Double.doubleToLongBits(another.horizontal())
        && Double.doubleToLongBits(vertical) == Double.doubleToLongBits(another.vertical());
  }

  /**
   * Computes a hash code from attributes: {@code horizontal}, {@code vertical}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    double horizontal = horizontal();
    h += (h << 5) + java.lang.Double.hashCode(horizontal);
    double vertical = vertical();
    h += (h << 5) + java.lang.Double.hashCode(vertical);
    return h;
  }

  /**
   * Generates a string representation of this {@code JCameraRotationCoefficientsType}.
   * If uninitialized, some attribute values may appear as question marks.
   * @return A string representation
   */
  @Override
  public String toString() {
    return "JCameraRotationCoefficientsMutable{"
        + "horizontal=" + horizontal()
        + ", vertical=" + vertical()
        + "}";
  }
}
