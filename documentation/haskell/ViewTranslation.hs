module ViewTranslation where

import qualified Matrix4f
import qualified Vector3f
import qualified Vector4f
import Vector3f (x, y, z)

translation :: Vector3f.T -> Matrix4f.T
translation p =
  let np_x = -(x p)
      np_y = -(y p)
      np_z = -(z p)
  in
    Matrix4f.T {
      Matrix4f.column_3 = Vector4f.V4 np_x np_y np_z 1.0,
      Matrix4f.column_2 = Vector4f.V4 0.0  0.0  1.0  0.0,
      Matrix4f.column_1 = Vector4f.V4 0.0  1.0  0.0  0.0,
      Matrix4f.column_0 = Vector4f.V4 1.0  0.0  0.0  0.0
    }
