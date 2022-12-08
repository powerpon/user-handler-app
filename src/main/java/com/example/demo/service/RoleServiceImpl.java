package com.example.demo.service;

import com.example.demo.exception.RoleDoesNotExistException;
import com.example.demo.model.AppRole;
import com.example.demo.model.Identity;
import com.example.demo.repository.IdentityRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.interfaces.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final IdentityRepository identityRepository;

    @Override
    public AppRole saveRole(String roleName) {
        return roleRepository.save(new AppRole(roleName));
    }

    @Override
    public AppRole getRole(String roleName) {
        Optional<AppRole> role = roleRepository.getByName(roleName);
        if(role.isEmpty()){
            throw new RoleDoesNotExistException();
        }
        return role.get();
    }

    @Override
    public AppRole updateRoleName(String roleName, String newRoleName) {
        AppRole role = this.getRole(roleName);
        role.setName(newRoleName);
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(String roleName) {
        List<Identity> identities = identityRepository.findByRolesName(roleName, null).stream().toList();
        identities.forEach(
                identity -> {
                    identity.getRoles().remove(this.getRole(roleName));
                }
        );
        roleRepository.delete(this.getRole(roleName));
    }

}
