module IntegratorAngularHorizontal where

import qualified Clamp
import qualified Input

horizontal :: (Float, Float, Input.T, Float, Float, Float) -> Float -> (Float, Float)
horizontal (h, sh, i, a, d, ms) delta =
  let
    sf0 = sh + ((Input.rotation_horizontal i) * a * delta)
    sf1 = Clamp.clamp sf0 (-ms, ms)
    hr  = h + (sf1 * delta)
    sfr = sf1 * (d ** delta)
  in
    (hr, sfr)
