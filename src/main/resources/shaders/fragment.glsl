#version 330 core

in vec3 exColor;
out vec4 color;

uniform sampler2D myTextureSampler;

void main() {
    color = vec4(exColor, 1.0);
}