package org.groovy.cookbook.dataflow

import groovyx.gpars.dataflow.DataflowVariable
import static groovyx.gpars.dataflow.Dataflow.task



class CriminalServiceWithDataflow {

  def baseUrl

  public CriminalServiceWithDataflow(String url) {
    baseUrl =  url
  }

  List getData(List countries) {
    List aggregatedJson = []
    countries.each() {
      aggregatedJson << fetchData(it)
    }
    return aggregatedJson.collect {it.val}
  }



  def fetchData(String country) {
    println "fetching data for ${country}"
    def jsonResponse = new DataflowVariable()
    task {

      try {
        "${baseUrl}/${country}".toURL().openConnection().with {
          connectTimeout = 3000
          if( responseCode == 200  ) {
            jsonResponse << inputStream.text
          } else {
            jsonResponse << new RuntimeException("Invalid Response Code from Http GET: {responseCode}") 
          }
          disconnect()
        }
      } catch( e ) { jsonResponse <<  e }
    }



      //jsonResponse << "${baseUrl}/${country}".toURL().text
    
    return jsonResponse

  }

}

