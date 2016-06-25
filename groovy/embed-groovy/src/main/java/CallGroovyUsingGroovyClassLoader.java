import groovy.lang.GroovyClassLoader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.codehaus.groovy.control.CompilationFailedException;

public class CallGroovyUsingGroovyClassLoader {

  public static void main(String[] args) throws InstantiationException,
      IllegalAccessException, CompilationFailedException, IOException,
      IllegalArgumentException, SecurityException, InvocationTargetException,
      NoSuchMethodException, InterruptedException {

    // Create class loader.
    GroovyClassLoader gcl = new GroovyClassLoader();

    Class<?> gClass = gcl.parseClass(new File("script.groovy"));
    Object object = gClass.newInstance();

    System.out.println(gClass.getDeclaredMethod("getName", new Class[] {})
        .invoke(object, new Object[] {}));

    @SuppressWarnings("unchecked")
    Class<? extends Runnable> runnableClass = gcl.parseClass(new File("TimePrinter.groovy"));
    new Thread(runnableClass.newInstance()).start();
    
    Thread.sleep(10000);

  }

}
