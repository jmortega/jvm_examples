package org.groovy.cookbook.rest

import static com.jayway.restassured.RestAssured.*
import static com.jayway.restassured.matcher.RestAssuredMatchers.*
import static org.hamcrest.Matchers.*
import static org.junit.Assert.*
import groovy.json.JsonBuilder

import org.groovy.cookbook.rest.Book
import org.groovy.cookbook.server.*
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

class RestTest {

	static server
	final static HOST = 'http://localhost:5050'

	@BeforeClass
	static void setUp(){
		server = App.init()
		server.startAndWait()
	}

	@AfterClass
	static void tearDown(){
		if(server.isRunning()){
			server.stop()
		}
	}

	@Test
	void testGetBooks(){
 		expect().body( 'author', hasItems('Ian Bogost','Greg Grandin')).when().get("${HOST}/api/books")
	}

	@Test
	void testGetBook(){
		 expect().body( 'author', is('Steven Levy')).when().get("${HOST}/api/books/5")
	}

	@Test
	void testPostBook(){
		 def book = new Book()
		 book.author = 'Haruki Murakami'
		 book.date = '2012-05-14'
		 book.title = 'Kafka on the shore'
		 JsonBuilder jb = new JsonBuilder()
		 jb.content = book
		 given().
		 	content(jb.toString()).
		 expect().body('id',is(9)).
		 when().post("${HOST}/api/books/new")
	}

	@Test
	void testDeleteBook(){
		 expect().statusCode(200).
		 when().delete("${HOST}/api/books/1")

		 expect().body( 'id', not(hasValue(1))).when().get("${HOST}/api/books")

	}
}