package com.easybytes.config;

import com.easybytes.Modal.Authority;
import com.easybytes.Modal.Customer;
import com.easybytes.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username=authentication.getName();
        String password=authentication.getCredentials().toString();


        List<Customer>customerList = customerRepository.findByEmail(username);
        Customer customer=null;

        if(customerList.size()>0) customer=customerList.get(0);

        if(customer != null){
            if(passwordEncoder.matches(password,customer.getPassword())){

               return new UsernamePasswordAuthenticationToken(username,"",getAuthorityList(customer.getAuthorityList()));
            }
            else{
                throw new BadCredentialsException("Invalid password");
            }
        }
        else{
            throw new BadCredentialsException("UserId does not exist");
        }


    }

    private Collection<? extends GrantedAuthority> getAuthorityList(List<Authority> authorityList) {

        List<GrantedAuthority>grantedAuthorityList=new ArrayList<>();

        for(Authority authority:authorityList){
            grantedAuthorityList.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorityList;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
