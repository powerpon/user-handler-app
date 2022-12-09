package com.example.demo.service.interfaces;

import com.example.demo.dtos.RegisterDTO;
import com.example.demo.dtos.UserChangePasswordDTO;
import com.example.demo.dtos.UserDetailsDTO;
import com.example.demo.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    AppUser saveUser(RegisterDTO registerDTO);
    AppUser getUserByUsername(String username);
    List<AppUser> getAllUsersByPage(Long page);
    List<AppUser> getUsersByRole(String roleName, Long page);
    AppUser updateUserDetails(String username, UserDetailsDTO userDetailsDTO);
    AppUser updateUserAddRole(String username, String roleName);
    AppUser updateUserRemoveRole(String username, String roleName);
    AppUser updateUserPassword(String username, UserChangePasswordDTO userChangePasswordDTO);
    void deleteUser(String username);

}
