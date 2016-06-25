package org.groovy.cookbook

import org.junit.*
import org.groovy.cookbook.*

class TestIntrospection {


  @Test void testIntrospection() {

    assert 'java.lang.String' == String.class.name
    assert 'org.groovy.cookbook.Author' == Author.class.name 
    Book book = new Book()
    book.title = "The Old Man and the Sea"
    book.year = 1952
    book.pages = 200
    Author a = new Author(name:'Ernest', lastName:'Hemingway')
    book.author = a
    
    //book.properties.each { println it}
    assert 7 == book.properties.size()

    assert book.metaClass.hasProperty(book,'pages')

    println '#### METHODS ####'
    println book.metaClass.methods.each { println it}

    println '#### PROPERTIES ####'
    println book.metaClass.properties.each { println it.name}

    assert book.respondsTo("getAmazonSalesPosition")
    assert book.respondsTo("attachReview", String)


  }


}