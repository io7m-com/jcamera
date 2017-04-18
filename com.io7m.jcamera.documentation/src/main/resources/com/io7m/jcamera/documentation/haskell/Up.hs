module Up where

import qualified Vector3f

move_up :: Vector3f.T -> Vector3f.T -> Float -> Vector3f.T
move_up p up d = Vector3f.add3 p (Vector3f.scale up d)
