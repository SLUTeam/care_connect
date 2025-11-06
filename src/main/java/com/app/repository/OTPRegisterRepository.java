package com.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.OTPRegister;

@Repository
public interface OTPRegisterRepository extends JpaRepository<OTPRegister, Long> {
    Optional<OTPRegister> findByEmail(String email);
}