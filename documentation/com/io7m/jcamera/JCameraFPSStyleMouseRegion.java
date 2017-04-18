package com.io7m.jcamera;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * An immutable rectangular region that maps mouse positions to movements.
 */
@SuppressWarnings({"all"})
@Generated({"Immutables.generator", "JCameraFPSStyleMouseRegionType"})
public final class JCameraFPSStyleMouseRegion implements JCameraFPSStyleMouseRegionType {
  private final JCameraScreenOrigin origin;
  private final double width;
  private final double height;
  private final double centerX;
  private final double centerY;

  private JCameraFPSStyleMouseRegion(JCameraScreenOrigin origin, double width, double height) {
    this.origin = Objects.requireNonNull(origin, "origin");
    this.width = width;
    this.height = height;
    this.centerX = initShim.centerX();
    this.centerY = initShim.centerY();
    this.initShim = null;
  }

  private JCameraFPSStyleMouseRegion(
      JCameraFPSStyleMouseRegion original,
      JCameraScreenOrigin origin,
      double width,
      double height) {
    this.origin = origin;
    this.width = width;
    this.height = height;
    this.centerX = initShim.centerX();
    this.centerY = initShim.centerY();
    this.initShim = null;
  }

  private static final int STAGE_INITIALIZING = -1;
  private static final int STAGE_UNINITIALIZED = 0;
  private static final int STAGE_INITIALIZED = 1;
  private transient volatile InitShim initShim = new InitShim();

  private final class InitShim {
    private double centerX;
    private int centerXBuildStage;

    double centerX() {
      if (centerXBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (centerXBuildStage == STAGE_UNINITIALIZED) {
        centerXBuildStage = STAGE_INITIALIZING;
        this.centerX = centerXInitialize();
        centerXBuildStage = STAGE_INITIALIZED;
      }
      return this.centerX;
    }
    private double centerY;
    private int centerYBuildStage;

    double centerY() {
      if (centerYBuildStage == STAGE_INITIALIZING) throw new IllegalStateException(formatInitCycleMessage());
      if (centerYBuildStage == STAGE_UNINITIALIZED) {
        centerYBuildStage = STAGE_INITIALIZING;
        this.centerY = centerYInitialize();
        centerYBuildStage = STAGE_INITIALIZED;
      }
      return this.centerY;
    }

    private String formatInitCycleMessage() {
      ArrayList<String> attributes = new ArrayList<String>();
      if (centerXBuildStage == STAGE_INITIALIZING) attributes.add("centerX");
      if (centerYBuildStage == STAGE_INITIALIZING) attributes.add("centerY");
      return "Cannot build JCameraFPSStyleMouseRegion, attribute initializers form cycle" + attributes;
    }
  }

  private double centerXInitialize() {
    return JCameraFPSStyleMouseRegionType.super.centerX();
  }

  private double centerYInitialize() {
    return JCameraFPSStyleMouseRegionType.super.centerY();
  }

  /**
   * @return The current screen origin
   */
  @Override
  public JCameraScreenOrigin origin() {
    return origin;
  }

  /**
   * @return The width of the region
   */
  @Override
  public double width() {
    return width;
  }

  /**
   * @return The height of the region
   */
  @Override
  public double height() {
    return height;
  }

  /**
   * @return The X coordinate of the center of the region
   */
  @Override
  public double centerX() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.centerX()
        : this.centerX;
  }

