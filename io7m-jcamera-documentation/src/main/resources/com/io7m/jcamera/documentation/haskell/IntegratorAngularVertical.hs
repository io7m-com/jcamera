module IntegratorAngularVertical where

import qualified Clamp
import qualified Input

vertical :: (Float, Float, Input.T, Float, Float, Float) -> Float -> (Float, Float)
vertical (v, sv, i, a, d, ms) delta =
  let
    sf0 = sv + ((Input.rotation_vertical i) * a * delta)
    sf1 = Clamp.clamp sf0 (-ms, ms)
    vr  = v + (sf1 * delta)
    sfr = sf1 * (d ** delta)
  in
    (vr, sfr)
