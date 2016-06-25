package org.groovy.cookbook

import groovy.json.JsonSlurper

def RTKEY = 'k56n29ntygst8yppswwaj7gj'
 
def it = '''{ "name_popular": [{ "id":"nm0000233", "title":"", "name":"Quentin Tarantino","description":"Actor, Pulp Fiction"}] }'''

def ui = new JsonSlurper().parseText(it)

ui.name_popular.each { println it}

def personId = ui.name_popular[0].id

def movies = null