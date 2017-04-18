module ExampleDefaultVectors where

import qualified Vector3f

h :: Float
h = 0

v :: Float
v = pi / 2.0

p :: Vector3f.T
p = Vector3f.V3 0.0 0.0 0.0

forward_x :: Float
forward_x = cos (v) * cos (h)
--        = cos (π / 2) * cos (0)
--        = 0 * 1
--        = 0

forward_y :: Float
forward_y = sin (h)
--        = sin (0)
--        = 0

forward_z :: Float
forward_z = -(cos (h) * sin (v))
--        = -(cos (0) * sin (π / 2))
--        = -(1 * 1)
--        = -1

forward :: Vector3f.T
forward = Vector3f.V3 forward_x forward_y forward_z
--      = (0, 0, -1)

right_x :: Float
right_x = cos (v - (pi / 2.0)) * cos (h)
--      = cos (0) * cos (0)
--      = 1 * 1
--      = 1

right_y :: Float
right_y = sin (h)
--      = sin (0)
--      = 0

right_z :: Float
right_z = -(cos (h) * sin (v - (pi / 2)))
--      = -(cos (0) * sin (0))
--      = -(1 * 0)
--      = 0

right :: Vector3f.T
right = Vector3f.V3 right_x right_y right_z
--    = (1, 0, 0)

up :: Vector3f.T
up = Vector3f.cross right forward
-- = ((right_y * forward_z) - (right_z * forward_y),
--    (right_z * forward_x) - (right_x * forward_z),
--    (right_x * forward_y) - (right_y * forward_x))
-- = ((0 * -1) - (0 * 0),
--    (0 * 0) - (1 * -1),
--    (1 * 0) - (0 * 0))
-- = (0, 1, 0)
