module View where

import ViewTranslation (translation)
import ViewRotation (rotation)
import qualified Matrix4f
import qualified Vector3f

view_matrix :: Vector3f.T -> (Vector3f.T, Vector3f.T, Vector3f.T) -> Matrix4f.T
view_matrix p (right, up, forward) =
  Matrix4f.mult (rotation (right, up, forward)) (translation p)
