@Grab('org.jyaml:jyaml:1.3')

package org.groovy.cookbook


import groovy.json.JsonSlurper
import groovy.transform.TupleConstructor
import org.ho.yaml.Yaml

def jsonConfig = '''{
   "configuration":{
      "database":[
         {
            "name":"database1",
            "host":"10.20.30.40",
            "port":"4930",
            "user":"user-alpha",
            "password":"secret",
            "pool-initial-size":"10",
            "pool-max-size":"10"
         },
         {
            "name":"database2",
            "host":"1.2.3.4",
            "port":"4930",
            "user":"user-alpha",
            "password":"secret",
            "pool-initial-size":"10",
            "pool-max-size":"10"
         }
      ]
   }
}'''

@TupleConstructor class Database {
  String name
  String host
  Integer port
  String user
  String password
  Integer initPool
  Integer maxPool

}

class AppConfig {

  def databases = []

  AppConfig(conf) {
    if (conf)
      conf.configuration.database.each {
        databases << new Database(it.name, it.host,
                                  it.port.toInteger(),
                                  it.user, it.password,
                                  it.'pool-initial-size'.toInteger(),
                                  it.'pool-max-size'.toInteger())}
  }

  def getDatabaseConfig = { name -> databases.find {it.name == name} }

}


def conf = new JsonSlurper().parseText(jsonConfig)
def dbConfig = new AppConfig(conf)

assert '1.2.3.4' == dbConfig.getDatabaseConfig('database2').host

class Staff {
    def firstname, lastname, position
}

yamlData = '''
firstname: Mike
lastname: Ross
position: CFO
'''


Staff s = Yaml.loadType(yamlData, Staff)

assert 'Mike' == s.firstname