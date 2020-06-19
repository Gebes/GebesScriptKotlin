package eu.gebes.commands

import eu.gebes.GebesScript
import eu.gebes.ScriptRuntimeException
import java.util.*

class methodCaller : Command() {
    override fun name(): String = "call"

    override fun execute(label: String, parameter: String?, args: List<String>, gebesScript: GebesScript) {

        var count = 0;

        if (parameter == null || parameter == "and repeat once" || parameter == "once") {
            count = 1
        } else if (parameter == "and repeat forever" || parameter == "forever") {
            count = -1
        } else {

            var param = parameter;

            if(param.startsWith("and repeat "))
                param = param.substring("and repeat ".length, param.length)

            count = param.toIntOrNull()
                ?: throw ScriptRuntimeException("Invalid parameter for Method Caller: $parameter")
        }

        while (count != 0) {
            args.forEach { arg: String -> gebesScript.invokeMethod(arg) }

            if (count >= 0)
                count--
        }

    }

}

