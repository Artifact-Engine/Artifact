#version 330 core

layout(location = 0) in vec3 a_Position;
layout(location = 1) in vec3 a_Normal;

uniform mat4 u_MVP;
uniform vec3 u_Color;

out vec3 v_Color;

void main() {
    gl_Position = u_MVP * vec4(a_Position, 1.0);
    v_Color = u_Color;
}