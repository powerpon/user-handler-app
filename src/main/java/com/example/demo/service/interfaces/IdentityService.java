package com.example.demo.service.interfaces;

import com.example.demo.model.Identity;

public interface IdentityService {

    Identity saveUserIdentity(Identity identity);
    Identity getUserIdentityByUsername(String username);
    Identity getUserIdentityById(String id);
    Identity updateUserIdentityPassword(String username, String newPassword);
    Identity updateUserIdentityAddRole(String username, String roleName);
    Identity updateUserIdentityRemoveRole(String username, String roleName);
    void deleteUserIdentity(Identity identity);

}
