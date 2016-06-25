package org.groovy.cookbook

import org.junit.*

class FileDownloaderTest {

  def static final download_base_dir = '/tmp'
  def static final templ_url = 'https://androidnetworktester.googlecode.com/files/1mb.txt?cache='
  def downloader = new FileDownloader()
  Map files

  @Before void before() {
    files = [:]
    (1..5).each {
      files.put("${templ_url}1.${it}","${download_base_dir}/${it}MyFile.txt")
    }
  }

  @Test
  void testSerialDownload(){

    long start = System.currentTimeMillis()

    files.each{ k,v ->
      new File(v) << k.toURL().text
    }
    println "TIME NOPAR: ${(System.currentTimeMillis() - start)}"

  }

  @Test
  void testParallelDownload(){

    long start = System.currentTimeMillis()
    downloader.download(files, 0)

    println "TIMEPAR: ${(System.currentTimeMillis() - start)}"

  }

  @Test
  void testParallelDownloadWithMaxConcurrent(){


    long start = System.currentTimeMillis()
    downloader.download(files, 3)

    println "TIMEPAR MAX 3: ${(System.currentTimeMillis() - start)}"

  }

}