package com.easybytes.control;

import com.easybytes.model.Customer;
import com.easybytes.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try{
            //encode the given password by Customer
            String hashedPassword = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashedPassword);
            //save the customer
            Customer savedCustomer = customerRepository.save(customer);
            if(savedCustomer == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Exception occured while registering");
            }else{
                return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An Exception occured while registering"+e.getMessage());
        }

    }
}
