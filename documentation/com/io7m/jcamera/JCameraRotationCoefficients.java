package com.io7m.jcamera;

import java.util.ArrayList;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * A pair of rotation coefficients.
 */
@SuppressWarnings({"all"})
@Generated({"Immutables.generator", "JCameraRotationCoefficientsType"})
public final class JCameraRotationCoefficients implements JCameraRotationCoefficientsType {
  private final double horizontal;
  private final double vertical;

  private JCameraRotationCoefficients(double horizontal, double vertical) {
    this.horizontal = horizontal;
    this.vertical = vertical;
    initShim.setHorizontal(this.horizontal);
    initShim.setVertical(this.vertical);
    this.initShim = null;
  }

  private JCameraRotationCoefficients(JCameraRotationCoefficients.Builder builder) {
    if (builder.horizontalIsSet()) {
      initShim.setHorizontal(builder.horizontal);
    }
    if (builder.verticalIsSet()) {
      initShim.setVertical(builder.vertical);
    }
    this.horizontal = initShim.horizontal();
    this.vertical = initShim.vertical();
    this.initShim = null;
  }

  private JCameraRotationCoefficients(JCameraRotationCoefficients original, double horizontal, double vertical) {
    this.horizontal = horizontal;
    this.vertical = vertical;
    this.initShim = null;
  }

  private static final int STAGE_INITIALIZING = -1;
  private static final int STAGE_UNINITIALIZED = 0;
  private static final int STAGE_INITIALIZED = 1;
  private transient volatile InitShim initShim = new InitShim();

  private final class InitShim {
    private double horizontal;
    private int horizontalBuildStage;

    double horizontal() {
      if (horizontalBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (horizontalBuildStage == STAGE_UNINITIALIZED) {
        horizontalBuildStage = STAGE_INITIALIZING;
        this.horizontal = horizontalInitialize();
        horizontalBuildStage = STAGE_INITIALIZED;
      }
      return this.horizontal;
    }

    void setHorizontal(double horizontal) {
      this.horizontal = horizontal;
      horizontalBuildStage = STAGE_INITIALIZED;
    }
    private double vertical;
    private int verticalBuildStage;

    double vertical() {
      if (verticalBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (verticalBuildStage == STAGE_UNINITIALIZED) {
        verticalBuildStage = STAGE_INITIALIZING;
        this.vertical = verticalInitialize();
        verticalBuildStage = STAGE_INITIALIZED;
      }
      return this.vertical;
    }

    void setVertical(double vertical) {
      this.vertical = vertical;
      verticalBuildStage = STAGE_INITIALIZED;
    }

    private String formatInitCycleMessage() {
      ArrayList<String> attributes = new ArrayList<String>();
      if (horizontalBuildStage == STAGE_INITIALIZING) attributes.add("horizontal");
      if (verticalBuildStage == STAGE_INITIALIZING) attributes.add("vertical");
      return "Cannot build JCameraRotationCoefficients, attribute initializers form cycle" + attributes;
    }
  }

  private double horizontalInitialize() {
    return JCameraRotationCoefficientsType.super.horizontal();
  }

  private double verticalInitialize() {
    return JCameraRotationCoefficientsType.super.vertical();
  }

  /**
   * @return The horizontal coefficient
   */
  @Override
  public double horizontal() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.horizontal()
        : this.horizontal;
  }

