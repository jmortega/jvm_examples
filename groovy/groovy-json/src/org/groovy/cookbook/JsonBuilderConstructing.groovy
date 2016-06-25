package org.groovy.cookbook

import groovy.json.JsonBuilder

def chart = [
  items: [
    type: 'chart',
    height: 200,
    width: 300,
    axes: [
      {
        type "Time"
        fields (["x"])
        position "left"
        title "Time"
      },
      {
        type "Numeric"
        fields ( [ "y" ] )
        position  "bottom"
        title "Profit in EUR"
      }
    ]
  ]
]


def builder = new JsonBuilder(chart)

println builder.toPrettyString()

