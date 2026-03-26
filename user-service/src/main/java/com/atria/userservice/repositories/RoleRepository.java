package com.atria.userservice.repositories;

import com.atria.userservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(String name);

    Set<Role> findByNameIn(Set<String> names);

    boolean existsByName(String name);
}