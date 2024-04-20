import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import org.openartifact.artifact.core.Engine
import org.openartifact.artifact.core.event.register
import org.slf4j.LoggerFactory
import java.io.File

private val logger = LoggerFactory.getLogger("EntryPoint")

fun main(args : Array<String>) {
    StartCommand().main(args)
}

class StartCommand : CliktCommand() {

    val projectPath : String? by option().help("The path to the project directory")

    override fun run() {
        val projectFile = File(projectPath)

        logger.debug("Project: ${projectFile.absolutePath} exists?: ${projectFile.exists()}")

        require(projectFile.isDirectory) { "Project can only be a directory." }

        logger.info(when (projectFile.exists()) {
            true -> "Project found."
            false -> "Project not found!"
        })

        logger.info("Initializing Artifact")

        Engine
        Engine.init(projectFile)
    }

}