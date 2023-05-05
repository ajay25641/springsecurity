package com.easybytes.controller;

import java.sql.Date;
import java.util.Random;

import com.easybytes.Modal.Contact;
import com.easybytes.Repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @PostMapping("/contact")
    public Contact saveContactInquiryDetails(@RequestBody Contact contact) {
        contact.setCreatedAt(new Date(System.currentTimeMillis()));
        return contactRepository.save(contact);
    }


}
