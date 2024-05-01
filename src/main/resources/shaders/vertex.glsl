#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec2 inTexCoord;

uniform mat4 MVP;

out vec2 texCoord;

void main() {
  gl_Position = MVP * vec4(position, 1);
  texCoord = inTexCoord;
}