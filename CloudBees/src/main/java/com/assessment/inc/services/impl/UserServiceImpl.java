package com.assessment.inc.services.impl;

import com.assessment.inc.entites.Customer;
import com.assessment.inc.exceptions.ResourceNotFoundException;
import com.assessment.inc.respositories.UserRepository;
import com.assessment.inc.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements CustomerService {

    @Autowired
    private UserRepository customerRepository;

    @Override
    @Transactional
    public List<Customer> saveCustomers(List<Customer> users) {
        List<Customer> saveCustomers = new ArrayList<>();
        for (Customer user : users) {
            Customer savedUser = customerRepository.save(user);
            saveCustomers.add(savedUser);
        }
        return saveCustomers;
    }
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();

    }

    @Override
    public Customer getCustomer(String userId) {
        return customerRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given userid  not found  for :"+userId));


    }


    @Override
    public void removeUser(String userId) {
        customerRepository.deleteById(userId);

    }

}
