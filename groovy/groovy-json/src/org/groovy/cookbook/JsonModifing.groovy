package org.groovy.cookbook

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

def ui = new JsonSlurper().parse(new FileReader('ui.json'))


ui.items[0].type = 'panel'
ui.items[0].remove('axes')
ui.items[0].remove('series')


println JsonOutput.prettyPrint(JsonOutput.toJson(ui))