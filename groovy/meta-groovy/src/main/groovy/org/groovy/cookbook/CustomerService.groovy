package org.groovy.cookbook

class CustomerService {

  CustomerDao dao

  void setCustomerDao(CustomerDao dao) { 
    this.dao = dao
  }

  Customer getCustomer(Long id) {

    dao.getCustomerById(id)
  }
}