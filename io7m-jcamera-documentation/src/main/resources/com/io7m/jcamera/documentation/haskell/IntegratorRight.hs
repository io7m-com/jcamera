module IntegratorRight where

import qualified Clamp
import qualified Vector3f
import qualified Input

right_speed :: Input.T -> Float -> Float -> Float -> Float
right_speed i sf a delta =
  if (Input.is_moving_right i) 
  then sf + (a * delta) 
  else sf

left_speed :: Input.T -> Float -> Float -> Float -> Float
left_speed i sf a delta =
  if (Input.is_moving_left i) 
  then sf - (a * delta) 
  else sf

right :: (Vector3f.T, Vector3f.T, Float, Input.T, Float, Float, Float) -> Float -> (Vector3f.T, Float)
right (p, v_right, sf, i, a, d, ms) delta =
  let
    sf0 = left_speed i (right_speed i sf a delta) a delta
    sf1 = Clamp.clamp sf0 (-ms, ms)
    pr  = Vector3f.add3 p (Vector3f.scale v_right (sf1 * delta))
    sfr = sf1 * (d ** delta)
  in
    (pr, sfr)