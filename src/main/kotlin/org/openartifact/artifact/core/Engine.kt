package org.openartifact.artifact.core

import glm_.vec3.Vec3
import org.openartifact.artifact.game.components.TransformComponent
import org.openartifact.artifact.game.nodes.CubeNode
import org.openartifact.artifact.game.scene.Scene
import org.openartifact.artifact.game.scene.readNodes
import org.openartifact.artifact.game.scene.writeNodes
import org.slf4j.LoggerFactory
import java.io.File

object Engine {

    private val logger = LoggerFactory.getLogger(javaClass)

    lateinit var application: Application

    private const val BASE_PKG = "org.openartifact.artifact"
    const val NODE_PGK = "$BASE_PKG.game.nodes"
    const val COMPONENT_PGK = "$BASE_PKG.game.components"

    private fun constructTempScene(): Scene {
        val scene = Scene("TempScene")
        val node = CubeNode()
        node.components.add(TransformComponent(
            Vec3(0.0f, 0.0f, 0.0f),
            Vec3(0.0f, 0.0f, 0.0f),
            Vec3(1.0f, 1.0f, 1.0f)
        ))
        scene.nodes.add(node)

        return scene
    }

    fun init(projectFile: File) {
        logger.info("Trying to read application...")

        application = readApplicationFromFile(projectFile)

        val scene = constructTempScene()

        val json = writeNodes(scene)

        println("Json (nodes): $json")

        val nodes = readNodes(json)

        println(nodes)

        val cubeNode = nodes[0] as CubeNode

        val transformComponent = cubeNode.components[0] as TransformComponent

        println(transformComponent.position)
        println(transformComponent.rotation)
        println(transformComponent.scale)
    }

}