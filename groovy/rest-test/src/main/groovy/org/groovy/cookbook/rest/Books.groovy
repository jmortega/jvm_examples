package org.groovy.cookbook.rest

import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

class Books {

	static books = new JsonSlurper().parseText('''[
		{
			"id"   : "1",
			"title": "Cybernetic Revolutionaries: Technology and Politics in Allende's Chile",
        	"author": "Eden Medina",
        	"date": "04-15-2013"
		},
		{
			"id"   : "2",
	        "title": "Close to the Machine: Technophilia and its Discontents",
	        "author": "Ellen Ullman",
	        "date": "01-15-2013"
      	},
	    {
			"id"   : "3",
	        "title": "The Signal and the Noise",
	        "author": "Nate Silver",
	        "date": "12-15-2012"
      	},
        {
			"id"   : "4",
	        "title": "Mr. Penumbra's 24-Hour Book Shop",
	        "author": "Robin Sloan",
	        "date": "10-15-2012"
		},
		{
			"id"   : "5",
	        "title": "Hackers: Heroes of the Computer Revolution",
	        "author": "Steven Levy",
	        "date": "07-15-2012"
		},
		{
			"id"   : "6",
	        "title": "How to Do Things With Videogames",
	        "author": "Ian Bogost",
	        "date": "03-15-2012"
        },
        {
			"id"   : "7",
	        "title": "Fordlandia: The Rise and Fall of Henry Ford's Forgotten Jungle City",
	        "author": "Greg Grandin",
	        "date": "01-15-2012"
        },
      	{
			"id"   : "8",
	        "title": "A Pattern Language",
	        "author": "Christopher Alexander",
	        "date": "10-15-2011"
      	}]''')


	static get(){
		JsonBuilder builder =  new JsonBuilder()
		builder.content = books
		builder.toPrettyString()
	}

	static get(id){
		JsonBuilder builder =  new JsonBuilder()
		builder.content = books.find{it.id==id}
		builder.toPrettyString()
	}

	static post(Book b){
		def max = books.max {it.id}.id
		b.id = Integer.parseInt(max)+1
		books.add(b)
		JsonBuilder builder =  new JsonBuilder()
		builder.content = b
		builder.toPrettyString()
	}

	static delete(String id){
		books.removeAll{it.id==id}
	}
}
