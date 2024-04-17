import glm_.vec3.Vec3
import org.openartifact.Engine
import org.openartifact.Project
import org.openartifact.configuration.vSync
import org.openartifact.node.Node
import org.openartifact.node.Scene
import org.openartifact.node.dynamic.Translation
import org.openartifact.parsing.scene.SceneParser

fun main(args: Array<String>) {

    // Init Engine
    Engine

    vSync = true

    // Init project
    val gameProject = Project("Game")

    Engine.scriptables.add(PlayerInput())

    SceneParser().parseSceneToJson(
        scene = Scene(
            "Scene001",
            mutableListOf(
                Node(),
                Node(),
                Translation(Vec3(20, 0, 50))
            )
        )
    )


    // Engine init
    // Project init
    // Scene load
    // Enter game

}