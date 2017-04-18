
-- Copyright © 2016 <code@io7m.com> http://io7m.com
--
-- Permission to use, copy, modify, and/or distribute this software for any
-- purpose with or without fee is hereby granted, provided that the above
-- copyright notice and this permission notice appear in all copies.
--
-- THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
-- WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
-- MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
-- ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
-- WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
-- ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
-- OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.

module Matrix4f (
  T (..),
  mult,
  mult_v,
  row,
  row_column
) where

import qualified Vector4f as V4

data T = T {
  column_0 :: V4.T,
  column_1 :: V4.T,
  column_2 :: V4.T,
  column_3 :: V4.T
} deriving (Eq, Ord, Show)

v4_get :: V4.T -> Integer -> Float
v4_get v 0 = V4.x v
v4_get v 1 = V4.y v
v4_get v 2 = V4.z v
v4_get v 3 = V4.w v
v4_get _ _ = undefined

row_column :: T -> (Integer, Integer) -> Float
row_column m (r, c) =
  case c of
    0 -> v4_get (column_0 m) r
    1 -> v4_get (column_1 m) r
    2 -> v4_get (column_2 m) r
    3 -> v4_get (column_3 m) r
    _ -> undefined

row :: T -> Integer -> V4.T
row m r =
  V4.V4
    (row_column m (r, 0))
    (row_column m (r, 1))
    (row_column m (r, 2))
    (row_column m (r, 3))

mult_v :: T -> V4.T -> V4.T
mult_v m v =
  V4.V4
    (V4.dot4 (row m 0) v)
    (V4.dot4 (row m 1) v)
    (V4.dot4 (row m 2) v)
    (V4.dot4 (row m 3) v)

mult :: T -> T -> T
mult m0 m1 =
  let
    m0r0 = row m0 0
    m0r1 = row m0 1
    m0r2 = row m0 2
    m0r3 = row m0 3

    m1c0 = column_0 m1
    m1c1 = column_1 m1
    m1c2 = column_2 m1
    m1c3 = column_3 m1

    r0c0 = V4.dot4 m0r0 m1c0
    r0c1 = V4.dot4 m0r1 m1c0
    r0c2 = V4.dot4 m0r2 m1c0
    r0c3 = V4.dot4 m0r3 m1c0

    r1c0 = V4.dot4 m0r0 m1c1
    r1c1 = V4.dot4 m0r1 m1c1
    r1c2 = V4.dot4 m0r2 m1c1
    r1c3 = V4.dot4 m0r3 m1c1

    r2c0 = V4.dot4 m0r0 m1c2
    r2c1 = V4.dot4 m0r1 m1c2
    r2c2 = V4.dot4 m0r2 m1c2
    r2c3 = V4.dot4 m0r3 m1c2

    r3c0 = V4.dot4 m0r0 m1c3
    r3c1 = V4.dot4 m0r1 m1c3
    r3c2 = V4.dot4 m0r2 m1c3
    r3c3 = V4.dot4 m0r3 m1c3
  in
    T {
      column_0 = V4.V4 r0c0 r1c0 r2c0 r3c0,
      column_1 = V4.V4 r0c1 r1c1 r2c1 r3c1,
      column_2 = V4.V4 r0c2 r1c2 r2c2 r3c2,
      column_3 = V4.V4 r0c3 r1c3 r2c3 r3c3
    }
