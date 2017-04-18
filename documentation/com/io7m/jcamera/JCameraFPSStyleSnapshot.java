package com.io7m.jcamera;

import com.io7m.jtensors.core.unparameterized.vectors.Vector3D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Generated;

/**
 * An immutable snapshot of a camera.
 */
@SuppressWarnings({"all"})
@Generated({"Immutables.generator", "JCameraFPSStyleSnapshotType"})
public final class JCameraFPSStyleSnapshot implements JCameraFPSStyleSnapshotType {
  private final double cameraGetAngleAroundHorizontal;
  private final double cameraGetAngleAroundVertical;
  private final Vector3D cameraGetRight;
  private final Vector3D cameraGetUp;
  private final Vector3D cameraGetForward;
  private final Vector3D cameraGetPosition;

  private JCameraFPSStyleSnapshot(
      double cameraGetAngleAroundHorizontal,
      double cameraGetAngleAroundVertical,
      Vector3D cameraGetRight,
      Vector3D cameraGetUp,
      Vector3D cameraGetForward,
      Vector3D cameraGetPosition) {
    this.cameraGetAngleAroundHorizontal = cameraGetAngleAroundHorizontal;
    this.cameraGetAngleAroundVertical = cameraGetAngleAroundVertical;
    this.cameraGetRight = Objects.requireNonNull(cameraGetRight, "cameraGetRight");
    this.cameraGetUp = Objects.requireNonNull(cameraGetUp, "cameraGetUp");
    this.cameraGetForward = Objects.requireNonNull(cameraGetForward, "cameraGetForward");
    this.cameraGetPosition = Objects.requireNonNull(cameraGetPosition, "cameraGetPosition");
  }

  private JCameraFPSStyleSnapshot(
      JCameraFPSStyleSnapshot original,
      double cameraGetAngleAroundHorizontal,
      double cameraGetAngleAroundVertical,
      Vector3D cameraGetRight,
      Vector3D cameraGetUp,
      Vector3D cameraGetForward,
      Vector3D cameraGetPosition) {
    this.cameraGetAngleAroundHorizontal = cameraGetAngleAroundHorizontal;
    this.cameraGetAngleAroundVertical = cameraGetAngleAroundVertical;
    this.cameraGetRight = cameraGetRight;
    this.cameraGetUp = cameraGetUp;
    this.cameraGetForward = cameraGetForward;
    this.cameraGetPosition = cameraGetPosition;
  }

  /**
   * @return The value of the {@code cameraGetAngleAroundHorizontal} attribute
   */
  @Override
  public double cameraGetAngleAroundHorizontal() {
    return cameraGetAngleAroundHorizontal;
  }

  /**
   * @return The value of the {@code cameraGetAngleAroundVertical} attribute
   */
  @Override
  public double cameraGetAngleAroundVertical() {
    return cameraGetAngleAroundVertical;
  }

  /**
   * @return The value of the {@code cameraGetRight} attribute
   */
  @Override
  public Vector3D cameraGetRight() {
    return cameraGetRight;
  }

  /**
   * @return The value of the {@code cameraGetUp} attribute
   */
  @Override
  public Vector3D cameraGetUp() {
    return cameraGetUp;
  }

  /**
   * @return The value of the {@code cameraGetForward} attribute
   */
  @Override
  public Vector3D cameraGetForward() {
    return cameraGetForward;
  }

