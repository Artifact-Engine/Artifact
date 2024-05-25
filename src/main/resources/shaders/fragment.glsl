#version 330 core

layout(location = 0) out vec4 color;

in vec3 v_Color;
in vec3 v_Normal;
in vec3 v_FragPosition;

void main() {
    color = vec4(v_Color, 1.0);
}
