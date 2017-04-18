module Left where

import qualified Vector3f
import qualified Right

move_left :: Vector3f.T -> Vector3f.T -> Float -> Vector3f.T
move_left p right d = Right.move_right p right (-d)
