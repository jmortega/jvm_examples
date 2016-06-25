package org.groovy.cookbook

@groovy.transform.Canonical
class Person {
    long   id
    String firstName
    String lastName
}
 



def people = [ new Person( 23, 'John', 'Collingwood'       ), 
                 new Person( 24, 'Maya', 'Starr' ), 
                 new Person( 25, 'Uccio', 'Pomes'       )]

def json = new groovy.json.JsonBuilder()
 
json.people() {
    people.each { Person p -> person ( id     : p.id, 
                                          name : p.firstName, 
                                          lastName   : p.lastName )}
}

println json.toString()

def json2 = new groovy.json.JsonBuilder()

json2.content =  [people:people]

println json2.toString()
