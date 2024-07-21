package com.assessment.inc.controllers;

import com.assessment.inc.controllers.CustomerController;
import com.assessment.inc.entites.User;
import com.assessment.inc.services.UserService;
import com.assessment.inc.util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomerController customerController;

    private List<User> mockUsers;

    @Before
    public void setUp() {
        mockUsers = new ArrayList<>();
        User user1=new User();
        user1.setUserId("1");
        user1.setFirstName("Abhijit");
        mockUsers.add(user1);
        User user2=new User();
        user2.setUserId("2");
        user2.setFirstName("Mandal");
        mockUsers.add(user2);
    }

    @Test
    public void testAddCustomers() {
        when(userService.saveCustomers(anyList())).thenReturn(mockUsers);
        ResponseEntity<?> responseEntity = customerController.addCustomers(mockUsers);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockUsers, responseEntity.getBody());
        verify(userService, times(1)).saveCustomers(mockUsers);
    }

    @Test
    public void testAddCustomersValidationException() {
        when(userService.saveCustomers(anyList())).thenThrow(new ValidationException("Validation error"));
        ResponseEntity<?> responseEntity = customerController.addCustomers(mockUsers);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(Constants.VALIDATE_ERROR, responseEntity.getBody());
        verify(userService, times(1)).saveCustomers(mockUsers);
    }

    @Test
    public void testAddCustomersDataIntegrityViolationException() {
        when(userService.saveCustomers(anyList())).thenThrow(new org.springframework.dao.DataIntegrityViolationException("Data integrity violation"));
          ResponseEntity<?> responseEntity = customerController.addCustomers(mockUsers);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals(Constants.DATABASE_ERROR, responseEntity.getBody());
        verify(userService, times(1)).saveCustomers(mockUsers);
    }

    @Test
    public void testAddCustomersUnexpectedException() {
        when(userService.saveCustomers(anyList())).thenThrow(new RuntimeException("Unexpected error"));
        ResponseEntity<?> responseEntity = customerController.addCustomers(mockUsers);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(Constants.UNEXPECTED_ERROR, responseEntity.getBody());
        verify(userService, times(1)).saveCustomers(mockUsers);
    }



    @Test
    public void testRemoveUser() {
        String userId = "1";
        ResponseEntity<?> responseEntity = customerController.removeUser(userId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(String.format(Constants.USER_REMOVED_SUCCESS, userId), responseEntity.getBody());
        verify(userService, times(1)).removeUser(userId);
    }
}
