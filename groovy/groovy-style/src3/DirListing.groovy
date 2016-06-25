
def path = args.size() > 0 ? args[0] : '.'
def directory = new File(path)
println "Directory of ${directory.canonicalFile.absolutePath}"
directory.eachFile { File file ->
  if (file.isFile()) {
    println file.name
  }
}
