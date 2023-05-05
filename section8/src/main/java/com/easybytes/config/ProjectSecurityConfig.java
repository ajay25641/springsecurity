package com.easybytes.config;


import com.easybytes.constants.ApplicationConstants;
import com.easybytes.filter.CsrfCookieFilter;
import com.easybytes.filter.RequestValidationBeforFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;


import java.util.Collections;


@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler requestAttributeHandler=new CsrfTokenRequestAttributeHandler();
        requestAttributeHandler.setCsrfRequestAttributeName("_csrf");


        http
                .securityContext().requireExplicitSave(false)
                .and()
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config=new CorsConfiguration();

                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        config.setAllowCredentials(true);

                        return config;


                    }
                })
                .and()
                .csrf((csrf)->csrf.csrfTokenRequestHandler(requestAttributeHandler)
                        .ignoringRequestMatchers("/register","/contact")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new RequestValidationBeforFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests()
                /*.requestMatchers("/myaccount").hasAuthority(ApplicationConstants.VIEW_ACCOUNT)
                .requestMatchers("/mybalance").hasAnyAuthority(ApplicationConstants.VIEW_ACCOUNT,ApplicationConstants.VIEW_BALANCE)
                .requestMatchers("/myloans").hasAuthority(ApplicationConstants.VIEW_LOANS)
                .requestMatchers("/mycards").hasAuthority(ApplicationConstants.VIEW_CARDS)*/
                .requestMatchers("/myaccount").hasRole("USER")
                .requestMatchers("/mybalance").hasAnyRole("USER","ADMIN")
                .requestMatchers("/myloans").hasRole("USER")
                .requestMatchers("/mycards").hasRole("USER")
                .requestMatchers("/user").authenticated()
                .requestMatchers("/notices","contact","/register").permitAll()
                .and()
                .formLogin()
                .and()
                .httpBasic();


        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
