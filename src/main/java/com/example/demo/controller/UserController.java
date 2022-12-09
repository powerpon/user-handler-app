package com.example.demo.controller;

import com.example.demo.dtos.UserChangePasswordDTO;
import com.example.demo.dtos.UserDetailsDTO;
import com.example.demo.model.AppUser;
import com.example.demo.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<AppUser> getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(userService.getUserByUsername(authentication.getName()));
    }

    @PutMapping("/addRoleToUser")
    public ResponseEntity<AppUser> addRoleToUser(@RequestParam String username, @RequestParam String roleName){
        return ResponseEntity.ok().body(userService.updateUserAddRole(username, roleName));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUserSelfProfileDeletion(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteUser(authentication.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getUsers/byRole")
    public ResponseEntity<List<AppUser>> getUsersByRole(@RequestParam String roleName, @RequestParam Long page){
        return ResponseEntity.ok().body(userService.getUsersByRole(roleName, page));
    }

    @PutMapping("/removeRoleFromUser")
    public ResponseEntity<AppUser> removeRoleFromUser(@RequestParam String username, @RequestParam String roleName){
        return ResponseEntity.ok().body(userService.updateUserRemoveRole(username, roleName));
    }

    @PutMapping("/changePassword")
    public ResponseEntity<AppUser> changeUserPassword(@RequestBody UserChangePasswordDTO userChangePasswordDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(userService.updateUserPassword(authentication.getName(), userChangePasswordDTO));
    }

    @PutMapping("/change/details")
    public ResponseEntity<AppUser> changeUserDetails(@RequestBody UserDetailsDTO userDetailsDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().body(userService.updateUserDetails(authentication.getName(), userDetailsDTO));
    }

    @GetMapping("/get/users")
    public ResponseEntity<List<AppUser>> getUsersByPage(@RequestParam Long page){
        return ResponseEntity.ok().body(userService.getAllUsersByPage(page));
    }

}
