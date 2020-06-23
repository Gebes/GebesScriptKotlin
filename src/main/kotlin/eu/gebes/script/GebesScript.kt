package eu.gebes.script

import eu.gebes.commands.CommandManager
import eu.gebes.commands.Printer
import java.util.*
import eu.gebes.utils.*
import kotlin.NoSuchElementException

class GebesScript(scriptFile: ScriptFile) {

    val commandManager: CommandManager = CommandManager()
    var printer = Printer(0)


    private var methods: LinkedList<ScriptMethod> = LinkedList()


    fun methodByName(name: String): ScriptMethod? =
        methods.stream().filter { t: ScriptMethod -> t.name == name }.findFirst().orElse(null);

    init {

        for (line in scriptFile.lines) {

            val intent = getIntend(line)


            if (intent == 0 && line.isNotEmpty()) {

                val name = stripIntend(line);

                if (methodByName(name) != null)
                    throw ScriptParseException("Method with name $name already exists")

                methods.add(ScriptMethod(name, this))

            } else if (intent == 1) {

                val commandName = stripIntend(line)

                val s = commandName.split(" ".toRegex(), 2)

                methods.last.addCommand(
                    ScriptCommand(
                        s[0],
                        if (s.size == 2) s[1] else null
                    )
                )

            } else {

                val argument = stripIntend(line)

                try {
                    methods.last.lastCommand().addArgument(argument)
                } catch (e: NoSuchElementException) {
                }
            }

        }

    }




    fun invokeMethod(name: String) {
        val method = methodByName(name)
            ?: throw ScriptRuntimeException("The method with the name $name does not exist")


        method.execute();
    }


}

class ScriptMethod(val name: String, val gebesScript: GebesScript) {


    private val commands: LinkedList<ScriptCommand> = LinkedList()

    fun execute() {
        commands.forEach { scriptCommand: ScriptCommand ->
            val command = gebesScript.commandManager.getCommandByName(scriptCommand.name)
                ?: throw ScriptRuntimeException("The command with the name ${scriptCommand.name} does not exist")

            command.execute(
                scriptCommand.name, scriptCommand.parameter,
                scriptCommand.arguments, gebesScript
            )

        }
    }

    fun addCommand(scriptCommand: ScriptCommand) = commands.add(scriptCommand)

    fun lastCommand(): ScriptCommand = commands.last

}

class ScriptCommand(val name: String, val parameter: String?) {

    val arguments: LinkedList<String> = LinkedList()


    fun addArgument(name: String) = arguments.add(name)

}

class ScriptParseException(name: String) : Exception(name)
class ScriptRuntimeException(name: String) : Exception(name)