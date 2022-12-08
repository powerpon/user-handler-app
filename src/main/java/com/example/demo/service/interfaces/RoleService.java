package com.example.demo.service.interfaces;

import com.example.demo.model.AppRole;

public interface RoleService {

    AppRole saveRole(String roleName);
    AppRole getRole(String roleName);
    AppRole updateRoleName(String roleName, String newRoleName);
    void deleteRole(String roleName);

}
