package com.assessment.inc.controllers;

import com.assessment.inc.entites.User;
import com.assessment.inc.services.UserService;
import com.assessment.inc.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotBlank;
import java.util.List;


@RestController
    @RequestMapping("/api/v1/users")
    public class CustomerController {

        @Autowired
        private UserService userService;

        private Logger logger = LoggerFactory.getLogger(CustomerController.class);

        @RequestMapping("/home")
        public String homePage() {
            return "Welcome to Cloude Bee!!! ";
        }

    @Validated
    @PostMapping("/create")
    public ResponseEntity<?> addCustomers(@Valid @RequestBody List<User> users) {
        try {
            List<User> userAdded = userService.saveCustomers(users);
            logger.info("Added {} users", users.size());
            return ResponseEntity.status(HttpStatus.CREATED).body(userAdded);
        } catch (ValidationException e) {
            logger.error("Validation error while adding customers", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Constants.VALIDATE_ERROR);
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation error while adding customers", e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Constants.DATABASE_ERROR);
        } catch (Exception e) {
            logger.error("Unexpected error while adding customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.UNEXPECTED_ERROR);
        }
    }

    /* An API that shows the details of the receipt for the user */
    @GetMapping("/{userId}")
    public User getReceiptDetails(@PathVariable String userId) {
        logger.info("Fetching receipt details for user ID: {}", userId);
        return userService.getCustomer(userId);
    }

    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable @NotBlank(message = Constants.USER_ID_NOT_BLANK) String userId) {
        try {
            userService.removeUser(userId);
            logger.info("User with ID {} removed successfully", userId);
            return ResponseEntity.ok(String.format(Constants.USER_REMOVED_SUCCESS, userId));
        } catch (Exception e) {
            logger.error("Failed to remove user with ID {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Constants.USER_FAILED_TO_REMOVE);
        }
    }
}