module Down where

import qualified Vector3f
import qualified Up

move_down :: Vector3f.T -> Vector3f.T -> Float -> Vector3f.T
move_down p up d = Up.move_up p up (-d)
