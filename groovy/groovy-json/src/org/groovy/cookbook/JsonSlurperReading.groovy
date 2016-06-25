package org.groovy.cookbook

import groovy.json.JsonSlurper

def ui = new JsonSlurper().parse(new FileReader('ui.json'))


ui.items.each { println it.type }

println ui.items[0].axes.find { it.fields.contains('y') }.title

println ui.getClass()
println ui.items.getClass()

