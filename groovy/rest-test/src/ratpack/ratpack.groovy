import static org.ratpackframework.groovy.RatpackScript.ratpack
import groovy.json.JsonSlurper
import org.groovy.cookbook.rest.Books
import org.groovy.cookbook.rest.Book

ratpack {
	routing {

		/*rest-assured tests*/
		get('api/books'){
			response.send('application/json',Books.get())
		}

		post('api/books/new'){

			def js = new JsonSlurper().parseText(request.text)
			def book = new Book()
			book.author = js.author
			book.title = js.title
			book.date = js.date
			response.send('application/json',Books.post(book))

		}

		handler('api/books/:id',['DELETE','PUT','GET']){

			def pathid =request.path.split('/').last()
			if (request.method.isDelete()) {
				Books.delete(pathid)
				response.status(200).send()
			} else if (request.method.isGet()) {
				response.send('application/json',Books.get(pathid))
			} else {
				response.status(500,'not implemented').send()
			}

		}
		assets 'public'
	}
}