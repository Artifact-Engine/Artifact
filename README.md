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
            <a href="#usage">Usage</a>
            <ul>
                <li><a href="#nodes">Nodes</a></li>
                <li><a href="#components">Components</a></li>
                <li><a href="#events">Events</a></li>
            </ul>
        </li>
        <li>
            <a href="#acknowledgments">Acknowledgments</a>
        </li>
    </ol>
</details>

## About Artifact
Artifact is a desktop 3D game engine written in Kotlin.
It started as a side project by [meo209](https://github.com/meo209) in april 2024.

## Getting Started
Currently, Artifact is in a too early phase of development to be used as a real game engine.
Sorry :(

## Usage

Artifact employs a node and component-based architecture, wherein a node represents an object that can be placed within a scene.
A component is an element that can be attached to a node.
Nodes have the capacity to possess numerous children, yet they can only accommodate a single instance of each component type.

For example, a node named Player of type Translation could have one PlayerInput script-component attached to it.
The Player node can have unlimited amounts of children. For example a sprite children to render a texture.

### Nodes
- Translation

### Components
- Scriptable

## Events
- KeyPressEvent
- KeyReleaseEvent
- KeyRepeatEvent

## Acknowledgments
Thanks to [svgrepo](https://www.svgrepo.com/svg/113419/lightning) for the logo.

