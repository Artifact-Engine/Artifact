#version 330 core

layout(location = 0) out vec4 color;

in vec2 v_TexCoord;

uniform sampler2D texSampler;

void main() {
    color = texture(texSampler, v_TexCoord);
}