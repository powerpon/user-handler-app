package com.example.demo.service.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface RefreshTokenService {

    public void getNewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
