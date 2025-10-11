package com.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, UUID> {

}
