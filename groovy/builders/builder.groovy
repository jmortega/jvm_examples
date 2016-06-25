import groovy.transform.*
import java.util.Random

interface DataPopulationStrategy {

   def execute()
}


@Canonical
class ShoppingCart {
    List<Book> items = []
    User user
    Address shippingData
}

@Canonical
class Book {
   Long id
   String title
   BigDecimal price
}

@Canonical
class User {
   Long id
   String firstName
   String lastName
   Address address
}

@Canonical
class Address {
   String street
   String city
   String country
}

class ECommerceTestDataBuilder {

   ShoppingCart shoppingCart
   def books = []

   ShoppingCart build(closure) {
      shoppingCart = new ShoppingCart()
      closure.delegate = this
      closure()

      shoppingCart.items = books
      shoppingCart
   }

   void items (int quantity, closure) {
      closure.delegate = this
      quantity.times {
         books << new Book()
         closure()
      }
   }

   def methodMissing(String name, args) {
      Book book = books.last()

      if (book.hasProperty(name)) {
         def dataStrategy = isDataStrategy (args)
         book.@"$name" = (dataStrategy)?dataStrategy.execute():args[0]
      } else throw new MissingMethodException(name, ECommerceTestDataBuilder, args)
   }


   def isDataStrategy(strategyData) {
      try {
         def strategyClass
         if (strategyData.length == 1) {
            strategyClass = strategyData[0].newInstance()
         } else {
            strategyClass = strategyData[0].newInstance(*strategyData[1,-1])
         }
         if (strategyClass instanceof DataPopulationStrategy) return strategyClass
      } catch (Exception e) {
         null
      }
   }

}



class RANDOM_TITLE implements DataPopulationStrategy {

   def titleCache = []

   def ignoredTitleWords = ["Page", "Sort", "Next"]

   void getRandomBookTitles() {

      def slurper = new XmlSlurper()
      slurper.setFeature('http://apache.org/xml/features/nonvalidating/load-external-dtd', false)
      def htmlParser = slurper.parse('http://m.gutenberg.org/ebooks/search.mobile/?sort_order=random')
      htmlParser.'**'.findAll{ it.@class == 'title'}.each {
         if (it.text().tokenize().disjoint(ignoredTitleWords)) {
            titleCache << it.text()
         }
      }

   }

   def execute() {
      if (titleCache.size==0) {
         getRandomBookTitles()
      }
      titleCache.pop()
   }

}

class RANDOM_ID implements DataPopulationStrategy {
   Long minVal
   Long maxVal

   //RANDOM_ID(){}
   RANDOM_ID (min, max) {
      minVal = min
      maxVal = max
   }

   def execute() {
      minVal + (long)(new Random().nextDouble()*(maxVal - minVal))
   }
}

def shoppingCart = new ECommerceTestDataBuilder().build {
   items(5) {
      title RANDOM_TITLE
      id RANDOM_ID, 100, 200000
      price 100
   }
}

assert shoppingCart.items.size == 5
shoppingCart.items.each {
   assert it.price == 100
   assert it.id > 100 && it.id < 200000
}
