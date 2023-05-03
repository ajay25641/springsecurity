package com.easybytes.config;

import com.easybytes.Modal.Customer;
import com.easybytes.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class EasyBankUserDetails implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName=null;
        String password=null;
        List<GrantedAuthority> authorityList;

        Customer customer=customerRepository.findByEmail(username);

        if(customer == null){
            throw new UsernameNotFoundException("UserDetails not found for the user "+username);
        }
        else{
            authorityList=new ArrayList<>();
            authorityList.add(new SimpleGrantedAuthority(customer.getRole()));

            userName= customer.getEmail();
            password= customer.getPassword();

            return new User(userName,password,authorityList);
        }


    }
}
