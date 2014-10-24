module IntegratorUp where

import qualified Clamp
import qualified Vector3f
import qualified Input

up_speed :: Input.T -> Float -> Float -> Float -> Float
up_speed i sf a delta =
  if (Input.is_moving_up i) 
  then sf + (a * delta) 
  else sf

down_speed :: Input.T -> Float -> Float -> Float -> Float
down_speed i sf a delta =
  if (Input.is_moving_down i) 
  then sf - (a * delta) 
  else sf

up :: (Vector3f.T, Vector3f.T, Float, Input.T, Float, Float, Float) -> Float -> (Vector3f.T, Float)
up (p, v_up, sf, i, a, d, ms) delta =
  let
    sf0 = down_speed i (up_speed i sf a delta) a delta
    sf1 = Clamp.clamp sf0 (-ms, ms)
    pr  = Vector3f.add3 p (Vector3f.scale v_up (sf1 * delta))
    sfr = sf1 * (d ** delta)
  in
    (pr, sfr)