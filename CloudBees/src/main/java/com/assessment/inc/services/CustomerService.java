package com.assessment.inc.services;

import com.assessment.inc.entites.Customer;


import java.util.List;

public interface CustomerService {
    List<Customer> saveCustomers(List<Customer> users);

    //get all user
    List<Customer> getAllCustomers();

    //get single user of given userId

    Customer getCustomer(String userId);

    void removeUser(String ticketId);
}
