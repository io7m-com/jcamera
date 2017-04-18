#version 130

uniform mat4 m_modelview;
uniform mat4 m_projection;

in vec3 v_position;
in vec3 v_color;
in vec2 v_uv;

out vec3 f_color;
out vec2 f_uv;

void
main (void)
{
  gl_Position = m_projection * (m_modelview * vec4 (v_position, 1.0));
  f_color = v_color;
  f_uv = v_uv;
}