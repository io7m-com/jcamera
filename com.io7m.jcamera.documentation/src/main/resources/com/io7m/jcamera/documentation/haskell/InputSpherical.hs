module InputSpherical (T (..)) where

data T = T {

  is_moving_backward_key    :: Bool,
  is_moving_backward_cursor :: Bool,

  is_moving_forward_key    :: Bool,
  is_moving_forward_cursor :: Bool,

  is_moving_left_key     :: Bool,
  is_moving_left_cursor  :: Bool,

  is_moving_right_key    :: Bool,
  is_moving_right_cursor :: Bool,

  is_moving_up        :: Bool,
  is_moving_down      :: Bool,

  moving_forward_continuous :: Float,
  moving_right_continuous   :: Float,

  is_orbiting_heading_positive :: Bool,
  is_orbiting_heading_negative :: Bool,
  is_orbiting_incline_positive :: Bool,
  is_orbiting_incline_negative :: Bool,

  is_zooming_in  :: Bool,
  is_zooming_out :: Bool
  
} deriving (Eq, Show)
