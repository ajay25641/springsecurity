package com.easybytes.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {


    @Bean
    public SecurityFilterChain defaultFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests().anyRequest().authenticated().and().oauth2Login();

        return http.build();
    }


    //my spring boot application will automatically configure clientRegistrationRepository bean when it finds client details in application.properties
    /*@Bean
    public ClientRegistrationRepository clientRegistrationRepository(){
        ClientRegistration clientReg=clientRegistration();
        
        return new InMemoryClientRegistrationRepository(clientReg);
    }

    private ClientRegistration clientRegistration() {

        return CommonOAuth2Provider.GITHUB.getBuilder("github").clientId("f502ccf806f2a85861a9").clientSecret("e092256c3979905787d133637bebebc72764e55a").build();

    }*/


}
