package com.easybytes.controller;


import com.easybytes.Modal.Accounts;
import com.easybytes.Modal.Customer;
import com.easybytes.Repository.AccountsRepository;
import com.easybytes.Repository.CustomerRepository;
import com.easybytes.constants.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountController {

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(ApiConstants.ACCOUNT_DETAILS)
    public Accounts getAccountDetails(@RequestParam String email) {

        List<Customer> customerList=customerRepository.findByEmail(email);
        if(customerList!=null && !customerList.isEmpty()){
            Accounts accounts = accountsRepository.findByCustomerId(customerList.get(0).getId());
            if (accounts != null ) {
                return accounts;
            }
        }
        return null;

    }

}
