module Backward where

import qualified Vector3f
import qualified Forward

move_backward :: Vector3f.T -> Vector3f.T -> Float -> Vector3f.T
move_backward p forward d = Forward.move_forward p forward (-d)
