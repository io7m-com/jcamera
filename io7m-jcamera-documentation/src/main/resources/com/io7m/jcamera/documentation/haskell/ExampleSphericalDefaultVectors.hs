module ExampleSphericalDefaultVectors where

import qualified Vector3f

heading :: Float
heading = -pi / 2.0

incline :: Float
incline = 0

radius :: Float
radius = 8

target :: Vector3f.T
target = Vector3f.V3 0 1 4

direction :: Float -> Float -> Vector3f.T
direction heading incline =
  let
    x = (cos heading) * (cos incline)
    y = sin incline
    z = -((cos incline) * (sin heading))
  in
    Vector3f.V3 x y z

d :: Vector3f.T
d = direction heading incline

q :: Vector3f.T
q = Vector3f.scale d radius

p :: Vector3f.T
p = Vector3f.add3 q target

forward :: Vector3f.T
forward = Vector3f.normalize (Vector3f.scale d (-1.0))

up :: Vector3f.T
up =
  let d = direction heading (incline - (pi / 2.0)) in
    Vector3f.normalize (Vector3f.scale d (-1.0))

right :: Vector3f.T
right = Vector3f.cross forward up

project :: Vector3f.T -> Vector3f.T
project v =
  let vx = Vector3f.x v
      vz = Vector3f.z v
  in Vector3f.normalize (Vector3f.V3 vx 0.0 vz)

forward_on_xz :: Vector3f.T
forward_on_xz = project forward
