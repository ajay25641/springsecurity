package com.easybytes.filter;

import com.easybytes.constants.ApiConstants;
import com.easybytes.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.header.writers.StaticHeadersWriter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JwtTokenValidatorFilter extends OncePerRequestFilter {
    private static final String AUTHENTICATION_SCHEME_BEARER="Bearer ";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //String jwtToken=request.getHeader(SecurityConstants.JWT_HEADER);
        String jwtToken=parseJwtToken(request);

        if(jwtToken!=null){
            try{
                SecretKey key= Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
                Claims claims= Jwts
                        .parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwtToken)
                        .getBody();
                String username=String.valueOf(claims.get("username"));
                String authorities=String.valueOf(claims.get("authorities"));

                Authentication auth=new
                        UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
            catch (Exception ex){
                throw new BadCredentialsException("Invalid token received");
            }
        }
        filterChain.doFilter(request,response);

    }

    private String parseJwtToken(HttpServletRequest request) {

        String header=request.getHeader(SecurityConstants.JWT_HEADER);
        if(header==null){
            return null;
        }
        header=header.trim();
        if(!StringUtils.startsWithIgnoreCase(header,AUTHENTICATION_SCHEME_BEARER)){
            return null;
        }
        if(header.equalsIgnoreCase(AUTHENTICATION_SCHEME_BEARER)){
            return null;
        }
        return header.substring(7);

    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest req){
        return req.getServletPath().equals(ApiConstants.USER_DETAILS);
    }
}
