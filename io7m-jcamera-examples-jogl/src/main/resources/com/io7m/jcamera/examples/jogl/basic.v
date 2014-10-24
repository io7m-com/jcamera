#version 130

uniform mat4 m_modelview;
uniform mat4 m_projection;

in vec3 v_position;
in vec3 v_color;

out vec3 f_color;

void
main (void)
{
  gl_Position = m_projection * (m_modelview * vec4 (v_position, 1.0));
  f_color = v_color;
}