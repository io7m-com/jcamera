module NormalizedDevice where

import qualified Vector3f

width :: Float
width = 640.0

height :: Float
height = 480.0

to_ndc_from_top_left :: (Integer, Integer) -> (Float, Float)
to_ndc_from_top_left (sx, sy) =
  let
    center_x = width / 2.0
    center_y = height / 2.0
    rx = ((fromIntegral sx - center_x) / width) * 2.0
    ry = ((fromIntegral sy - center_y) / height) * 2.0
  in
    (rx, ry)

to_ndc_from_bottom_left :: (Integer, Integer) -> (Float, Float)
to_ndc_from_bottom_left (sx, sy) =
  let
    center_x = width / 2.0
    center_y = height / 2.0
    rx = ((fromIntegral sx - center_x) / width) * 2.0
    ry = ((fromIntegral sy - center_y) / height) * 2.0
  in
    (rx, -ry)

