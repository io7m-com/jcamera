#version 130

in vec3 f_color;
in vec2 f_uv;

uniform sampler2D f_texture;

void
main (void)
{
  vec4 c = texture (f_texture, f_uv);
  gl_FragColor = vec4 (c.xyz, 1.0);
}