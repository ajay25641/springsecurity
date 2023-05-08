package com.easybytes.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.easybytes.Modal.Contact;
import com.easybytes.Repository.ContactRepository;
import com.easybytes.constants.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;


    //@PreFilter("filterObject.contactName!='Test'")
    @PostFilter("filterObject.contactName!='Test'")
    @PostMapping(ApiConstants.CONTACT)
    public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contactList) {

        if(contactList.size()==0) return new ArrayList<>();

        Contact contact=contactList.get(0);

        contact.setCreatedAt(new Date(System.currentTimeMillis()));

        contact=contactRepository.save(contact);

        List<Contact>returnContactList=new ArrayList<>();
        returnContactList.add(contact);

        return returnContactList;
    }


}
