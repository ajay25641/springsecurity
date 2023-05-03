package com.easybytes.controller;


import com.easybytes.Constants;
import com.easybytes.Modal.Customer;
import com.easybytes.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer){

        Customer savedCustomer=null;

        ResponseEntity response = null;

        try{
            customer.setRole(Constants.ROLE);
            savedCustomer=customerRepository.save(customer);
            if(savedCustomer.getId()>0){
                response=ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("User registered successfully");
            }
        }
        catch (Exception ex){
            response=ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Some exception occured due to "+ex.toString());
        }
        return response;

    }
}
