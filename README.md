<br>
<div align="center">
    <a href="https://github.com/meo209/Artifact">
        <img src="docs/artifact.svg" alt="Logo" width="80" height="80">
    </a>
    <h2>Artifact</h2>
    <h4>Open 3D Game Engine written in Kotlin</h4>
</div>

<details>
    <summary>Table of Contents</summary>
        <ol>
        <li>
            <a href="#about-artifact">About Artifact</a>
        </li>
        <li>
            <a href="#technology">Technology</a>
            <ul>
                <li><a href="#list-of-nodes">List of nodes</a></li>
                <li><a href="#list-of-components">List of components</a></li>
                <li><a href="#list-of-events">List of events</a></li>
            </ul>
        </li>
        <li>
            <a href="#dependencies">Dependencies</a>
        </li>
        <li>
            <a href="#acknowledgements">Acknowledgements</a>
        </li>
    </ol>
</details>

# Note
This engine is by no means stable or production-ready.
I strongly advise against using this engine at the moment.

## About Artifact
Artifact is a desktop 3D game engine written in Kotlin.

## Getting started
Currently, Artifact is in a too early stage of development to be used as a real game engine.
Sorry :(

## Technology

Artifact uses a node- and component-based architecture, where a node is an object that can be placed in a scene.
A component is an element that can be attached to a node.
Nodes have the ability to have many children, but can only contain a single instance of each component type.
The engine is capable of using [OpenGL](https://www.opengl.org/) (primary) and [Vulkan](https://www.vulkan.org/) (secondary).
At the moment there is no working physics engine implemented.

## Q&A

### Do you have a Roadmap?
No, not at the moment. Although, I'm planning to open one.

### Can I contribute?
Yes, you can! Please contribute to improve the engine or fix bugs. Just make a PR and we'll take a look.

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


## Example
For some example code visit https://github.com/meo209/ArtifactTestProject

## Acknowledgements
Thanks to [svgrepo](https://www.svgrepo.com/svg/113419/lightning) for the logo.

## License
[GPLv3](https://www.gnu.org/licenses/gpl-3.0.html)