  /**
   * @return The Y coordinate of the center of the region
   */
  @Override
  public double centerY() {
    InitShim shim = this.initShim;
    return shim != null
        ? shim.centerY()
        : this.centerY;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraFPSStyleMouseRegionType#origin() origin} attribute.
   * A value equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for origin
   * @return A modified copy of the {@code this} object
   */
  public final JCameraFPSStyleMouseRegion withOrigin(JCameraScreenOrigin value) {
    if (this.origin == value) return this;
    JCameraScreenOrigin newValue = Objects.requireNonNull(value, "origin");
    return validate(new JCameraFPSStyleMouseRegion(this, newValue, this.width, this.height));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraFPSStyleMouseRegionType#width() width} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for width
   * @return A modified copy of the {@code this} object
   */
  public final JCameraFPSStyleMouseRegion withWidth(double value) {
    if (Double.doubleToLongBits(this.width) == Double.doubleToLongBits(value)) return this;
    return validate(new JCameraFPSStyleMouseRegion(this, this.origin, value, this.height));
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraFPSStyleMouseRegionType#height() height} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for height
   * @return A modified copy of the {@code this} object
   */
  public final JCameraFPSStyleMouseRegion withHeight(double value) {
    if (Double.doubleToLongBits(this.height) == Double.doubleToLongBits(value)) return this;
    return validate(new JCameraFPSStyleMouseRegion(this, this.origin, this.width, value));
  }

  /**
   * This instance is equal to all instances of {@code JCameraFPSStyleMouseRegion} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof JCameraFPSStyleMouseRegion
        && equalTo((JCameraFPSStyleMouseRegion) another);
  }

  private boolean equalTo(JCameraFPSStyleMouseRegion another) {
    return origin.equals(another.origin)
        && Double.doubleToLongBits(width) == Double.doubleToLongBits(another.width)
        && Double.doubleToLongBits(height) == Double.doubleToLongBits(another.height)
        && Double.doubleToLongBits(centerX) == Double.doubleToLongBits(another.centerX)
        && Double.doubleToLongBits(centerY) == Double.doubleToLongBits(another.centerY);
  }

  /**
   * Computes a hash code from attributes: {@code origin}, {@code width}, {@code height}, {@code centerX}, {@code centerY}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + origin.hashCode();
    h += (h << 5) + Double.hashCode(width);
    h += (h << 5) + Double.hashCode(height);
    h += (h << 5) + Double.hashCode(centerX);
    h += (h << 5) + Double.hashCode(centerY);
    return h;
  }

  /**
   * Prints the immutable value {@code JCameraFPSStyleMouseRegion} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "JCameraFPSStyleMouseRegion{"
        + "origin=" + origin
        + ", width=" + width
        + ", height=" + height
        + ", centerX=" + centerX
        + ", centerY=" + centerY
        + "}";
  }

  /**
   * Construct a new immutable {@code JCameraFPSStyleMouseRegion} instance.
   * @param origin The value for the {@code origin} attribute
   * @param width The value for the {@code width} attribute
   * @param height The value for the {@code height} attribute
   * @return An immutable JCameraFPSStyleMouseRegion instance
   */
  public static JCameraFPSStyleMouseRegion of(JCameraScreenOrigin origin, double width, double height) {
    return validate(new JCameraFPSStyleMouseRegion(origin, width, height));
  }


  private static JCameraFPSStyleMouseRegion validate(JCameraFPSStyleMouseRegion instance) {
    instance.checkPreconditions();
    return instance;
  }

  /**
   * Creates an immutable copy of a {@link JCameraFPSStyleMouseRegionType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable JCameraFPSStyleMouseRegion instance
   */
  public static JCameraFPSStyleMouseRegion copyOf(JCameraFPSStyleMouseRegionType instance) {
    if (instance instanceof JCameraFPSStyleMouseRegion) {
      return (JCameraFPSStyleMouseRegion) instance;
    }
    return JCameraFPSStyleMouseRegion.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link JCameraFPSStyleMouseRegion JCameraFPSStyleMouseRegion}.
   * @return A new JCameraFPSStyleMouseRegion builder
   */
  public static JCameraFPSStyleMouseRegion.Builder builder() {
    return new JCameraFPSStyleMouseRegion.Builder();
  }

  /**
   * Builds instances of type {@link JCameraFPSStyleMouseRegion JCameraFPSStyleMouseRegion}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_ORIGIN = 0x1L;
    private static final long INIT_BIT_WIDTH = 0x2L;
    private static final long INIT_BIT_HEIGHT = 0x4L;
    private long initBits = 0x7L;

    private JCameraScreenOrigin origin;
    private double width;
    private double height;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code JCameraFPSStyleMouseRegionType} instance.
     * Regular attribute values will be replaced with those from the given instance.
     * Absent optional values will not replace present values.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(JCameraFPSStyleMouseRegionType instance) {
      Objects.requireNonNull(instance, "instance");
      setOrigin(instance.origin());
      setWidth(instance.width());
      setHeight(instance.height());
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraFPSStyleMouseRegionType#origin() origin} attribute.
     * @param origin The value for origin 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setOrigin(JCameraScreenOrigin origin) {
      this.origin = Objects.requireNonNull(origin, "origin");
      initBits &= ~INIT_BIT_ORIGIN;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraFPSStyleMouseRegionType#width() width} attribute.
     * @param width The value for width 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setWidth(double width) {
      this.width = width;
      initBits &= ~INIT_BIT_WIDTH;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraFPSStyleMouseRegionType#height() height} attribute.
     * @param height The value for height 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setHeight(double height) {
      this.height = height;
      initBits &= ~INIT_BIT_HEIGHT;
      return this;
    }

    /**
     * Builds a new {@link JCameraFPSStyleMouseRegion JCameraFPSStyleMouseRegion}.
     * @return An immutable instance of JCameraFPSStyleMouseRegion
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public JCameraFPSStyleMouseRegion build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return JCameraFPSStyleMouseRegion.validate(new JCameraFPSStyleMouseRegion(null, origin, width, height));
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<String>();
      if ((initBits & INIT_BIT_ORIGIN) != 0) attributes.add("origin");
      if ((initBits & INIT_BIT_WIDTH) != 0) attributes.add("width");
      if ((initBits & INIT_BIT_HEIGHT) != 0) attributes.add("height");
      return "Cannot build JCameraFPSStyleMouseRegion, some of required attributes are not set " + attributes;
    }
  }
}
