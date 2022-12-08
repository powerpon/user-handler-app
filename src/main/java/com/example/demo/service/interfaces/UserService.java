package com.example.demo.service.interfaces;

import com.example.demo.dtos.RegisterDTO;
import com.example.demo.dtos.UserDetailsDTO;
import com.example.demo.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    AppUser saveUser(RegisterDTO registerDTO);
    AppUser getUserByUsername(String username);
    AppUser getUserById(String id);
    AppUser updateUserDetails(String username, UserDetailsDTO userDetailsDTO);
    void deleteUser(String username);

}
