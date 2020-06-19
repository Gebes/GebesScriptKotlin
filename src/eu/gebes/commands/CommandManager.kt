package eu.gebes.commands

import eu.gebes.GebesScript
import java.util.*


class CommandManager {

    private var commands: LinkedList<Command> = LinkedList()

    fun registerCommand(command: Command) = commands.add(command)

    fun getCommandByName(name: String): Command? =
        commands.stream().filter { t: Command -> t.name() == name}.findFirst().orElse(null)

    init {
        registerCommand(print())
        registerCommand(println())
        registerCommand(methodCaller())
        registerCommand(forever())
    }

}

abstract class Command {

    abstract fun name(): String

    abstract fun execute(label: String, args: List<String>, gebesScript: GebesScript)

}