package com.easybytes.controller;


import com.easybytes.Modal.AccountTransactions;
import com.easybytes.Modal.Customer;
import com.easybytes.Repository.AccountTransactionsRepository;
import com.easybytes.Repository.CustomerRepository;
import com.easybytes.constants.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BalanceController {

    @Autowired
    private AccountTransactionsRepository accountTransactionsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(ApiConstants.BALANCE_DETAILS)
    public List<AccountTransactions> getBalanceDetails(@RequestParam String email) {

        List<Customer>customerList=customerRepository.findByEmail(email);

        if(customerList!=null && !customerList.isEmpty()){
            List<AccountTransactions> accountTransactions = accountTransactionsRepository.
                    findByCustomerIdOrderByTransactionDtDesc(customerList.get(0).getId());
            if(accountTransactions!=null){
                return accountTransactions;
            }
        }
        return null;

    }
}
