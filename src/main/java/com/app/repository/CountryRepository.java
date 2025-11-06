package com.app.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.Country;
@Repository
public interface CountryRepository extends WriteableRepository<Country, UUID> {

	Country findByCountryName(String countryName);

	Country findByShortName(String shortName);

	Country findByCountryCode(String countryCode);
}
