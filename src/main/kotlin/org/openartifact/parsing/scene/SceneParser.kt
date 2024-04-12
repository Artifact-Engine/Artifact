package org.openartifact.parsing.scene

import com.google.gson.Gson
import org.openartifact.node.fixed.Scene

/**
 * Manages the parsing of scenes and handling the process
 */
class SceneParser {

    /**
     * Encodes a scene to json
     */
    fun parseSceneToJson(scene: Scene): String =
        Gson().toJson(scene)

    /**
     * Decodes a json string to a scene
     */
    fun parseJsonToScene(json: String): Scene =
        Gson().fromJson(json, Scene::class.java)

}