package com.app.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.Country;
@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {

}
