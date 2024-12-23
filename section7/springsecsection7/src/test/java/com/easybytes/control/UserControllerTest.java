package com.easybytes.control;

import com.easybytes.model.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private Customer customer = new Customer();

    @Test
    void registerUser(){

        customer.setEmail("happy@example.com");
        customer.setPwd("EazyBytes@12345");
        customer.setRole("read");

        UserController userController = new UserController(null, null);

        String response = userController.registerUser(customer).toString();

        assertEquals("User registered successfully", response);
    }

}