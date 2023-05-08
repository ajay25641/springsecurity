package com.easybytes.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.io.IOException;



@Slf4j
public class AuthoritiesLoggingAfterFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Authentication authenticationn= SecurityContextHolder.getContext().getAuthentication();

        var req=(HttpServletRequest) servletRequest;




        if(authenticationn!=null){
            log.info("User "+authenticationn.getName()+" is successfully authenticated and has the authorities "+authenticationn.getAuthorities().toString());
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }
}
