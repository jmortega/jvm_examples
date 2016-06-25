
def dir = new File('./tmp')
def file = new File('./tmp/test.txt')

println 'Creating directories and files'
println new File(dir, 'dir01/dir11').mkdirs()
println new File(dir, 'dir01/dir12').mkdirs()
println new File(dir, 'dir02/dir21').mkdirs()
println new File(dir, 'dir02/dir22').mkdirs()
file.text = "test"


println 'Deleting directories and files'
println file.deleteDir()
println file.delete()
println dir.delete()
println dir.deleteDir()


File.metaClass.safeDelete = {
  if (exists()) {
    if (isDirectory()) {
      if (!deleteDir()) {
        throw new IOException("Unable to delete a directory: ${name}")
      }
    } else if (!delete()) {
      throw new IOException("Unable to delete a file: ${name}")
    }
  }
}


println 'Creating directories and files'
println new File(dir, 'dir01/dir11').mkdirs()
println new File(dir, 'dir01/dir12').mkdirs()
println new File(dir, 'dir02/dir21').mkdirs()
println new File(dir, 'dir02/dir22').mkdirs()
file.text = 'test'


file.safeDelete()
dir.safeDelete()
