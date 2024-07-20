package com.assessment.inc.services.impl;

import com.assessment.inc.entites.User;
import com.assessment.inc.exceptions.ResourceNotFoundException;
import com.assessment.inc.respositories.UserRepository;
import com.assessment.inc.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository customerRepository;

    @Override
    @Transactional
    public List<User> saveCustomers(List<User> users) {
        List<User> saveUsers = new ArrayList<>();
        for (User user : users) {
            User savedUser = customerRepository.save(user);
            saveUsers.add(savedUser);
        }
        return saveUsers;
    }
    @Override
    public List<User> getAllCustomers() {
        return customerRepository.findAll();

    }

    @Override
    public User getCustomer(String userId) {
        return customerRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given userid  not found  for :"+userId));


    }


    @Override
    public void removeUser(String userId) {
        customerRepository.deleteById(userId);

    }

}
