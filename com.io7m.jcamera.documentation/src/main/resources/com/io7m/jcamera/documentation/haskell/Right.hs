module Right where

import qualified Vector3f

move_right :: Vector3f.T -> Vector3f.T -> Float -> Vector3f.T
move_right p right d = Vector3f.add3 p (Vector3f.scale right d)
