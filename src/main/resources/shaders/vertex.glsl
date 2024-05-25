#version 330 core

layout(location = 0) in vec3 a_Position;

uniform mat4 u_MVP;
uniform vec3 u_Color;
uniform vec3 u_LightColor;

out vec3 v_Color;
out vec3 v_LightColor;

void main() {
    gl_Position = u_MVP * vec4(a_Position, 1.0);
    v_Color = u_Color;
    v_LightColor = u_LightColor;
}