package com.example.demo.controller;

import com.example.demo.dtos.RoleDTO;
import com.example.demo.dtos.UsernameRoleNameDTO;
import com.example.demo.model.AppRole;
import com.example.demo.model.AppUser;
import com.example.demo.model.Identity;
import com.example.demo.service.interfaces.IdentityService;
import com.example.demo.service.interfaces.RoleService;
import com.example.demo.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IdentityService identityService;
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<AppUser> getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(userService.getUserByUsername(authentication.getName()));
    }

    @PutMapping("/addRoleToUser")
    public ResponseEntity<Identity> addRoleToUser(@RequestParam String username, @RequestBody RoleDTO roleDTO){
        return ResponseEntity.ok().body(identityService.updateUserIdentityAddRole(username, roleDTO.getName()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserSelfProfileDeletion(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteUser(authentication.getName());
        return ResponseEntity.ok().build();
    }


}
