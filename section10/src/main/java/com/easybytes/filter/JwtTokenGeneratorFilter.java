package com.easybytes.filter;

import com.easybytes.constants.ApiConstants;
import com.easybytes.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication!=null){
            SecretKey key= Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

            String jwtToken=Jwts
                    .builder()
                    .setIssuer("Easy Bank")
                    .setSubject("Jwt Token")
                    .claim("username",authentication.getName())
                    .claim("authorities",populateAuthorites(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(new Date().getTime()+3_000_000_000L))
                    .signWith(key).compact();
            response.setHeader(SecurityConstants.JWT_HEADER,jwtToken);
        }
        filterChain.doFilter(request,response);

    }

    @Override
    public boolean shouldNotFilter(HttpServletRequest req){
        return !req.getServletPath().equals(ApiConstants.USER_DETAILS);
    }

    private Object populateAuthorites(Collection<? extends GrantedAuthority> authorities) {

        Set<String> authrotiesSet=new HashSet<>();
        for(GrantedAuthority authority:authorities){
            authrotiesSet.add(authority.getAuthority());
        }

        return String.join(",",authrotiesSet);

    }
}
