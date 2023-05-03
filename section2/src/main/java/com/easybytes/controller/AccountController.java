package com.easybytes.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/myaccount")
    public String getMyAccountDetails(){
        return "Here are my account details from db";
    }
}
