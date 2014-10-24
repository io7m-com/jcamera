module Forward where

import qualified Vector3f

move_forward :: Vector3f.T -> Vector3f.T -> Float -> Vector3f.T
move_forward p forward d = Vector3f.add3 p (Vector3f.scale forward d)
