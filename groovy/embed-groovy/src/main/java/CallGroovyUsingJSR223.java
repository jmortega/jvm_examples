import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class CallGroovyUsingJSR223 {

  public static void main(String[] args) throws ScriptException {

    ScriptEngineManager manager = new ScriptEngineManager();
    ScriptEngine engine = manager.getEngineByName("groovy");

    // Expression evaluation.
    System.out.println(engine.eval(" (4/3) * Math.PI * 6370 // Earth volume in cubic killometeres "));

    engine.put("name", "Andrew");
    engine.eval("println \"My name is ${name}\"");

  }

}
