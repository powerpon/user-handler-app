package com.example.demo.configuration;

import com.example.demo.dtos.RegisterDTO;
import com.example.demo.model.AppRole;
import com.example.demo.model.Identity;
import com.example.demo.repository.IdentityRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.interfaces.IdentityService;
import com.example.demo.service.interfaces.RoleService;
import com.example.demo.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final RoleService roleService;
    private final UserService userService;
    private final IdentityService identityService;

    @Bean
    CommandLineRunner initializeDefaultRoles(){
        return args -> {
            roleService.saveRole("ROLE_USER");
            roleService.saveRole("ROLE_ADMIN");
        };
    }

    @Bean
    CommandLineRunner initializeAdmin(){
        return args -> {
            RegisterDTO registerDTO = new RegisterDTO("admin", "pass", "Admin", "Admin");
            userService.saveUser(registerDTO);
            identityService.updateUserIdentityAddRole("admin", "ROLE_ADMIN");
        };
    }

}
