#version 330 core

layout(location = 0) in vec3 a_Position;

uniform vec4 u_Color;
uniform mat4 u_MVP;

out vec4 v_Color;
out vec3 v_Position;

void main() {
    gl_Position = u_MVP * vec4(a_Position, 1.0);
    v_Color = u_Color;
    v_Position = a_Position;
}