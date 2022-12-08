package com.example.demo.security.filter;

import com.example.demo.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().equals("/login") || request.getServletPath().equals("/register") || request.getServletPath().equals("/refreshAccessToken")){
            filterChain.doFilter(request, response);
        }
        else {
            if((request.getHeader(AUTHORIZATION) != null) && request.getHeader(AUTHORIZATION).startsWith("Bearer ")){
                try {
                    String username = JwtUtil.getDecodedUsernameFromRefreshToken(JwtUtil.getRefreshTokenFromBearer(request.getHeader(AUTHORIZATION)));
                    List<String> roles = JwtUtil.getListOfRolesFromJwtPayload(JwtUtil.getRefreshTokenFromBearer(request.getHeader(AUTHORIZATION)));
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    roles.forEach(
                            role -> {
                                authorities.add(new SimpleGrantedAuthority(role));
                            }
                    );
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                }catch (Exception ex){
                    response.setHeader(HttpHeaders.WARNING, ex.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("errorMessage", ex.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            }
            else {
                filterChain.doFilter(request, response);
            }
        }
    }

}
