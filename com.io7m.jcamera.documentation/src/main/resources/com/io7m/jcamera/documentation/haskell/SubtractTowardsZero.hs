module SubtractTowardsZero where

sub :: Float -> Float -> Float
sub x y =
  let sign = if x > 0.0 then 1.0 else -1.0 in
    (max 0.0 ((abs x) - y)) * sign
