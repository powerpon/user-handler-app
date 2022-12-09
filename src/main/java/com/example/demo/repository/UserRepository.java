package com.example.demo.repository;

import com.example.demo.model.AppUser;
import com.example.demo.model.Identity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {

    Optional<AppUser> findByIdentity(Identity identity);
    @NonNull Page<AppUser> findAll(@NonNull Pageable page);

}
