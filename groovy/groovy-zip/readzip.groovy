
def dumpZipContent(File zipFIle) {

  def zf = new java.util.zip.ZipFile(zipFIle)

  zf.entries().findAll { !it.directory }.each {
    println it.name
    println zf.getInputStream(it).text
  }

}

dumpZipContent(new File('archive.zip'))

def ant = new AntBuilder()
ant.unzip (src: 'zipped.zip', dest: '.')