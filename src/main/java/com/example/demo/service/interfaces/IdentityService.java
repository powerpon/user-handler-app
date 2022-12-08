package com.example.demo.service.interfaces;

import com.example.demo.model.Identity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IdentityService {

    Identity saveUserIdentity(Identity identity);
    Identity getUserIdentityByUsername(String username);
    List<Identity> getIdentitiesByRole(String roleName, Pageable page);
    Identity updateUserIdentityPassword(String username, String newPassword);
    Identity updateUserIdentityAddRole(String username, String roleName);
    Identity updateUserIdentityRemoveRole(String username, String roleName);
    void deleteUserIdentity(Identity identity);

}