  /**
   * @return The value of the {@code cameraGetPosition} attribute
   */
  @Override
  public Vector3D cameraGetPosition() {
    return cameraGetPosition;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraFPSStyleSnapshotType#cameraGetAngleAroundHorizontal() cameraGetAngleAroundHorizontal} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetAngleAroundHorizontal
   * @return A modified copy of the {@code this} object
   */
  public final JCameraFPSStyleSnapshot withCameraGetAngleAroundHorizontal(double value) {
    if (Double.doubleToLongBits(this.cameraGetAngleAroundHorizontal) == Double.doubleToLongBits(value)) return this;
    return new JCameraFPSStyleSnapshot(
        this,
        value,
        this.cameraGetAngleAroundVertical,
        this.cameraGetRight,
        this.cameraGetUp,
        this.cameraGetForward,
        this.cameraGetPosition);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraFPSStyleSnapshotType#cameraGetAngleAroundVertical() cameraGetAngleAroundVertical} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetAngleAroundVertical
   * @return A modified copy of the {@code this} object
   */
  public final JCameraFPSStyleSnapshot withCameraGetAngleAroundVertical(double value) {
    if (Double.doubleToLongBits(this.cameraGetAngleAroundVertical) == Double.doubleToLongBits(value)) return this;
    return new JCameraFPSStyleSnapshot(
        this,
        this.cameraGetAngleAroundHorizontal,
        value,
        this.cameraGetRight,
        this.cameraGetUp,
        this.cameraGetForward,
        this.cameraGetPosition);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraFPSStyleSnapshotType#cameraGetRight() cameraGetRight} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetRight
   * @return A modified copy of the {@code this} object
   */
  public final JCameraFPSStyleSnapshot withCameraGetRight(Vector3D value) {
    if (this.cameraGetRight == value) return this;
    Vector3D newValue = Objects.requireNonNull(value, "cameraGetRight");
    return new JCameraFPSStyleSnapshot(
        this,
        this.cameraGetAngleAroundHorizontal,
        this.cameraGetAngleAroundVertical,
        newValue,
        this.cameraGetUp,
        this.cameraGetForward,
        this.cameraGetPosition);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraFPSStyleSnapshotType#cameraGetUp() cameraGetUp} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetUp
   * @return A modified copy of the {@code this} object
   */
  public final JCameraFPSStyleSnapshot withCameraGetUp(Vector3D value) {
    if (this.cameraGetUp == value) return this;
    Vector3D newValue = Objects.requireNonNull(value, "cameraGetUp");
    return new JCameraFPSStyleSnapshot(
        this,
        this.cameraGetAngleAroundHorizontal,
        this.cameraGetAngleAroundVertical,
        this.cameraGetRight,
        newValue,
        this.cameraGetForward,
        this.cameraGetPosition);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraFPSStyleSnapshotType#cameraGetForward() cameraGetForward} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetForward
   * @return A modified copy of the {@code this} object
   */
  public final JCameraFPSStyleSnapshot withCameraGetForward(Vector3D value) {
    if (this.cameraGetForward == value) return this;
    Vector3D newValue = Objects.requireNonNull(value, "cameraGetForward");
    return new JCameraFPSStyleSnapshot(
        this,
        this.cameraGetAngleAroundHorizontal,
        this.cameraGetAngleAroundVertical,
        this.cameraGetRight,
        this.cameraGetUp,
        newValue,
        this.cameraGetPosition);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraFPSStyleSnapshotType#cameraGetPosition() cameraGetPosition} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetPosition
   * @return A modified copy of the {@code this} object
   */
  public final JCameraFPSStyleSnapshot withCameraGetPosition(Vector3D value) {
    if (this.cameraGetPosition == value) return this;
    Vector3D newValue = Objects.requireNonNull(value, "cameraGetPosition");
    return new JCameraFPSStyleSnapshot(
        this,
        this.cameraGetAngleAroundHorizontal,
        this.cameraGetAngleAroundVertical,
        this.cameraGetRight,
        this.cameraGetUp,
        this.cameraGetForward,
        newValue);
  }

  /**
   * This instance is equal to all instances of {@code JCameraFPSStyleSnapshot} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof JCameraFPSStyleSnapshot
        && equalTo((JCameraFPSStyleSnapshot) another);
  }

  private boolean equalTo(JCameraFPSStyleSnapshot another) {
    return Double.doubleToLongBits(cameraGetAngleAroundHorizontal) == Double.doubleToLongBits(another.cameraGetAngleAroundHorizontal)
        && Double.doubleToLongBits(cameraGetAngleAroundVertical) == Double.doubleToLongBits(another.cameraGetAngleAroundVertical)
        && cameraGetRight.equals(another.cameraGetRight)
        && cameraGetUp.equals(another.cameraGetUp)
        && cameraGetForward.equals(another.cameraGetForward)
        && cameraGetPosition.equals(another.cameraGetPosition);
  }

  /**
   * Computes a hash code from attributes: {@code cameraGetAngleAroundHorizontal}, {@code cameraGetAngleAroundVertical}, {@code cameraGetRight}, {@code cameraGetUp}, {@code cameraGetForward}, {@code cameraGetPosition}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + Double.hashCode(cameraGetAngleAroundHorizontal);
    h += (h << 5) + Double.hashCode(cameraGetAngleAroundVertical);
    h += (h << 5) + cameraGetRight.hashCode();
    h += (h << 5) + cameraGetUp.hashCode();
    h += (h << 5) + cameraGetForward.hashCode();
    h += (h << 5) + cameraGetPosition.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code JCameraFPSStyleSnapshot} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "JCameraFPSStyleSnapshot{"
        + "cameraGetAngleAroundHorizontal=" + cameraGetAngleAroundHorizontal
        + ", cameraGetAngleAroundVertical=" + cameraGetAngleAroundVertical
        + ", cameraGetRight=" + cameraGetRight
        + ", cameraGetUp=" + cameraGetUp
        + ", cameraGetForward=" + cameraGetForward
        + ", cameraGetPosition=" + cameraGetPosition
        + "}";
  }

  /**
   * Construct a new immutable {@code JCameraFPSStyleSnapshot} instance.
   * @param cameraGetAngleAroundHorizontal The value for the {@code cameraGetAngleAroundHorizontal} attribute
   * @param cameraGetAngleAroundVertical The value for the {@code cameraGetAngleAroundVertical} attribute
   * @param cameraGetRight The value for the {@code cameraGetRight} attribute
   * @param cameraGetUp The value for the {@code cameraGetUp} attribute
   * @param cameraGetForward The value for the {@code cameraGetForward} attribute
   * @param cameraGetPosition The value for the {@code cameraGetPosition} attribute
   * @return An immutable JCameraFPSStyleSnapshot instance
   */
  public static JCameraFPSStyleSnapshot of(double cameraGetAngleAroundHorizontal, double cameraGetAngleAroundVertical, Vector3D cameraGetRight, Vector3D cameraGetUp, Vector3D cameraGetForward, Vector3D cameraGetPosition) {
    return new JCameraFPSStyleSnapshot(cameraGetAngleAroundHorizontal, cameraGetAngleAroundVertical, cameraGetRight, cameraGetUp, cameraGetForward, cameraGetPosition);
  }

  /**
   * Creates an immutable copy of a {@link JCameraFPSStyleSnapshotType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable JCameraFPSStyleSnapshot instance
   */
  public static JCameraFPSStyleSnapshot copyOf(JCameraFPSStyleSnapshotType instance) {
    if (instance instanceof JCameraFPSStyleSnapshot) {
      return (JCameraFPSStyleSnapshot) instance;
    }
    return JCameraFPSStyleSnapshot.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link JCameraFPSStyleSnapshot JCameraFPSStyleSnapshot}.
   * @return A new JCameraFPSStyleSnapshot builder
   */
  public static JCameraFPSStyleSnapshot.Builder builder() {
    return new JCameraFPSStyleSnapshot.Builder();
  }

  /**
   * Builds instances of type {@link JCameraFPSStyleSnapshot JCameraFPSStyleSnapshot}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_CAMERA_GET_ANGLE_AROUND_HORIZONTAL = 0x1L;
    private static final long INIT_BIT_CAMERA_GET_ANGLE_AROUND_VERTICAL = 0x2L;
    private static final long INIT_BIT_CAMERA_GET_RIGHT = 0x4L;
    private static final long INIT_BIT_CAMERA_GET_UP = 0x8L;
    private static final long INIT_BIT_CAMERA_GET_FORWARD = 0x10L;
    private static final long INIT_BIT_CAMERA_GET_POSITION = 0x20L;
    private long initBits = 0x3fL;

    private double cameraGetAngleAroundHorizontal;
    private double cameraGetAngleAroundVertical;
    private Vector3D cameraGetRight;
    private Vector3D cameraGetUp;
    private Vector3D cameraGetForward;
    private Vector3D cameraGetPosition;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code com.io7m.jcamera.JCameraFPSStyleSnapshotType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(JCameraFPSStyleSnapshotType instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code com.io7m.jcamera.JCameraFPSStyleReadableType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(JCameraFPSStyleReadableType instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      long bits = 0;
      if (object instanceof JCameraFPSStyleSnapshotType) {
        JCameraFPSStyleSnapshotType instance = (JCameraFPSStyleSnapshotType) object;
        if ((bits & 0x1L) == 0) {
          setCameraGetRight(instance.cameraGetRight());
          bits |= 0x1L;
        }
        if ((bits & 0x8L) == 0) {
          setCameraGetPosition(instance.cameraGetPosition());
          bits |= 0x8L;
        }
        if ((bits & 0x10L) == 0) {
          setCameraGetAngleAroundHorizontal(instance.cameraGetAngleAroundHorizontal());
          bits |= 0x10L;
        }
        if ((bits & 0x2L) == 0) {
          setCameraGetAngleAroundVertical(instance.cameraGetAngleAroundVertical());
          bits |= 0x2L;
        }
        if ((bits & 0x4L) == 0) {
          setCameraGetForward(instance.cameraGetForward());
          bits |= 0x4L;
        }
        if ((bits & 0x20L) == 0) {
          setCameraGetUp(instance.cameraGetUp());
          bits |= 0x20L;
        }
      }
      if (object instanceof JCameraFPSStyleReadableType) {
        JCameraFPSStyleReadableType instance = (JCameraFPSStyleReadableType) object;
        if ((bits & 0x1L) == 0) {
          setCameraGetRight(instance.cameraGetRight());
          bits |= 0x1L;
        }
        if ((bits & 0x8L) == 0) {
          setCameraGetPosition(instance.cameraGetPosition());
          bits |= 0x8L;
        }
        if ((bits & 0x10L) == 0) {
          setCameraGetAngleAroundHorizontal(instance.cameraGetAngleAroundHorizontal());
          bits |= 0x10L;
        }
        if ((bits & 0x2L) == 0) {
          setCameraGetAngleAroundVertical(instance.cameraGetAngleAroundVertical());
          bits |= 0x2L;
        }
        if ((bits & 0x4L) == 0) {
          setCameraGetForward(instance.cameraGetForward());
          bits |= 0x4L;
        }
        if ((bits & 0x20L) == 0) {
          setCameraGetUp(instance.cameraGetUp());
          bits |= 0x20L;
        }
      }
    }

    /**
     * Initializes the value for the {@link JCameraFPSStyleSnapshotType#cameraGetAngleAroundHorizontal() cameraGetAngleAroundHorizontal} attribute.
     * @param cameraGetAngleAroundHorizontal The value for cameraGetAngleAroundHorizontal 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetAngleAroundHorizontal(double cameraGetAngleAroundHorizontal) {
      this.cameraGetAngleAroundHorizontal = cameraGetAngleAroundHorizontal;
      initBits &= ~INIT_BIT_CAMERA_GET_ANGLE_AROUND_HORIZONTAL;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraFPSStyleSnapshotType#cameraGetAngleAroundVertical() cameraGetAngleAroundVertical} attribute.
     * @param cameraGetAngleAroundVertical The value for cameraGetAngleAroundVertical 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetAngleAroundVertical(double cameraGetAngleAroundVertical) {
      this.cameraGetAngleAroundVertical = cameraGetAngleAroundVertical;
      initBits &= ~INIT_BIT_CAMERA_GET_ANGLE_AROUND_VERTICAL;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraFPSStyleSnapshotType#cameraGetRight() cameraGetRight} attribute.
     * @param cameraGetRight The value for cameraGetRight 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetRight(Vector3D cameraGetRight) {
      this.cameraGetRight = Objects.requireNonNull(cameraGetRight, "cameraGetRight");
      initBits &= ~INIT_BIT_CAMERA_GET_RIGHT;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraFPSStyleSnapshotType#cameraGetUp() cameraGetUp} attribute.
     * @param cameraGetUp The value for cameraGetUp 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetUp(Vector3D cameraGetUp) {
      this.cameraGetUp = Objects.requireNonNull(cameraGetUp, "cameraGetUp");
      initBits &= ~INIT_BIT_CAMERA_GET_UP;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraFPSStyleSnapshotType#cameraGetForward() cameraGetForward} attribute.
     * @param cameraGetForward The value for cameraGetForward 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetForward(Vector3D cameraGetForward) {
      this.cameraGetForward = Objects.requireNonNull(cameraGetForward, "cameraGetForward");
      initBits &= ~INIT_BIT_CAMERA_GET_FORWARD;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraFPSStyleSnapshotType#cameraGetPosition() cameraGetPosition} attribute.
     * @param cameraGetPosition The value for cameraGetPosition 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetPosition(Vector3D cameraGetPosition) {
      this.cameraGetPosition = Objects.requireNonNull(cameraGetPosition, "cameraGetPosition");
      initBits &= ~INIT_BIT_CAMERA_GET_POSITION;
      return this;
    }

    /**
     * Builds a new {@link JCameraFPSStyleSnapshot JCameraFPSStyleSnapshot}.
     * @return An immutable instance of JCameraFPSStyleSnapshot
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public JCameraFPSStyleSnapshot build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new JCameraFPSStyleSnapshot(
          null,
          cameraGetAngleAroundHorizontal,
          cameraGetAngleAroundVertical,
          cameraGetRight,
          cameraGetUp,
          cameraGetForward,
          cameraGetPosition);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<String>();
      if ((initBits & INIT_BIT_CAMERA_GET_ANGLE_AROUND_HORIZONTAL) != 0) attributes.add("cameraGetAngleAroundHorizontal");
      if ((initBits & INIT_BIT_CAMERA_GET_ANGLE_AROUND_VERTICAL) != 0) attributes.add("cameraGetAngleAroundVertical");
      if ((initBits & INIT_BIT_CAMERA_GET_RIGHT) != 0) attributes.add("cameraGetRight");
      if ((initBits & INIT_BIT_CAMERA_GET_UP) != 0) attributes.add("cameraGetUp");
      if ((initBits & INIT_BIT_CAMERA_GET_FORWARD) != 0) attributes.add("cameraGetForward");
      if ((initBits & INIT_BIT_CAMERA_GET_POSITION) != 0) attributes.add("cameraGetPosition");
      return "Cannot build JCameraFPSStyleSnapshot, some of required attributes are not set " + attributes;
    }
  }
}
