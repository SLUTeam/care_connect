package com.app.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.CountryDAO;
import com.app.entity.Country;
import com.app.enumeration.Status;
import com.app.repository.CountryRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class CountryService {

	private @NonNull CountryRepository countryRepository;
	@Autowired
	CountryDAO countryDAO;

	public Optional<Country> findById(UUID id) {
		return countryRepository.findById(id);
	}

	public void save(Country country) { 
		countryRepository.save(country);
	}

	public void delete(UUID countryId) {
		countryRepository.deleteById(countryId);
	}

	public Country findByCountryName(String name) {
		return countryRepository.findByCountryName(name);
	}

	public Country findByShortName(String shortName) {
		return countryRepository.findByShortName(shortName);
	}

	public Country get(UUID id) {
		return countryDAO.get(id);
	}

	public Country findByNameExcludeId(String name, UUID id) {
		return countryDAO.findByNameExcludeId(name,id);
	}

	public void update(Country country) {
		countryDAO.saveOrUpdate(country);
		
	}

	public Object getCountryList() {
		return countryDAO.findAll();
	}

	public Country findByCodeExcludeId(String countryCode, UUID id) {
		return  countryDAO.findByCodeExcludeId(countryCode,id);
	}

	public Country findByCountryCode(String countryCode) {
		
		return countryRepository.findByCountryCode(countryCode);
	}

	}
