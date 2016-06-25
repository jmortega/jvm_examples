package org.groovy.cookbook

import org.junit.*
import static org.junit.Assert.*
import org.groovy.cookbook.*
import java.text.NumberFormat

class TestMetaclass  {

    @Test
    void addMethod() {

      BigDecimal.metaClass.getInEuros = { ->
        def EXCHANGE_RATE = 0.763461
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US)
        nf.setCurrency(Currency.getInstance("EUR"))
        nf.format(delegate * EXCHANGE_RATE)
      }

      assert 1500.00.inEuros == 'EUR1,145.19'
    }



    @Test
    void addMethodUsingDsl() {

      BigDecimal.metaClass {

        getInEuros = {
          def EXCHANGE_RATE = 0.763461
          NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US)
          nf.setCurrency(Currency.getInstance("EUR"))
          nf.format(delegate * EXCHANGE_RATE)
        }
      }

      assert 1500.00.inEuros == 'EUR1,145.19'
    }

    @Test
    void addConstructor() {
      Customer.metaClass.constructor = { String name ->
       new Customer(name:name)
     }
      def c = new Customer("John")
      assert "John" == c.name
    }

    @Test
    void addStaticMethod() {
      Customer.metaClass.'static'.sayHello = {  ->
       "hello! I'm your customer"
      }
      assert  "hello! I'm your customer" == Customer.sayHello()
    }

    @Test
    void addProperty() {
      Customer.metaClass.gsm = null
      def c = new Customer()
      c.gsm = "123456"
      assert "123456" == c.gsm

    }

    @Test
    void mockMethod() {
      boolean daoCalled = false
      CustomerDao.metaClass.getCustomerById = { Long id ->
        daoCalled = true
        new Customer(name:'Yoda')
      }
      def cs = new CustomerService()
      def cDao = new CustomerDao()
      cs.setCustomerDao(cDao)
      def customer = cs.getCustomer(100L)
      assertTrue(daoCalled)
      assertEquals(customer.name, 'Yoda')
    }

    

}
