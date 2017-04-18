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
@Generated({"Immutables.generator", "JCameraSphericalSnapshotType"})
public final class JCameraSphericalSnapshot implements JCameraSphericalSnapshotType {
  private final Vector3D cameraGetRight;
  private final Vector3D cameraGetUp;
  private final Vector3D cameraGetForward;
  private final Vector3D cameraGetPosition;
  private final double cameraGetAngleHeading;
  private final double cameraGetAngleIncline;
  private final double cameraGetZoom;
  private final Vector3D cameraGetForwardProjectedOnXZ;
  private final Vector3D cameraGetTargetPosition;

  private JCameraSphericalSnapshot(
      Vector3D cameraGetRight,
      Vector3D cameraGetUp,
      Vector3D cameraGetForward,
      Vector3D cameraGetPosition,
      double cameraGetAngleHeading,
      double cameraGetAngleIncline,
      double cameraGetZoom,
      Vector3D cameraGetForwardProjectedOnXZ,
      Vector3D cameraGetTargetPosition) {
    this.cameraGetRight = Objects.requireNonNull(cameraGetRight, "cameraGetRight");
    this.cameraGetUp = Objects.requireNonNull(cameraGetUp, "cameraGetUp");
    this.cameraGetForward = Objects.requireNonNull(cameraGetForward, "cameraGetForward");
    this.cameraGetPosition = Objects.requireNonNull(cameraGetPosition, "cameraGetPosition");
    this.cameraGetAngleHeading = cameraGetAngleHeading;
    this.cameraGetAngleIncline = cameraGetAngleIncline;
    this.cameraGetZoom = cameraGetZoom;
    this.cameraGetForwardProjectedOnXZ = Objects.requireNonNull(cameraGetForwardProjectedOnXZ, "cameraGetForwardProjectedOnXZ");
    this.cameraGetTargetPosition = Objects.requireNonNull(cameraGetTargetPosition, "cameraGetTargetPosition");
  }

