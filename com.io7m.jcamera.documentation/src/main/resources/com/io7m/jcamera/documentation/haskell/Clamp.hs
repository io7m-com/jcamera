module Clamp where

clamp :: Float -> (Float, Float) -> Float
clamp x (lo, hi) = max (min x hi) lo
