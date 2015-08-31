module ViewRotation where

import qualified Matrix4f
import qualified Vector3f
import qualified Vector4f
import Vector3f (x, y, z)

rotation :: (Vector3f.T, Vector3f.T, Vector3f.T) -> Matrix4f.T
rotation (right, up, forward) =
  Matrix4f.T {
    Matrix4f.column_3 = Vector4f.V4  0.0         0.0         0.0        1.0,
    Matrix4f.column_2 = Vector4f.V4 (x forward) (y forward) (z forward) 0.0,
    Matrix4f.column_1 = Vector4f.V4 (x up)      (y up)      (z up)      0.0,
    Matrix4f.column_0 = Vector4f.V4 (x right)   (y right)   (z right)   0.0
  }
