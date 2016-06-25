import groovy.lang.GroovyShell;
import groovy.lang.Script;

import java.io.File;
import java.io.IOException;

import org.codehaus.groovy.control.CompilationFailedException;

public class CallGroovyUsingGroovyShell {

  public static void main(String[] args) throws CompilationFailedException,
      IOException {

    // Create shell object
    GroovyShell shell = new GroovyShell();

    double result = (Double) shell
        .evaluate(" (4/3) * Math.PI * 6370 // Earth volume in cubic killometeres ");
    System.out.println(result);

    shell.evaluate("name = 'Andrew'");
    shell.setVariable("name", "Andrew");

    shell.evaluate("println \"My name is ${name}\"");
    shell.evaluate("println \"My name is ${name}\"");

    shell.evaluate("name = name.toUpperCase()");
    
    System.out.println(shell.getVariable("name"));
    System.out.println(shell.getProperty("name"));

    shell.evaluate("println 'Hello from shell!'");
    System.out.println(shell.evaluate(" 1 + 2 "));

    shell.evaluate(new File("script.groovy"));

    // Calling internal script
    Script script = shell.parse(new File("script.groovy"));
    script.run();
    System.out.println(script.invokeMethod("getCurrentDate", null));
    System.out.println(script.getProperty("name"));

    // Calling internal script
    script = shell.parse("println 'Hello from internal script!'");
    script.run();

    script = shell.parse(new File("functions.groovy"));
    System.out.println(script.invokeMethod("year", null));

  }

}
