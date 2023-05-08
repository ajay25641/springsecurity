package com.easybytes.controller;


import com.easybytes.Modal.Cards;
import com.easybytes.Modal.Customer;
import com.easybytes.Repository.CardsRepository;
import com.easybytes.Repository.CustomerRepository;
import com.easybytes.constants.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardsController {

    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(ApiConstants.CARD_DETAILS)
    public List<Cards> getCardDetails(@RequestParam String email) {

        List<Customer>customerList=customerRepository.findByEmail(email);

        if(customerList!=null && !customerList.isEmpty()){
            List<Cards> cards = cardsRepository.findByCustomerId(customerList.get(0).getId());
            if(cards!=null){
                return cards;
            }
        }
        return null;
    }

}
