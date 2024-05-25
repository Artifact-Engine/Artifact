#version 330 core

layout(location = 0) out vec4 color;

in vec3 v_Color;
in vec3 v_LightColor;

void main() {
    float ambientStrenght = 0.1;
    vec3 ambient = ambientStrenght * v_LightColor;

    vec3 result = ambient * v_Color;

    color = vec4(result, 1.0);
}