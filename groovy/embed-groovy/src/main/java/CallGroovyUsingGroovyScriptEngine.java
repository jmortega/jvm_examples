import groovy.lang.Binding;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;

import java.io.IOException;

public class CallGroovyUsingGroovyScriptEngine {

  public static void main(String[] args) throws IOException, ResourceException, ScriptException {

    GroovyScriptEngine gse = new GroovyScriptEngine(".");
    Binding binding = new Binding();
    binding.setVariable("input", "world");
    gse.run("hello.groovy", binding);
    System.out.println(binding.getVariable("output"));

  }

}
