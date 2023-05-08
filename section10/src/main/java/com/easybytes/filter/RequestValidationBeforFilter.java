package com.easybytes.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.invoke.StringConcatException;
import java.util.Base64;


public class RequestValidationBeforFilter implements Filter {


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String auth="authorization";
        String authType="Basic";

        var req= (HttpServletRequest) request;
        var res= (HttpServletResponse) response;


        if(req.getHeader(auth)!=null){
            String header[]=req.getHeader(auth).trim().split(" ");
            if(StringUtils.startsWithIgnoreCase(header[0],authType)){

                try{
                    String token=new String(Base64.getDecoder().decode(header[1]));
                    int delimeter=token.indexOf(":");
                    if(delimeter == -1){
                        throw new BadCredentialsException("Invalid Basic authentication token !!");
                    }
                    String email=token.split(":")[0];
                    if(email.toLowerCase().contains("test")){
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                }
                catch (IllegalArgumentException ex){
                    throw new BadCredentialsException("Failed to decode basic authentication token");
                }
            }
        }
        chain.doFilter(request,response);

    }


}
