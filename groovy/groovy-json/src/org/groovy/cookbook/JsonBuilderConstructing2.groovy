package org.groovy.cookbook

import groovy.json.JsonBuilder

def builder = new JsonBuilder()

builder.items { 
  chart {
    height 200
    width 300
    axes([
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
    ]) 
  }  
}
  

//println builder.toPrettyString()

builder.customer {
  name 'John'
  lastName 'Appleseed'
  address {
    streetName 'Gordon street'
    city 'Philadelphia'
    houseNumber 20
  }

}

println builder.toPrettyString()
