package org.openartifact.artifact.game.scene

import com.google.gson.*
import com.google.gson.reflect.TypeToken
import org.openartifact.artifact.core.Engine
import org.openartifact.artifact.core.GameContext
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.utils.requireFile
import java.io.File
import java.lang.reflect.Type

fun readScene(sceneFile : File) : Scene {
    val nodesFile = requireFile(sceneFile, "nodes.json")
    val settingsFile = requireFile(sceneFile, "settings.json")

    val scene = Scene(readSettings(settingsFile.readText()), readNodes(nodesFile.readText()))

    return scene
}

fun writeNodes(scene : Scene) : String =
    Gson().toJson(scene.nodes)

fun readNodes(source : String) : List<Node> {
    val listType : Type = object : TypeToken<List<Node>>() {}.type
    return GsonBuilder()
        .registerTypeAdapter(Node::class.java, NodeDeserializer())
        .registerTypeAdapter(Component::class.java, ComponentDeserializer())
        .create().fromJson(source, listType)
}

fun readSettings(source : String) : SceneProfile =
    Gson().fromJson(source, SceneProfile::class.java)

class NodeDeserializer : JsonDeserializer<Node> {

    override fun deserialize(json : JsonElement, typeOfT : Type, context : JsonDeserializationContext) : Node {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type").asString

        val kClass = GameContext.current().engine.nodeClasses.find { it.simpleName == type }

        val node = context.deserialize<Node>(json, kClass?.java)

        node.children.forEach {
            it.parent = node
        }

        return node
    }

}

class ComponentDeserializer : JsonDeserializer<Component> {

    override fun deserialize(json : JsonElement, typeOfT : Type, context : JsonDeserializationContext) : Component {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type").asString

        val kClass = GameContext.current().engine.componentClasses.find { it.simpleName == type }

        return context.deserialize(json, kClass?.java)
    }

}