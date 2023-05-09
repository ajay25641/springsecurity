package com.easybytes.controller;

import com.easybytes.Modal.Customer;
import com.easybytes.Modal.Loans;
import com.easybytes.Repository.CustomerRepository;
import com.easybytes.Repository.LoanRepository;

import com.easybytes.constants.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LoansController {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;



    @GetMapping(ApiConstants.LOAN_DETAILS)
    public List<Loans> getLoanDetails(@RequestParam String email) {

        List<Customer>customerList=customerRepository.findByEmail(email);

        if(customerList!=null && !customerList.isEmpty()){
            List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(customerList.get(0).getId());
            if (loans != null ) {
                return loans;
            }
        }
        return null;
    }

}
