import java.io.*

class DirListing {

  static void main(String[] args) throws IOException {
    String path
    if (args.length > 0) {
      path = args[0]
    } else {
      path = '.'
    }
    File directory = new File(path)
    File[] files = directory.listFiles()
    System.out.println(" Directory of ${directory.canonicalFile.absolutePath}")
    for (int i = 0; i < files.length; i++) {
      if (files[i].isFile()) {
        File file = files[i]
        System.out.println(file.name)
      }
    }
  }

}