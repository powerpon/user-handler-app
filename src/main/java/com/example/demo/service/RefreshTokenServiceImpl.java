package com.example.demo.service;

import com.example.demo.exception.MissingRefreshTokenException;
import com.example.demo.model.AppUser;
import com.example.demo.service.interfaces.RefreshTokenService;
import com.example.demo.service.interfaces.UserService;
import com.example.demo.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@Transactional
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserService userService;

    @Override
    public void getNewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if((request.getHeader(AUTHORIZATION) != null) && request.getHeader(AUTHORIZATION).startsWith("Bearer ")){
            try {
                String refreshToken = JwtUtil.getRefreshTokenFromBearer(request.getHeader(AUTHORIZATION));
                String username = JwtUtil.getDecodedUsernameFromRefreshToken(refreshToken);
                AppUser user = userService.getUserByUsername(username);
                String newAccessToken = JwtUtil.refreshAppUserAccessToken(user, request.getRequestURL().toString());
                Map<String, String> tokens = JwtUtil.getMappedTokens(newAccessToken, refreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception ex){
                response.setHeader(HttpHeaders.WARNING, ex.getMessage());
                response.setStatus(NO_CONTENT.value());
                Map<String, String> error = new HashMap<>();
                error.put("errorMessage", ex.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }
        else {
            throw new MissingRefreshTokenException();
        }
    }

}