  /**
   * @return The vertical coefficient
   */
  @Override
  public double vertical() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.vertical()
        : this.vertical;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraRotationCoefficientsType#horizontal() horizontal} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for horizontal
   * @return A modified copy of the {@code this} object
   */
  public final JCameraRotationCoefficients withHorizontal(double value) {
    if (Double.doubleToLongBits(this.horizontal) == Double.doubleToLongBits(value)) return this;
    return new JCameraRotationCoefficients(this, value, this.vertical);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraRotationCoefficientsType#vertical() vertical} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for vertical
   * @return A modified copy of the {@code this} object
   */
  public final JCameraRotationCoefficients withVertical(double value) {
    if (Double.doubleToLongBits(this.vertical) == Double.doubleToLongBits(value)) return this;
    return new JCameraRotationCoefficients(this, this.horizontal, value);
  }

  /**
   * This instance is equal to all instances of {@code JCameraRotationCoefficients} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof JCameraRotationCoefficients
        && equalTo((JCameraRotationCoefficients) another);
  }

  private boolean equalTo(JCameraRotationCoefficients another) {
    return Double.doubleToLongBits(horizontal) == Double.doubleToLongBits(another.horizontal)
        && Double.doubleToLongBits(vertical) == Double.doubleToLongBits(another.vertical);
  }

  /**
   * Computes a hash code from attributes: {@code horizontal}, {@code vertical}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Double.hashCode(horizontal);
    h += (h << 5) + Double.hashCode(vertical);
    return h;
  }

  /**
   * Prints the immutable value {@code JCameraRotationCoefficients} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "JCameraRotationCoefficients{"
        + "horizontal=" + horizontal
        + ", vertical=" + vertical
        + "}";
  }

  /**
   * Construct a new immutable {@code JCameraRotationCoefficients} instance.
   * @param horizontal The value for the {@code horizontal} attribute
   * @param vertical The value for the {@code vertical} attribute
   * @return An immutable JCameraRotationCoefficients instance
   */
  public static JCameraRotationCoefficients of(double horizontal, double vertical) {
    return new JCameraRotationCoefficients(horizontal, vertical);
  }

  /**
   * Creates an immutable copy of a {@link JCameraRotationCoefficientsType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable JCameraRotationCoefficients instance
   */
  public static JCameraRotationCoefficients copyOf(JCameraRotationCoefficientsType instance) {
    if (instance instanceof JCameraRotationCoefficients) {
      return (JCameraRotationCoefficients) instance;
    }
    return JCameraRotationCoefficients.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link JCameraRotationCoefficients JCameraRotationCoefficients}.
   * @return A new JCameraRotationCoefficients builder
   */
  public static JCameraRotationCoefficients.Builder builder() {
    return new JCameraRotationCoefficients.Builder();
  }

  /**
   * Builds instances of type {@link JCameraRotationCoefficients JCameraRotationCoefficients}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long OPT_BIT_HORIZONTAL = 0x1L;
    private static final long OPT_BIT_VERTICAL = 0x2L;
    private long optBits;

    private double horizontal;
    private double vertical;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code JCameraRotationCoefficientsType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(JCameraRotationCoefficientsType instance) {
      Objects.requireNonNull(instance, "instance");
      setHorizontal(instance.horizontal());
      setVertical(instance.vertical());
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraRotationCoefficientsType#horizontal() horizontal} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link JCameraRotationCoefficientsType#horizontal() horizontal}.</em>
     * @param horizontal The value for horizontal 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setHorizontal(double horizontal) {
      this.horizontal = horizontal;
      optBits |= OPT_BIT_HORIZONTAL;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraRotationCoefficientsType#vertical() vertical} attribute.
     * <p><em>If not set, this attribute will have a default value as returned by the initializer of {@link JCameraRotationCoefficientsType#vertical() vertical}.</em>
     * @param vertical The value for vertical 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setVertical(double vertical) {
      this.vertical = vertical;
      optBits |= OPT_BIT_VERTICAL;
      return this;
    }

    /**
     * Builds a new {@link JCameraRotationCoefficients JCameraRotationCoefficients}.
     * @return An immutable instance of JCameraRotationCoefficients
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public JCameraRotationCoefficients build() {
      return new JCameraRotationCoefficients(this);
    }

    private boolean horizontalIsSet() {
      return (optBits & OPT_BIT_HORIZONTAL) != 0;
    }

    private boolean verticalIsSet() {
      return (optBits & OPT_BIT_VERTICAL) != 0;
    }
  }
}
