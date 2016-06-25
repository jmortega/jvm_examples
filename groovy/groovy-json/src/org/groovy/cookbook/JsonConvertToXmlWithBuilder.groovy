package org.groovy.cookbook

import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder

def reader = new File('ui.json').newReader()
def ui = new JsonSlurper().parse(reader)

def writer = new StringWriter()
def xml = new MarkupBuilder(writer)

xml.items {
  ui.items.each { widget -> 
    item(type: widget.type, 
         height: widget.height, 
         width: widget.width) {
      axes {
        widget.axes.each { widget_axis ->
          axis(type: widget_axis.type, 
              name: widget_axis.title)
        }
      }
    }
  }
}

println writer.toString()
