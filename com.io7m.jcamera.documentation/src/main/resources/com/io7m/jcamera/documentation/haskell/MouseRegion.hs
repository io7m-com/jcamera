module MouseRegion (T, newRegion, coefficients) where

data Origin =
    TopLeft
  | BottomLeft
    deriving (Eq, Show)

data T = T {
  origin   :: Origin,
  width    :: Float,
  height   :: Float,
  center_x :: Float,
  center_y :: Float
} deriving (Eq, Show)

newRegion :: Origin -> Float -> Float -> T
newRegion o w h = T {
  origin   = o,
  width    = w,
  height   = h,
  center_x = w / 2.0,
  center_y = h / 2.0
}

coefficients :: T -> (Integer, Integer) -> (Float, Float)
coefficients r (sx, sy) =
  let fx = fromIntegral sx
      fy = fromIntegral sy
      ox = ((fx - center_x r) / width r) * 2.0
      oy = ((fy - center_y r) / height r) * 2.0
  in
    case (origin r) of
      TopLeft    -> (-ox, -oy)
      BottomLeft -> (-ox, oy)
