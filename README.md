# GebesScript
 Simple Script Language with Kotlin as Interpreter
 
 ## HelloWorld Example
 There a ton of examples in the ./scripts/ folder you can look at. Here a basic one
 ```
 main
  println
   Hello World!
 ```

 ## Execute a script
 Execute the jar, and it will look up for .gebes files in a scripts folder (which will be automatically created).
 It's essential that you execute the .jar in the console/terminal because there is no GUI
 
 ### Launch arguments
 If the argument is the path, then it will execute this script in chronological order.
 If you don't want the "Executing script..." message, then add `--no-prefix`.  
 
 Example:
 ```
    java -jar GebesScript-1.x.jar ./scripts/hello.gebes --no-prefix
 ``` 

 If there are no arguments, it will then redirect you to the selection screen.
 
 ## What did I learn?
 * Kotlin
 * How to write an interpreter
