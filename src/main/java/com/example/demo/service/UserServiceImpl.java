package com.example.demo.service;

import com.example.demo.dtos.RegisterDTO;
import com.example.demo.dtos.UserDetailsDTO;
import com.example.demo.exception.UserDoesNotExistException;
import com.example.demo.model.AppUser;
import com.example.demo.model.Identity;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.interfaces.IdentityService;
import com.example.demo.service.interfaces.RoleService;
import com.example.demo.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final IdentityService identityService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

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
    public AppUser getUserById(String id) {
        Identity identity = identityService.getUserIdentityById(id);
        Optional<AppUser> user = userRepository.findByIdentity(identity);
        if(user.isEmpty()){
            throw new UserDoesNotExistException();
        }
        return user.get();
    }

    @Override
    public AppUser updateUserDetails(String username, UserDetailsDTO userDetailsDTO) {
        ModelMapper modelMapper = new ModelMapper();
        AppUser user = this.getUserByUsername(username);
        modelMapper.map(userDetailsDTO, user);
        return userRepository.save(user);
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
