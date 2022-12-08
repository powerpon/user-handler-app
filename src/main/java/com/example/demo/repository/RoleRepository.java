package com.example.demo.repository;

import com.example.demo.model.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<AppRole, Long> {

    Optional<AppRole> getByName(String name);

}
