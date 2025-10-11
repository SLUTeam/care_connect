package com.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.LocalUser;

import java.util.UUID;

@Repository
public interface LocalUserRepository extends JpaRepository<LocalUser, UUID> {

    LocalUser findByPhoneNumber(String phoneNumber);

}
