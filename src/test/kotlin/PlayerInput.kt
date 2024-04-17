import org.openartifact.scripting.Scriptable
import org.openartifact.scripting.event.events.KeyRepeatEvent
import org.openartifact.scripting.event.handler

class PlayerInput: Scriptable(TODO()) {

    init {
        handler<KeyRepeatEvent>({
            println(it.key)
        })
    }

    override fun update() {

    }

}