#version 330

in vec2 texCoord;

out vec4 fragColor;

uniform sampler2D texSampler;
uniform vec3 color;
uniform int useColor;

void main()
{
    if ( useColor == 1 )
    {
        fragColor = vec4(color, 1);
    }
    else
    {
        fragColor = texture(texSampler, texCoord);
    }
}