  private JCameraSphericalSnapshot(
      JCameraSphericalSnapshot original,
      Vector3D cameraGetRight,
      Vector3D cameraGetUp,
      Vector3D cameraGetForward,
      Vector3D cameraGetPosition,
      double cameraGetAngleHeading,
      double cameraGetAngleIncline,
      double cameraGetZoom,
      Vector3D cameraGetForwardProjectedOnXZ,
      Vector3D cameraGetTargetPosition) {
    this.cameraGetRight = cameraGetRight;
    this.cameraGetUp = cameraGetUp;
    this.cameraGetForward = cameraGetForward;
    this.cameraGetPosition = cameraGetPosition;
    this.cameraGetAngleHeading = cameraGetAngleHeading;
    this.cameraGetAngleIncline = cameraGetAngleIncline;
    this.cameraGetZoom = cameraGetZoom;
    this.cameraGetForwardProjectedOnXZ = cameraGetForwardProjectedOnXZ;
    this.cameraGetTargetPosition = cameraGetTargetPosition;
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
   * @return The value of the {@code cameraGetAngleHeading} attribute
   */
  @Override
  public double cameraGetAngleHeading() {
    return cameraGetAngleHeading;
  }

  /**
   * @return The value of the {@code cameraGetAngleIncline} attribute
   */
  @Override
  public double cameraGetAngleIncline() {
    return cameraGetAngleIncline;
  }

  /**
   * @return The value of the {@code cameraGetZoom} attribute
   */
  @Override
  public double cameraGetZoom() {
    return cameraGetZoom;
  }

  /**
   * @return The value of the {@code cameraGetForwardProjectedOnXZ} attribute
   */
  @Override
  public Vector3D cameraGetForwardProjectedOnXZ() {
    return cameraGetForwardProjectedOnXZ;
  }

  /**
   * @return The value of the {@code cameraGetTargetPosition} attribute
   */
  @Override
  public Vector3D cameraGetTargetPosition() {
    return cameraGetTargetPosition;
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraSphericalSnapshotType#cameraGetRight() cameraGetRight} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetRight
   * @return A modified copy of the {@code this} object
   */
  public final JCameraSphericalSnapshot withCameraGetRight(Vector3D value) {
    if (this.cameraGetRight == value) return this;
    Vector3D newValue = Objects.requireNonNull(value, "cameraGetRight");
    return new JCameraSphericalSnapshot(
        this,
        newValue,
        this.cameraGetUp,
        this.cameraGetForward,
        this.cameraGetPosition,
        this.cameraGetAngleHeading,
        this.cameraGetAngleIncline,
        this.cameraGetZoom,
        this.cameraGetForwardProjectedOnXZ,
        this.cameraGetTargetPosition);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraSphericalSnapshotType#cameraGetUp() cameraGetUp} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetUp
   * @return A modified copy of the {@code this} object
   */
  public final JCameraSphericalSnapshot withCameraGetUp(Vector3D value) {
    if (this.cameraGetUp == value) return this;
    Vector3D newValue = Objects.requireNonNull(value, "cameraGetUp");
    return new JCameraSphericalSnapshot(
        this,
        this.cameraGetRight,
        newValue,
        this.cameraGetForward,
        this.cameraGetPosition,
        this.cameraGetAngleHeading,
        this.cameraGetAngleIncline,
        this.cameraGetZoom,
        this.cameraGetForwardProjectedOnXZ,
        this.cameraGetTargetPosition);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraSphericalSnapshotType#cameraGetForward() cameraGetForward} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetForward
   * @return A modified copy of the {@code this} object
   */
  public final JCameraSphericalSnapshot withCameraGetForward(Vector3D value) {
    if (this.cameraGetForward == value) return this;
    Vector3D newValue = Objects.requireNonNull(value, "cameraGetForward");
    return new JCameraSphericalSnapshot(
        this,
        this.cameraGetRight,
        this.cameraGetUp,
        newValue,
        this.cameraGetPosition,
        this.cameraGetAngleHeading,
        this.cameraGetAngleIncline,
        this.cameraGetZoom,
        this.cameraGetForwardProjectedOnXZ,
        this.cameraGetTargetPosition);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraSphericalSnapshotType#cameraGetPosition() cameraGetPosition} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetPosition
   * @return A modified copy of the {@code this} object
   */
  public final JCameraSphericalSnapshot withCameraGetPosition(Vector3D value) {
    if (this.cameraGetPosition == value) return this;
    Vector3D newValue = Objects.requireNonNull(value, "cameraGetPosition");
    return new JCameraSphericalSnapshot(
        this,
        this.cameraGetRight,
        this.cameraGetUp,
        this.cameraGetForward,
        newValue,
        this.cameraGetAngleHeading,
        this.cameraGetAngleIncline,
        this.cameraGetZoom,
        this.cameraGetForwardProjectedOnXZ,
        this.cameraGetTargetPosition);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraSphericalSnapshotType#cameraGetAngleHeading() cameraGetAngleHeading} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetAngleHeading
   * @return A modified copy of the {@code this} object
   */
  public final JCameraSphericalSnapshot withCameraGetAngleHeading(double value) {
    if (Double.doubleToLongBits(this.cameraGetAngleHeading) == Double.doubleToLongBits(value)) return this;
    return new JCameraSphericalSnapshot(
        this,
        this.cameraGetRight,
        this.cameraGetUp,
        this.cameraGetForward,
        this.cameraGetPosition,
        value,
        this.cameraGetAngleIncline,
        this.cameraGetZoom,
        this.cameraGetForwardProjectedOnXZ,
        this.cameraGetTargetPosition);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraSphericalSnapshotType#cameraGetAngleIncline() cameraGetAngleIncline} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetAngleIncline
   * @return A modified copy of the {@code this} object
   */
  public final JCameraSphericalSnapshot withCameraGetAngleIncline(double value) {
    if (Double.doubleToLongBits(this.cameraGetAngleIncline) == Double.doubleToLongBits(value)) return this;
    return new JCameraSphericalSnapshot(
        this,
        this.cameraGetRight,
        this.cameraGetUp,
        this.cameraGetForward,
        this.cameraGetPosition,
        this.cameraGetAngleHeading,
        value,
        this.cameraGetZoom,
        this.cameraGetForwardProjectedOnXZ,
        this.cameraGetTargetPosition);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraSphericalSnapshotType#cameraGetZoom() cameraGetZoom} attribute.
   * A value strict bits equality used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetZoom
   * @return A modified copy of the {@code this} object
   */
  public final JCameraSphericalSnapshot withCameraGetZoom(double value) {
    if (Double.doubleToLongBits(this.cameraGetZoom) == Double.doubleToLongBits(value)) return this;
    return new JCameraSphericalSnapshot(
        this,
        this.cameraGetRight,
        this.cameraGetUp,
        this.cameraGetForward,
        this.cameraGetPosition,
        this.cameraGetAngleHeading,
        this.cameraGetAngleIncline,
        value,
        this.cameraGetForwardProjectedOnXZ,
        this.cameraGetTargetPosition);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraSphericalSnapshotType#cameraGetForwardProjectedOnXZ() cameraGetForwardProjectedOnXZ} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetForwardProjectedOnXZ
   * @return A modified copy of the {@code this} object
   */
  public final JCameraSphericalSnapshot withCameraGetForwardProjectedOnXZ(Vector3D value) {
    if (this.cameraGetForwardProjectedOnXZ == value) return this;
    Vector3D newValue = Objects.requireNonNull(value, "cameraGetForwardProjectedOnXZ");
    return new JCameraSphericalSnapshot(
        this,
        this.cameraGetRight,
        this.cameraGetUp,
        this.cameraGetForward,
        this.cameraGetPosition,
        this.cameraGetAngleHeading,
        this.cameraGetAngleIncline,
        this.cameraGetZoom,
        newValue,
        this.cameraGetTargetPosition);
  }

  /**
   * Copy the current immutable object by setting a value for the {@link JCameraSphericalSnapshotType#cameraGetTargetPosition() cameraGetTargetPosition} attribute.
   * A shallow reference equality check is used to prevent copying of the same value by returning {@code this}.
   * @param value A new value for cameraGetTargetPosition
   * @return A modified copy of the {@code this} object
   */
  public final JCameraSphericalSnapshot withCameraGetTargetPosition(Vector3D value) {
    if (this.cameraGetTargetPosition == value) return this;
    Vector3D newValue = Objects.requireNonNull(value, "cameraGetTargetPosition");
    return new JCameraSphericalSnapshot(
        this,
        this.cameraGetRight,
        this.cameraGetUp,
        this.cameraGetForward,
        this.cameraGetPosition,
        this.cameraGetAngleHeading,
        this.cameraGetAngleIncline,
        this.cameraGetZoom,
        this.cameraGetForwardProjectedOnXZ,
        newValue);
  }

  /**
   * This instance is equal to all instances of {@code JCameraSphericalSnapshot} that have equal attribute values.
   * @return {@code true} if {@code this} is equal to {@code another} instance
   */
  @Override
  public boolean equals(Object another) {
    if (this == another) return true;
    return another instanceof JCameraSphericalSnapshot
        && equalTo((JCameraSphericalSnapshot) another);
  }

  private boolean equalTo(JCameraSphericalSnapshot another) {
    return cameraGetRight.equals(another.cameraGetRight)
        && cameraGetUp.equals(another.cameraGetUp)
        && cameraGetForward.equals(another.cameraGetForward)
        && cameraGetPosition.equals(another.cameraGetPosition)
        && Double.doubleToLongBits(cameraGetAngleHeading) == Double.doubleToLongBits(another.cameraGetAngleHeading)
        && Double.doubleToLongBits(cameraGetAngleIncline) == Double.doubleToLongBits(another.cameraGetAngleIncline)
        && Double.doubleToLongBits(cameraGetZoom) == Double.doubleToLongBits(another.cameraGetZoom)
        && cameraGetForwardProjectedOnXZ.equals(another.cameraGetForwardProjectedOnXZ)
        && cameraGetTargetPosition.equals(another.cameraGetTargetPosition);
  }

  /**
   * Computes a hash code from attributes: {@code cameraGetRight}, {@code cameraGetUp}, {@code cameraGetForward}, {@code cameraGetPosition}, {@code cameraGetAngleHeading}, {@code cameraGetAngleIncline}, {@code cameraGetZoom}, {@code cameraGetForwardProjectedOnXZ}, {@code cameraGetTargetPosition}.
   * @return hashCode value
   */
  @Override
  public int hashCode() {
    int h = 5381;
    h += (h << 5) + cameraGetRight.hashCode();
    h += (h << 5) + cameraGetUp.hashCode();
    h += (h << 5) + cameraGetForward.hashCode();
    h += (h << 5) + cameraGetPosition.hashCode();
    h += (h << 5) + Double.hashCode(cameraGetAngleHeading);
    h += (h << 5) + Double.hashCode(cameraGetAngleIncline);
    h += (h << 5) + Double.hashCode(cameraGetZoom);
    h += (h << 5) + cameraGetForwardProjectedOnXZ.hashCode();
    h += (h << 5) + cameraGetTargetPosition.hashCode();
    return h;
  }

  /**
   * Prints the immutable value {@code JCameraSphericalSnapshot} with attribute values.
   * @return A string representation of the value
   */
  @Override
  public String toString() {
    return "JCameraSphericalSnapshot{"
        + "cameraGetRight=" + cameraGetRight
        + ", cameraGetUp=" + cameraGetUp
        + ", cameraGetForward=" + cameraGetForward
        + ", cameraGetPosition=" + cameraGetPosition
        + ", cameraGetAngleHeading=" + cameraGetAngleHeading
        + ", cameraGetAngleIncline=" + cameraGetAngleIncline
        + ", cameraGetZoom=" + cameraGetZoom
        + ", cameraGetForwardProjectedOnXZ=" + cameraGetForwardProjectedOnXZ
        + ", cameraGetTargetPosition=" + cameraGetTargetPosition
        + "}";
  }

  /**
   * Construct a new immutable {@code JCameraSphericalSnapshot} instance.
   * @param cameraGetRight The value for the {@code cameraGetRight} attribute
   * @param cameraGetUp The value for the {@code cameraGetUp} attribute
   * @param cameraGetForward The value for the {@code cameraGetForward} attribute
   * @param cameraGetPosition The value for the {@code cameraGetPosition} attribute
   * @param cameraGetAngleHeading The value for the {@code cameraGetAngleHeading} attribute
   * @param cameraGetAngleIncline The value for the {@code cameraGetAngleIncline} attribute
   * @param cameraGetZoom The value for the {@code cameraGetZoom} attribute
   * @param cameraGetForwardProjectedOnXZ The value for the {@code cameraGetForwardProjectedOnXZ} attribute
   * @param cameraGetTargetPosition The value for the {@code cameraGetTargetPosition} attribute
   * @return An immutable JCameraSphericalSnapshot instance
   */
  public static JCameraSphericalSnapshot of(Vector3D cameraGetRight, Vector3D cameraGetUp, Vector3D cameraGetForward, Vector3D cameraGetPosition, double cameraGetAngleHeading, double cameraGetAngleIncline, double cameraGetZoom, Vector3D cameraGetForwardProjectedOnXZ, Vector3D cameraGetTargetPosition) {
    return new JCameraSphericalSnapshot(cameraGetRight, cameraGetUp, cameraGetForward, cameraGetPosition, cameraGetAngleHeading, cameraGetAngleIncline, cameraGetZoom, cameraGetForwardProjectedOnXZ, cameraGetTargetPosition);
  }

  /**
   * Creates an immutable copy of a {@link JCameraSphericalSnapshotType} value.
   * Uses accessors to get values to initialize the new immutable instance.
   * If an instance is already immutable, it is returned as is.
   * @param instance The instance to copy
   * @return A copied immutable JCameraSphericalSnapshot instance
   */
  public static JCameraSphericalSnapshot copyOf(JCameraSphericalSnapshotType instance) {
    if (instance instanceof JCameraSphericalSnapshot) {
      return (JCameraSphericalSnapshot) instance;
    }
    return JCameraSphericalSnapshot.builder()
        .from(instance)
        .build();
  }

  /**
   * Creates a builder for {@link JCameraSphericalSnapshot JCameraSphericalSnapshot}.
   * @return A new JCameraSphericalSnapshot builder
   */
  public static JCameraSphericalSnapshot.Builder builder() {
    return new JCameraSphericalSnapshot.Builder();
  }

  /**
   * Builds instances of type {@link JCameraSphericalSnapshot JCameraSphericalSnapshot}.
   * Initialize attributes and then invoke the {@link #build()} method to create an
   * immutable instance.
   * <p><em>{@code Builder} is not thread-safe and generally should not be stored in a field or collection,
   * but instead used immediately to create instances.</em>
   */
  public static final class Builder {
    private static final long INIT_BIT_CAMERA_GET_RIGHT = 0x1L;
    private static final long INIT_BIT_CAMERA_GET_UP = 0x2L;
    private static final long INIT_BIT_CAMERA_GET_FORWARD = 0x4L;
    private static final long INIT_BIT_CAMERA_GET_POSITION = 0x8L;
    private static final long INIT_BIT_CAMERA_GET_ANGLE_HEADING = 0x10L;
    private static final long INIT_BIT_CAMERA_GET_ANGLE_INCLINE = 0x20L;
    private static final long INIT_BIT_CAMERA_GET_ZOOM = 0x40L;
    private static final long INIT_BIT_CAMERA_GET_FORWARD_PROJECTED_ON_X_Z = 0x80L;
    private static final long INIT_BIT_CAMERA_GET_TARGET_POSITION = 0x100L;
    private long initBits = 0x1ffL;

    private Vector3D cameraGetRight;
    private Vector3D cameraGetUp;
    private Vector3D cameraGetForward;
    private Vector3D cameraGetPosition;
    private double cameraGetAngleHeading;
    private double cameraGetAngleIncline;
    private double cameraGetZoom;
    private Vector3D cameraGetForwardProjectedOnXZ;
    private Vector3D cameraGetTargetPosition;

    private Builder() {
    }

    /**
     * Fill a builder with attribute values from the provided {@code com.io7m.jcamera.JCameraSphericalReadableType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(JCameraSphericalReadableType instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    /**
     * Fill a builder with attribute values from the provided {@code com.io7m.jcamera.JCameraSphericalSnapshotType} instance.
     * @param instance The instance from which to copy values
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder from(JCameraSphericalSnapshotType instance) {
      Objects.requireNonNull(instance, "instance");
      from((Object) instance);
      return this;
    }

    private void from(Object object) {
      long bits = 0;
      if (object instanceof JCameraSphericalReadableType) {
        JCameraSphericalReadableType instance = (JCameraSphericalReadableType) object;
        if ((bits & 0x1L) == 0) {
          setCameraGetRight(instance.cameraGetRight());
          bits |= 0x1L;
        }
        if ((bits & 0x2L) == 0) {
          setCameraGetZoom(instance.cameraGetZoom());
          bits |= 0x2L;
        }
        if ((bits & 0x4L) == 0) {
          setCameraGetForward(instance.cameraGetForward());
          bits |= 0x4L;
        }
        if ((bits & 0x8L) == 0) {
          setCameraGetAngleHeading(instance.cameraGetAngleHeading());
          bits |= 0x8L;
        }
        if ((bits & 0x10L) == 0) {
          setCameraGetTargetPosition(instance.cameraGetTargetPosition());
          bits |= 0x10L;
        }
        if ((bits & 0x20L) == 0) {
          setCameraGetAngleIncline(instance.cameraGetAngleIncline());
          bits |= 0x20L;
        }
        if ((bits & 0x40L) == 0) {
          setCameraGetPosition(instance.cameraGetPosition());
          bits |= 0x40L;
        }
        if ((bits & 0x80L) == 0) {
          setCameraGetUp(instance.cameraGetUp());
          bits |= 0x80L;
        }
        if ((bits & 0x100L) == 0) {
          setCameraGetForwardProjectedOnXZ(instance.cameraGetForwardProjectedOnXZ());
          bits |= 0x100L;
        }
      }
      if (object instanceof JCameraSphericalSnapshotType) {
        JCameraSphericalSnapshotType instance = (JCameraSphericalSnapshotType) object;
        if ((bits & 0x1L) == 0) {
          setCameraGetRight(instance.cameraGetRight());
          bits |= 0x1L;
        }
        if ((bits & 0x2L) == 0) {
          setCameraGetZoom(instance.cameraGetZoom());
          bits |= 0x2L;
        }
        if ((bits & 0x4L) == 0) {
          setCameraGetForward(instance.cameraGetForward());
          bits |= 0x4L;
        }
        if ((bits & 0x8L) == 0) {
          setCameraGetAngleHeading(instance.cameraGetAngleHeading());
          bits |= 0x8L;
        }
        if ((bits & 0x10L) == 0) {
          setCameraGetTargetPosition(instance.cameraGetTargetPosition());
          bits |= 0x10L;
        }
        if ((bits & 0x20L) == 0) {
          setCameraGetAngleIncline(instance.cameraGetAngleIncline());
          bits |= 0x20L;
        }
        if ((bits & 0x40L) == 0) {
          setCameraGetPosition(instance.cameraGetPosition());
          bits |= 0x40L;
        }
        if ((bits & 0x80L) == 0) {
          setCameraGetUp(instance.cameraGetUp());
          bits |= 0x80L;
        }
        if ((bits & 0x100L) == 0) {
          setCameraGetForwardProjectedOnXZ(instance.cameraGetForwardProjectedOnXZ());
          bits |= 0x100L;
        }
      }
    }

    /**
     * Initializes the value for the {@link JCameraSphericalSnapshotType#cameraGetRight() cameraGetRight} attribute.
     * @param cameraGetRight The value for cameraGetRight 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetRight(Vector3D cameraGetRight) {
      this.cameraGetRight = Objects.requireNonNull(cameraGetRight, "cameraGetRight");
      initBits &= ~INIT_BIT_CAMERA_GET_RIGHT;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraSphericalSnapshotType#cameraGetUp() cameraGetUp} attribute.
     * @param cameraGetUp The value for cameraGetUp 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetUp(Vector3D cameraGetUp) {
      this.cameraGetUp = Objects.requireNonNull(cameraGetUp, "cameraGetUp");
      initBits &= ~INIT_BIT_CAMERA_GET_UP;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraSphericalSnapshotType#cameraGetForward() cameraGetForward} attribute.
     * @param cameraGetForward The value for cameraGetForward 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetForward(Vector3D cameraGetForward) {
      this.cameraGetForward = Objects.requireNonNull(cameraGetForward, "cameraGetForward");
      initBits &= ~INIT_BIT_CAMERA_GET_FORWARD;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraSphericalSnapshotType#cameraGetPosition() cameraGetPosition} attribute.
     * @param cameraGetPosition The value for cameraGetPosition 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetPosition(Vector3D cameraGetPosition) {
      this.cameraGetPosition = Objects.requireNonNull(cameraGetPosition, "cameraGetPosition");
      initBits &= ~INIT_BIT_CAMERA_GET_POSITION;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraSphericalSnapshotType#cameraGetAngleHeading() cameraGetAngleHeading} attribute.
     * @param cameraGetAngleHeading The value for cameraGetAngleHeading 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetAngleHeading(double cameraGetAngleHeading) {
      this.cameraGetAngleHeading = cameraGetAngleHeading;
      initBits &= ~INIT_BIT_CAMERA_GET_ANGLE_HEADING;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraSphericalSnapshotType#cameraGetAngleIncline() cameraGetAngleIncline} attribute.
     * @param cameraGetAngleIncline The value for cameraGetAngleIncline 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetAngleIncline(double cameraGetAngleIncline) {
      this.cameraGetAngleIncline = cameraGetAngleIncline;
      initBits &= ~INIT_BIT_CAMERA_GET_ANGLE_INCLINE;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraSphericalSnapshotType#cameraGetZoom() cameraGetZoom} attribute.
     * @param cameraGetZoom The value for cameraGetZoom 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetZoom(double cameraGetZoom) {
      this.cameraGetZoom = cameraGetZoom;
      initBits &= ~INIT_BIT_CAMERA_GET_ZOOM;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraSphericalSnapshotType#cameraGetForwardProjectedOnXZ() cameraGetForwardProjectedOnXZ} attribute.
     * @param cameraGetForwardProjectedOnXZ The value for cameraGetForwardProjectedOnXZ 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetForwardProjectedOnXZ(Vector3D cameraGetForwardProjectedOnXZ) {
      this.cameraGetForwardProjectedOnXZ = Objects.requireNonNull(cameraGetForwardProjectedOnXZ, "cameraGetForwardProjectedOnXZ");
      initBits &= ~INIT_BIT_CAMERA_GET_FORWARD_PROJECTED_ON_X_Z;
      return this;
    }

    /**
     * Initializes the value for the {@link JCameraSphericalSnapshotType#cameraGetTargetPosition() cameraGetTargetPosition} attribute.
     * @param cameraGetTargetPosition The value for cameraGetTargetPosition 
     * @return {@code this} builder for use in a chained invocation
     */
    public final Builder setCameraGetTargetPosition(Vector3D cameraGetTargetPosition) {
      this.cameraGetTargetPosition = Objects.requireNonNull(cameraGetTargetPosition, "cameraGetTargetPosition");
      initBits &= ~INIT_BIT_CAMERA_GET_TARGET_POSITION;
      return this;
    }

    /**
     * Builds a new {@link JCameraSphericalSnapshot JCameraSphericalSnapshot}.
     * @return An immutable instance of JCameraSphericalSnapshot
     * @throws java.lang.IllegalStateException if any required attributes are missing
     */
    public JCameraSphericalSnapshot build() {
      if (initBits != 0) {
        throw new IllegalStateException(formatRequiredAttributesMessage());
      }
      return new JCameraSphericalSnapshot(
          null,
          cameraGetRight,
          cameraGetUp,
          cameraGetForward,
          cameraGetPosition,
          cameraGetAngleHeading,
          cameraGetAngleIncline,
          cameraGetZoom,
          cameraGetForwardProjectedOnXZ,
          cameraGetTargetPosition);
    }

    private String formatRequiredAttributesMessage() {
      List<String> attributes = new ArrayList<String>();
      if ((initBits & INIT_BIT_CAMERA_GET_RIGHT) != 0) attributes.add("cameraGetRight");
      if ((initBits & INIT_BIT_CAMERA_GET_UP) != 0) attributes.add("cameraGetUp");
      if ((initBits & INIT_BIT_CAMERA_GET_FORWARD) != 0) attributes.add("cameraGetForward");
      if ((initBits & INIT_BIT_CAMERA_GET_POSITION) != 0) attributes.add("cameraGetPosition");
      if ((initBits & INIT_BIT_CAMERA_GET_ANGLE_HEADING) != 0) attributes.add("cameraGetAngleHeading");
      if ((initBits & INIT_BIT_CAMERA_GET_ANGLE_INCLINE) != 0) attributes.add("cameraGetAngleIncline");
      if ((initBits & INIT_BIT_CAMERA_GET_ZOOM) != 0) attributes.add("cameraGetZoom");
      if ((initBits & INIT_BIT_CAMERA_GET_FORWARD_PROJECTED_ON_X_Z) != 0) attributes.add("cameraGetForwardProjectedOnXZ");
      if ((initBits & INIT_BIT_CAMERA_GET_TARGET_POSITION) != 0) attributes.add("cameraGetTargetPosition");
      return "Cannot build JCameraSphericalSnapshot, some of required attributes are not set " + attributes;
    }
  }
}
