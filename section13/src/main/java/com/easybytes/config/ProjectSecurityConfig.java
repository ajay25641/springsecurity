package com.easybytes.config;


import com.easybytes.constants.ApiConstants;
import com.easybytes.constants.ApplicationConstants;
import com.easybytes.filter.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


import java.util.Arrays;
import java.util.Collections;


@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler requestAttributeHandler=new CsrfTokenRequestAttributeHandler();
        requestAttributeHandler.setCsrfRequestAttributeName("_csrf");


        JwtAuthenticationConverter jwtAuthenticationConverter=new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());


        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config=new CorsConfiguration();

                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setExposedHeaders(Arrays.asList(ApplicationConstants.JWT_HEADER));
                        config.setMaxAge(3600L);
                        config.setAllowCredentials(true);

                        return config;


                    }
                })
                .and()
                .csrf((csrf)->csrf.csrfTokenRequestHandler(requestAttributeHandler)
                        .ignoringRequestMatchers(ApiConstants.REGISTER,ApiConstants.CONTACT)
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)

                .authorizeHttpRequests()
                .requestMatchers(ApiConstants.ACCOUNT_DETAILS).hasRole(ApplicationConstants.ROLE_USER)
                .requestMatchers(ApiConstants.BALANCE_DETAILS).hasAnyRole(ApplicationConstants.ROLE_USER,ApplicationConstants.ROLE_ADMIN)
                .requestMatchers(ApiConstants.LOAN_DETAILS).authenticated()
                .requestMatchers(ApiConstants.CARD_DETAILS).hasRole(ApplicationConstants.ROLE_USER)
                .requestMatchers(ApiConstants.USER_DETAILS).authenticated()
                .requestMatchers(ApiConstants.NOTICE,ApiConstants.CONTACT,ApiConstants.REGISTER).permitAll()
                .and()
                .oauth2ResourceServer().jwt().jwtAuthenticationConverter(jwtAuthenticationConverter);


        return http.build();
    }




}
