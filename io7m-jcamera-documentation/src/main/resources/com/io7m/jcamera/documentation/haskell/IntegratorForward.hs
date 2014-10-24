module IntegratorForward where

import qualified Clamp
import qualified Vector3f
import qualified Input

forward_speed :: Input.T -> Float -> Float -> Float -> Float
forward_speed i sf a delta =
  if (Input.is_moving_forward i) 
  then sf + (a * delta) 
  else sf

backward_speed :: Input.T -> Float -> Float -> Float -> Float
backward_speed i sf a delta =
  if (Input.is_moving_backward i) 
  then sf - (a * delta) 
  else sf

forward :: (Vector3f.T, Vector3f.T, Float, Input.T, Float, Float, Float) -> Float -> (Vector3f.T, Float)
forward (p, v_forward, sf, i, a, d, ms) delta =
  let
    sf0 = backward_speed i (forward_speed i sf a delta) a delta
    sf1 = Clamp.clamp sf0 (-ms, ms)
    pr  = Vector3f.add3 p (Vector3f.scale v_forward (sf1 * delta))
    sfr = sf1 * (d ** delta)
  in
    (pr, sfr)