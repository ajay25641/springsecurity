package com.easybytes.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoansController {

    @GetMapping("/myloans")
    public String getMyLoans(){
        return "Here are my loans details";
    }
}
