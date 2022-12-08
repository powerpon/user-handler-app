package com.example.demo.service;

import com.example.demo.exception.IdentityDoesNotExistException;
import com.example.demo.model.Identity;
import com.example.demo.repository.IdentityRepository;
import com.example.demo.service.interfaces.IdentityService;
import com.example.demo.service.interfaces.RoleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class IdentityServiceImpl implements IdentityService {

    private final IdentityRepository identityRepository;
    private final RoleService roleService;

    @Override
    public Identity saveUserIdentity(Identity identity) {
        return identityRepository.save(identity);
    }

    @Override
    public Identity getUserIdentityByUsername(String username) {
        Optional<Identity> identity = identityRepository.findByUsername(username);
        if(identity.isEmpty()){
            throw new IdentityDoesNotExistException();
        }
        return identity.get();
    }

    @Override
    public Identity getUserIdentityById(String id) {
        return identityRepository.getReferenceById(id);
    }

    @Override
    public Identity updateUserIdentityPassword(String username, String newPassword) {
        Identity identity = this.getUserIdentityByUsername(username);
        identity.setPassword(newPassword);
        return identityRepository.save(identity);
    }

    @Override
    public Identity updateUserIdentityAddRole(String username, String roleName) {
        Identity identity = this.getUserIdentityByUsername(username);
        if(identity.getRoles().contains(roleService.getRole(roleName))){
            return identity;
        }
        identity.getRoles().add(roleService.getRole(roleName));
        return identityRepository.save(identity);
    }

    @Override
    public Identity updateUserIdentityRemoveRole(String username, String roleName) {
        Identity identity = this.getUserIdentityByUsername(username);
        if(identity.getRoles().contains(roleService.getRole(roleName))){
            identity.getRoles().remove(roleService.getRole(roleName));
            return identityRepository.save(identity);
        }
        return identity;
    }

    @Override
    public void deleteUserIdentity(Identity identity) {
        identityRepository.delete(identity);
    }

}
