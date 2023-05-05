package com.easybytes.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.invoke.StringConcatException;
import java.util.Base64;


public class RequestValidationBeforFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {


        var req= (HttpServletRequest) request;
        var res= (HttpServletResponse) response;


        String header=req
                         .getHeader("authorization")
                         .trim()
                         .split(" ")
                         [1];
        String email= new String(Base64.getDecoder().decode(header))
                           .split(":")
                           [0];


        System.out.println("printing header from user"+System.lineSeparator()+email);

        chain.doFilter(request,response);

    }


}
