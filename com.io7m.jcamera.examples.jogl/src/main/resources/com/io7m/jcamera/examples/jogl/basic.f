#version 130

in vec3 f_color;

void
main (void)
{
  gl_FragColor = vec4 (f_color, 1.0);
}