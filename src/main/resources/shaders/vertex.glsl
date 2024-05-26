#version 330 core

layout(location = 0) in vec3 a_Position;
layout(location = 1) in vec3 a_Normal;

uniform mat4 u_Projection;
uniform mat4 u_View;
uniform mat4 u_Model;

uniform vec3 u_Color;

out vec3 v_Position;
out vec3 v_Color;
out vec3 v_Normal;

void main() {
    mat4 mvp = u_Projection * u_View * u_Model;
    gl_Position = mvp * vec4(a_Position, 1.0);
    v_Position = vec3(u_Model * vec4(a_Position, 1.0));
    v_Color = u_Color;
    v_Normal = a_Normal;
}