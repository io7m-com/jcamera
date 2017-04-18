module IntegratorSphericalForwardZoomScaled where

import qualified Clamp
import qualified Vector3f
import qualified InputSpherical

type ScaleFunction = Float -> Float

forward_speed :: InputSpherical.T -> Float -> Float -> Float -> Float -> ScaleFunction -> Float
forward_speed i sf a zoom scale delta =
  let accel = (scale zoom) * (a * delta) in
    if (InputSpherical.is_moving_forward_key i || InputSpherical.is_moving_forward_cursor i)
    then sf + accel
    else sf

backward_speed :: InputSpherical.T -> Float -> Float -> Float -> Float -> ScaleFunction -> Float
backward_speed i sf a zoom scale delta =
  let accel = (scale zoom) * (a * delta) in
    if (InputSpherical.is_moving_backward_key i || InputSpherical.is_moving_backward_cursor i)
    then sf - accel
    else sf

drag_forward_speed :: InputSpherical.T -> Float -> Float -> ScaleFunction -> Float
drag_forward_speed i a zoom scale delta =
  (InputSpherical.moving_forward_continuous i) * a * (scale zoom) * delta

forward :: (Vector3f.T, Vector3f.T, Float, InputSpherical.T, Float, Float, Float, Float, (ScaleFunction, ScaleFunction)) -> Float -> (Vector3f.T, Float)
forward (p, v_forward_on_xz, sf, i, a, d, ms, zoom, (scale_linear, scale_dragging)) delta =
  let
    sf0 = backward_speed i (forward_speed i sf a delta) a delta
    sms = (scale_linear zoom) * ms
    sf1 = Clamp.clamp sf0 (-sms, sms)
    
    sd  = drag_forward_speed i a zoom scale_dragging delta
    sf2 = sf1 + sd

    pr  = Vector3f.add3 p (Vector3f.scale v_forward_on_xz (sf2 * delta))
    sfr = sf1 * (d ** delta)
  in
    (pr, sfr)
