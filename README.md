![Artifact](https://socialify.git.ci/Artifact-Engine/Artifact/image?description=1&font=Inter&language=1&name=1&pattern=Circuit%20Board&theme=Dark)

![GitHub License](https://img.shields.io/github/license/Artifact-Engine/Artifact)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/Artifact-Engine/Artifact)
[![wakatime](https://wakatime.com/badge/user/8a1e0c8a-eea8-45b2-a408-ab9228e0ee06/project/49481bf8-200d-4d50-b8d9-09d74073950d.svg)](https://wakatime.com/badge/user/8a1e0c8a-eea8-45b2-a408-ab9228e0ee06/project/49481bf8-200d-4d50-b8d9-09d74073950d)

<details>
    <summary>Table of Contents</summary>
        <ol>
        <li>
            <a href="#about-artifact">About Artifact</a>
        </li>
        <li>
            <a href="#getting-started">Getting started</a>
        </li>
        <li>
            <a href="#technology">Technology</a>
        </li>
        <li>
            <a href="#qa">Q&A</a>
        </li>
        <li>
            <a href="#dependencies">Dependencies</a>
        </li>
        <li>
            <a href="#example">Example</a>
        </li>
        <li>
            <a href="#acknowledgements">Acknowledgements</a>
        </li>
        <li>
            <a href="#license">License</a>
        </li>
    </ol>
</details>

# Note
This engine is by no means stable or production-ready.
I strongly advise against using this engine at the moment.

## About Artifact
Artifact is a desktop 3D game engine written in Kotlin.

## Getting started
There is a [Template](https://github.com/Artifact-Engine/Template) that you can use to generate some basic code.
If you need an example, take a look at [Sandbox](https://github.com/Artifact-Engine/Sandbox).

## Technology

Artifact uses a node- and component-based architecture, where a node is an object that can be placed in a scene.
A component is an element that can be attached to a node.
Nodes have the ability to have many children, but can only contain a single instance of each component type.
The engine is primarily using [OpenGL](https://www.opengl.org/).
At the moment there is no working physics engine implemented.

## Q&A

### Can I contribute?
Yes, you can! Please contribute to improve the engine or fix bugs. Just make a PR and we'll take a look.

### Does this run on x os?
Artifact is currently compatible with Windows and Gnu/Linux.
However, it should be noted that windows support may be somewhat limited.

## Dependencies
(Might not be 100% accurate)
<br>

[LWJGL](https://www.lwjgl.org/)
<br>
[glm](https://github.com/kotlin-graphics/glm)
<br>
[SLF4J](https://www.slf4j.org/)
<br>
[kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines)
<br>
[Gson](https://github.com/google/gson)
<br>
[Kotlin-reflect](https://kotlinlang.org/docs/reflection.html)
<br>
[Apache Commons](https://commons.apache.org/)


## Example
For some example code visit https://github.com/Artifact-Engine/Sandbox

## Acknowledgements
Thanks to [svgrepo](https://www.svgrepo.com/svg/113419/lightning) for the logo.

## License
This project is licensed under the [GPLv3](https://www.gnu.org/licenses/gpl-3.0.html).
