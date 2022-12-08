package com.example.demo.repository;

import com.example.demo.model.Identity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentityRepository extends JpaRepository<Identity, String> {

    Optional<Identity> findByUsername(String username);
    @NonNull Page<Identity> findByRolesName(String roles_name, Pageable pageable);

}
