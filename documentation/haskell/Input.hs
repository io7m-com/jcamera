module Input (T (..)) where

data T = T {
  is_moving_backward  :: Bool,
  is_moving_forward   :: Bool,
  is_moving_left      :: Bool,
  is_moving_right     :: Bool,
  is_moving_up        :: Bool,
  is_moving_down      :: Bool,
  rotation_horizontal :: Float,
  rotation_vertical   :: Float
} deriving (Eq, Show)
