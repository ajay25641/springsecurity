package com.easybytes.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardsController {

    @GetMapping("/mycards")
    public String getMyCardsDetails(){
        return "Here are my cards details from DB";
    }
}
