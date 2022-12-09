package com.example.demo.controller;

import com.example.demo.dtos.RegisterDTO;
import com.example.demo.model.AppUser;
import com.example.demo.service.interfaces.RefreshTokenService;
import com.example.demo.service.interfaces.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class LoginRegisterController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<AppUser> registerUser(@RequestBody RegisterDTO registerDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(registerDTO));
    }

    @GetMapping("/refreshAccessToken")
    public void getNewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        refreshTokenService.getNewAccessToken(request, response);
    }

}
