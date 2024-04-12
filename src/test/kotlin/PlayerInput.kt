import org.lwjgl.glfw.GLFW
import org.openartifact.input.isKeyPressed
import org.openartifact.node.Node
import org.openartifact.scripting.Scriptable

class PlayerInput: Scriptable<Node>() {

    override fun update() {
        if (isKeyPressed(GLFW.GLFW_KEY_A)) {
            println("A")
        }
    }

}