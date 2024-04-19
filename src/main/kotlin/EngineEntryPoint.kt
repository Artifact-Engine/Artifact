import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import org.openartifact.core.event.notify
import org.slf4j.LoggerFactory
import java.io.File

private val logger = LoggerFactory.getLogger("EntryPoint")

fun main(args : Array<String>) {
    StartCommand().main(args)
}

class StartCommand : CliktCommand() {

    val projectPath : String? by option().help("The path to the project")

    override fun run() {
        val projectFile = File(projectPath)

        logger.debug("Project: ${projectFile.absolutePath} exists?: ${projectFile.exists()}")
        logger.info("Initializing Artifact")


    }

}