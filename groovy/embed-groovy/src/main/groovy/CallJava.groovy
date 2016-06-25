
// Variant 1

import java.io.File;

1.times {
  File currentDir = new File(".");
  System.out.println("Current directory: " + currentDir.getAbsolutePath());

  File[] files = currentDir.listFiles();
  for(File file: files) {
    System.out.println(file.getName());
  }
}



// Variant 2
1.times {
  def currentDir = new File(".")
  println "Current directory $currentDir.absolutePath"
  def files = currentDir.listFiles()
  for(def file: files) {
    println file.name
  }
}
