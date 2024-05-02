package org.openartifact.artifact.game.scene

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import org.openartifact.artifact.core.Application
import org.openartifact.artifact.game.Component
import org.openartifact.artifact.game.Node
import org.openartifact.artifact.utils.requireFile
import java.io.File
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

fun readScene(sceneFile : File) : Scene {
    val nodesFile = requireFile(sceneFile, "nodes.json")
    val settingsFile = requireFile(sceneFile, "settings.json")

    val scene = Scene(readSettings(settingsFile.readText()), readNodes(nodesFile.readText()))

    return scene
}

fun writeNodes(scene : Scene) : String = Gson().toJson(scene.nodes)

fun readNodes(source : String) : List<Node> {
    val listType : Type = object : TypeToken<List<Node>>() {}.type
    return GsonBuilder()
        .registerTypeAdapter(Node::class.java, NodeDeserializer())
        .registerTypeAdapter(Component::class.java, ComponentDeserializer())
        .create().fromJson(source, listType)
}

fun readSettings(source : String) : SceneProfile = Gson().fromJson(source, SceneProfile::class.java)

fun validateJsonFields(jsonObject: JsonObject, typeName: String, clazz: KClass<*>) {
    clazz.primaryConstructor?.parameters?.forEach {
        require(jsonObject.has(it.name)) { "Missing required field '${it.name}' in JSON for component type $typeName" }
    }
}

class NodeDeserializer : JsonDeserializer<Node> {
    override fun deserialize(json : JsonElement, typeOfT : Type, context : JsonDeserializationContext) : Node {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type").asString

        val kClass = Application.current().engine.nodeClasses.find { it.simpleName == type }
            ?: throw IllegalArgumentException("Node class not found for type $type")

        validateJsonFields(jsonObject, type, kClass)

        val node = context.deserialize<Node>(json, kClass.java)

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

        val kClass = Application.current().engine.componentClasses.find { it.simpleName == type }
            ?: throw IllegalArgumentException("Component class not found for type $type")

        validateJsonFields(jsonObject, type, kClass)

        return context.deserialize(json, kClass.java)
    }
}
