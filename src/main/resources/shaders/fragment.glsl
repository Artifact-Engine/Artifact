#version 330 core

layout(location = 0) out vec4 a_Color;

in vec3 v_Color;
in vec3 v_Normal;
in vec3 v_Position;

uniform vec3 u_Light_Pos;
uniform vec3 u_Light_Color;

void main() {
    float ambientStrength = 0.1;
    vec3 ambient = ambientStrength * u_Light_Color;

    vec3 norm = normalize(v_Normal);
    vec3 lightDir = normalize(u_Light_Pos - v_Position);

    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff + u_Light_Color;

    vec3 result = (ambient + diffuse) * v_Color;
    a_Color = vec4(result, 1.0);
}
