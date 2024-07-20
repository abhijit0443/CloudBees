package com.assessment.inc.controllers;

import com.assessment.inc.entites.User;
import com.assessment.inc.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;


@RestController
    @RequestMapping("/api/v1/")
    public class CustomerController {

        @Autowired
        private UserService userService;

        private Logger logger = LoggerFactory.getLogger(CustomerController.class);

        @RequestMapping("/home")
        public String homePage() {
            return "Welcome to Cloude Bee!!! ";
        }



    @PostMapping("/createUsers")
    public ResponseEntity<?> addCustomers(@RequestBody List<User> users) {
        try {
            List<User> userAdded = userService.saveCustomers(users);
            return ResponseEntity.status(HttpStatus.CREATED).body(userAdded);
        } catch (ValidationException e) {
            // Handle validation errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Validation error: ");
        } catch (DataIntegrityViolationException e) {
            // Handle database constraints violation errors
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Database error: " );
        } catch (Exception e) {
            // Handle other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error: " );
        }
    }
    /*An API that shows the details of the receipt for the user*/
    @GetMapping("/customers/{userId}")
    public User getReceiptDetails(@PathVariable String userId) {
        return userService.getCustomer(userId);
    }


    @DeleteMapping("/customers/remove/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable String userId) {
        try {
            userService.removeUser(userId);
            return ResponseEntity.ok("User with ID " + userId + " removed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to remove user: ");
        }
    }
    }
