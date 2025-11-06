package com.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.User;

@Repository
public interface RegisterRepository extends WriteableRepository <User,UUID>{

	Optional<User> findById(UUID id);

	User saveAndFlush(User userObj);



}
