module IntegratorSphericalForward where

import qualified Clamp
import qualified Vector3f
import qualified InputSpherical

forward_speed :: InputSpherical.T -> Float -> Float -> Float -> Float
forward_speed i sf a delta =
  if (InputSpherical.is_moving_forward_key i || InputSpherical.is_moving_forward_cursor i)
  then sf + (a * delta)
  else sf

backward_speed :: InputSpherical.T -> Float -> Float -> Float -> Float
backward_speed i sf a delta =
  if (InputSpherical.is_moving_backward_key i || InputSpherical.is_moving_backward_cursor i)
  then sf - (a * delta)
  else sf

drag_forward_speed :: InputSpherical.T -> Float -> Float -> Float
drag_forward_speed i a delta =
  (InputSpherical.moving_forward_continuous i) * a * delta

forward :: (Vector3f.T, Vector3f.T, Float, InputSpherical.T, Float, Float, Float) -> Float -> (Vector3f.T, Float)
forward (p, v_forward_on_xz, sf, i, a, d, ms) delta =
  let
    sf0 = backward_speed i (forward_speed i sf a delta) a delta
    sf1 = Clamp.clamp sf0 (-ms, ms)
    sd  = drag_forward_speed i a delta
    sf2 = sf1 + sd
    pr  = Vector3f.add3 p (Vector3f.scale v_forward_on_xz (sf2 * delta))
    sfr = sf1 * (d ** delta)
  in
    (pr, sfr)
