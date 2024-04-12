package org.openartifact

import glm_.vec3.Vec3
import org.openartifact.node.Node
import org.openartifact.node.dynamic.Translation
import org.openartifact.node.fixed.Scene
import org.openartifact.parsing.scene.SceneParser
import org.slf4j.Logger
import org.slf4j.LoggerFactory

data class Project(val name: String) {

    private var logger: Logger = LoggerFactory.getLogger(name)

    fun initialize() {
        logger.info("Loading Artifact Project: $name")

        if (Engine.engineState != Engine.EngineState.INITIALIZED)
            throw IllegalStateException("Engine not initialized.")

        logger.info("Loading Scene")
        println(SceneParser().parseSceneToJson(
            scene = Scene(
                    "Scene001",
                    mutableListOf(
                        Node(),
                        Node(),
                        Translation(Vec3(20, 0, 50))
                    )
                )
        ))
    }

}