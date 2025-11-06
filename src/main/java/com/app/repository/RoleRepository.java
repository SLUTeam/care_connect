package com.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.Role;
import com.app.enumeration.UserType;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findById(UUID id);

    Optional<Role> findByRoleName(UserType roleName);

    boolean existsByRoleName(UserType roleName);

    Optional<Role> findByRoleDescription(String roleDescription);

    @Query("SELECT r.roleName FROM Role r WHERE r.id = :id")
    Optional<UserType> findRoleNameById(@Param("id") UUID id);
}