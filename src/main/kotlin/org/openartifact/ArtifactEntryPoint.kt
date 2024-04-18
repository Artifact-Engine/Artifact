package org.openartifact

import glm_.vec3.Vec3
import org.openartifact.configuration.getProjectsDirectory
import org.openartifact.core.Engine
import org.openartifact.core.application.Application
import org.openartifact.node.Node
import org.openartifact.node.Scene
import org.openartifact.node.fixed.cube.CubeNode
import org.openartifact.parsing.scene.SceneParser
import java.io.File

/**
 * Entry point for the engine.
 * Uses arguments to specify which project to load.
 */

fun main(args: Array<String>) {
    Engine // Initialize the engine

    val projectFile = File(getProjectsDirectory(), args[0])

    if (!projectFile.exists())
        projectFile.mkdir()

    val list = mutableListOf<Node>()

    repeat(1) {
        list.add(CubeNode(Vec3(50, 50, 50)))
    }

    val scene = Scene("testscene",
        list
    )

    val sceneParser = SceneParser()
    val sceneJson = sceneParser.parseSceneToJson(scene)

    println(sceneJson)

    val sceneFromJson = sceneParser.parseJsonToScene(sceneJson)

    println(sceneFromJson)

    Engine.application = Application()
    Engine.application.loadScene(sceneFromJson) // Fix shit
}