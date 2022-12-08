package com.example.demo.service;

import com.example.demo.dtos.RegisterDTO;
import com.example.demo.dtos.UserChangePasswordDTO;
import com.example.demo.dtos.UserDetailsDTO;
import com.example.demo.exception.PasswordMismatchException;
import com.example.demo.exception.UserDoesNotExistException;
import com.example.demo.model.AppUser;
import com.example.demo.model.Identity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.interfaces.IdentityService;
import com.example.demo.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final IdentityService identityService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveUser(RegisterDTO registerDTO) {
        Identity identity = identityService.saveUserIdentity(new Identity(
                registerDTO.getUsername(),
                registerDTO.getPassword()
        ));
        identity.setPassword(passwordEncoder.encode(identity.getPassword()));
        identityService.updateUserIdentityAddRole(registerDTO.getUsername(), "ROLE_USER");
        return userRepository.save(new AppUser(
                identity,
                registerDTO.getFirstName(),
                registerDTO.getLastName()
        ));
    }

    @Override
    public AppUser getUserByUsername(String username) {
        Identity identity = identityService.getUserIdentityByUsername(username);
        Optional<AppUser> user = userRepository.findByIdentity(identity);
        if(user.isEmpty()){
            throw new UserDoesNotExistException();
        }
        return user.get();
    }

    @Override
    public List<AppUser> getUsersByRole(String roleName, Long page) {
        List<Identity> identities = identityService.getIdentitiesByRole(roleName, PageRequest.of(Math.toIntExact(page), 5));
        List<AppUser> users = new ArrayList<>();
        identities.forEach(
                identity -> {
                    users.add(this.getUserByUsername(identity.getUsername()));
                }
        );
        return users;
    }

    @Override
    public AppUser updateUserDetails(String username, UserDetailsDTO userDetailsDTO) {
        ModelMapper modelMapper = new ModelMapper();
        AppUser user = this.getUserByUsername(username);
        modelMapper.map(userDetailsDTO, user);
        return userRepository.save(user);
    }

    @Override
    public AppUser updateUserAddRole(String username, String roleName) {
        Optional<AppUser> user = userRepository.findByIdentity(identityService.updateUserIdentityAddRole(username, roleName));
        if(user.isEmpty()){
            throw new UserDoesNotExistException();
        }
        return user.get();
    }

    @Override
    public AppUser updateUserRemoveRole(String username, String roleName) {
        Optional<AppUser> user = userRepository.findByIdentity(identityService.updateUserIdentityRemoveRole(username, roleName));
        if(user.isEmpty()){
            throw new UserDoesNotExistException();
        }
        return user.get();
    }

    @Override
    public AppUser updateUserPassword(String username, UserChangePasswordDTO userChangePasswordDTO) {
        AppUser user = this.getUserByUsername(username);
        if(passwordEncoder.matches(userChangePasswordDTO.getOldpassword(), user.getIdentity().getPassword())) {
            Optional<AppUser> userOptional = userRepository.findByIdentity(identityService.updateUserIdentityPassword(username, userChangePasswordDTO.getNewpassword()));
            if (userOptional.isEmpty()) {
                throw new UserDoesNotExistException();
            }
            return userOptional.get();
        }
        throw new PasswordMismatchException();
    }

    @Override
    public void deleteUser(String username) {
        AppUser user = getUserByUsername(username);
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = this.getUserByUsername(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getIdentity().getRoles().forEach(
                appRole -> {
                    authorities.add(new SimpleGrantedAuthority(appRole.getName()));
                }
        );
        return new User(user.getIdentity().getUsername(), user.getIdentity().getPassword(), authorities);
    }

}
