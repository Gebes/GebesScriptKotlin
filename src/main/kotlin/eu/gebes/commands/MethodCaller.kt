package eu.gebes.commands

import eu.gebes.Command
import eu.gebes.script.GebesScript

class MethodCaller : Command() {
    override val name: String = "call"

    override val description = """
        Call multiple functions once or multiple times
    """.trimIndent()

    override val examples = arrayOf(
        """
        func
         println
          Func called
        
        # prints "Func called" once
        main
         call once
          func
    """.trimIndent(), """
        func
         println
          Func called
        
        # prints "Func called" once
        main
         call and repeat once
          func
    """.trimIndent(),"""
        func
         println
          Func called
        
        # prints "Func called" forever
        main
         call forever
          func
    """.trimIndent(),"""
        func
         println
          Func called
        
        # prints "Func called" forever
        main
         call and repeat forever
          func
    """.trimIndent(),"""
        func
         println
          Func called
        
        # prints "Func called" 3 times
        main
         call 3 times
          func
    """.trimIndent())

    override fun execute(label: String, parameter: String?, args: List<String>, gebesScript: GebesScript): String? {

        var count = 0

        if (parameter == null || parameter == "and repeat once" || parameter == "once") {
            count = 1
        } else if (parameter == "and repeat forever" || parameter == "forever") {
            count = -1
        } else {

            var param = parameter

            if(param.startsWith("and repeat "))
                param = param.substring("and repeat ".length, param.length)

            if(param.endsWith(" times"))
                param = param.substring(0, param.length-" times".length)


            count = param.toIntOrNull()
                ?: return "Invalid parameter for Method Caller: $parameter"
        }

        while (count != 0) {
            args.forEach { arg: String -> gebesScript.invokeMethod(arg) }

            if (count >= 0)
                count--
        }

        return null

    }

}

