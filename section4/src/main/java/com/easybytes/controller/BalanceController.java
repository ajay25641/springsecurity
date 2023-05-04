package com.easybytes.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @GetMapping("mybalance")
    public String getMyBalance(){
        return "Here are my balance details from DB";
    }
}
