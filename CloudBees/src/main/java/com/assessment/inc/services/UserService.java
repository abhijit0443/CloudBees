package com.assessment.inc.services;

import com.assessment.inc.entites.User;


import java.util.List;

public interface UserService {
    List<User> saveCustomers(List<User> users);

    //get all user
    List<User> getAllCustomers();

    //get single user of given userId

    User getCustomer(String userId);

    void removeUser(String ticketId);
}
