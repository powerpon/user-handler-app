package com.example.demo.controller;

import com.example.demo.dtos.RoleDTO;
import com.example.demo.model.AppRole;
import com.example.demo.service.interfaces.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RolesController {

    private final RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<AppRole> createRole(@RequestBody RoleDTO roleDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.saveRole(roleDTO.getName()));
    }

    @GetMapping("/get")
    public ResponseEntity<AppRole> getRole(@RequestParam String roleName){
        return ResponseEntity.ok().body(roleService.getRole(roleName));
    }

    @PutMapping("/change/name")
    public ResponseEntity<AppRole> changeRoleName(@RequestParam String roleName, @RequestBody RoleDTO roleDTO){
        return ResponseEntity.ok().body(roleService.updateRoleName(roleName, roleDTO.getName()));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRole(@RequestParam String roleName){
        roleService.deleteRole(roleName);
        return ResponseEntity.ok().build();
    }

}
