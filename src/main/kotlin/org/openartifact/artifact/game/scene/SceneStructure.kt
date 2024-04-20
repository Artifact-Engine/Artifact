package org.openartifact.artifact.game.scene

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import org.openartifact.artifact.core.Engine
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.game.components.TransformComponent
import org.openartifact.artifact.game.nodes.CubeNode
import org.openartifact.artifact.utils.requireFile
import java.io.File
import java.lang.reflect.Type

fun validateSceneStructure(sceneFile: File) {

    requireFile(sceneFile, "nodes.json")
    requireFile(sceneFile, "settings.json")

}

fun readScene(sceneFile: File): Scene {



    TODO()
}

fun writeNodes(scene: Scene): String =
    Gson().toJson(scene.nodes)

fun readNodes(source: String): List<Node> {
    val listType: Type = object : TypeToken<List<Node>>() {}.type
    return GsonBuilder()
        .registerTypeAdapter(Node::class.java, NodeDeserializer())
        .registerTypeAdapter(Component::class.java, ComponentDeserializer())
        .create().fromJson(source, listType)
}


class NodeDeserializer : JsonDeserializer<Node> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Node {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type").asString

        val kClass = Class.forName("${Engine.NODE_PGK}.$type").kotlin
        require(Node::class.java.isAssignableFrom(kClass.java)) { "Class $type is not a subclass of Node" }

        return context.deserialize(json, kClass.java)
    }

}

class ComponentDeserializer : JsonDeserializer<Component> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Component {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type").asString

        val kClass = Class.forName("${Engine.COMPONENT_PGK}.$type").kotlin
        println(kClass.simpleName)
        require(Component::class.java.isAssignableFrom(kClass.java)) { "Class $type is not a subclass of Component" }

        return context.deserialize(json, kClass.java)
    }

}