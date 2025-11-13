package com.app.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.entity.Country;
import com.app.entity.DistrictEntity;
import com.app.entity.StateEntity;
import com.app.repository.CountryRepository;
import com.app.repository.DistrictRepository;
import com.app.repository.StateRepositry;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Repository
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class CountryStateServiceImpl implements CountryStateService {

	final static Logger logger = LoggerFactory.getLogger(CountryStateServiceImpl.class);
	@Autowired
	private @NonNull CountryRepository countryRepository;
	@Autowired
	private @NonNull StateRepositry stateRepository;
	@Autowired
	private @NonNull DistrictRepository districtRepository;
	

	private List<Country> countryList = new ArrayList<Country>();
	private Map<UUID, List<StateEntity>> countryStateMap = new HashMap<UUID, List<StateEntity>>();
	private Map<UUID, List<DistrictEntity>> stateDistrictMap = new HashMap<UUID, List<DistrictEntity>>();
	private Map<UUID, Country> countryMap = new HashMap<>();
	private Map<UUID, StateEntity> stateMap = new HashMap<>();
	private Map<UUID, DistrictEntity> districtMap = new HashMap<>();
	
	

	@PostConstruct
	private void init() {

		List<Country> countries = countryRepository.findAll();

		if (null != countries && !countries.isEmpty()) {

			List<StateEntity> stateList = stateRepository.findAll();

			Map<UUID, List<StateEntity>> countryStateMapTemp = new HashMap<UUID, List<StateEntity>>();
			Map<UUID, StateEntity> stateMapTemp = new HashMap<>();
			for (StateEntity state : stateList) {
				List<StateEntity> stateTempList = countryStateMapTemp.get(state.getCountryId());
				if (null != stateTempList && !stateTempList.isEmpty()) {
					stateTempList.add(state);
				} else {
					stateTempList = new ArrayList<StateEntity>();
					stateTempList.add(state);
				}
				countryStateMapTemp.put(state.getCountryId(), stateTempList);
				stateMapTemp.put(state.getId(), state);
				List<DistrictEntity> districts = districtRepository.findByStateId(state.getId());
				stateDistrictMap.put(state.getId(), districts);

			}

			Map<UUID, Country> countryMapTemp = new HashMap<>();
			List<Country> countryListTemp = new ArrayList<Country>();
			for (Country country : countries) {
				countryMapTemp.put(country.getId(), country);
				countryListTemp.add(country);
			}

			countryMap.clear();
			countryMap = new HashMap<UUID, Country>(countryMapTemp);

			countryList.clear();
			countryList = new ArrayList<>(countryListTemp);

			countryStateMap.clear();
			countryStateMap = new HashMap<>(countryStateMapTemp);

			stateMap.clear();
			stateMap = new HashMap<UUID, StateEntity>(stateMapTemp);

		}
	}

	@Override
	public List<Country> getCountryList() {
		return countryList;
	}

	@Override
	public List<StateEntity> getStatesByCountryId(UUID countryId) {
		return countryStateMap.get(countryId);
	}

	@Override
	public Map<UUID, List<StateEntity>> getCountryStateMap() {
		return countryStateMap;
	}

	public List<DistrictEntity> getDistrictBykey(String districtKey) {
		List<DistrictEntity> districtList = new ArrayList<DistrictEntity>();
		for (UUID stateId : stateDistrictMap.keySet()) {
			for (DistrictEntity districtOb : stateDistrictMap.get(stateId)) {
				if (districtOb.getDistrictName().toLowerCase().contains(districtKey.toLowerCase())) {
					districtList.add(districtOb);
				}
			}
		}

		return districtList;
	}

	@Override
	public DistrictEntity getDistrictByStateIdAndDistrictID(UUID stateId, UUID districtId) {
		DistrictEntity districtObj = null;
		for (DistrictEntity districtOb : stateDistrictMap.get(stateId)) {
			if (districtOb.getId().equals(districtId)) {
				districtObj = districtOb;
				break;
			}
		}
		return districtObj;
	}

	@Override
	public void pushCountry(Country countryOBj) {
		countryMap.put(countryOBj.getId(), countryOBj);
		countryList.add(countryOBj);
	}

	@Override
	public void pushState(StateEntity state) {
		stateMap.put(state.getId(), state);
		List<StateEntity> stateTempList = countryStateMap.get(state.getCountryId());
		if (null != stateTempList && !stateTempList.isEmpty()) {
			stateTempList.add(state);
		} else {
			stateTempList = new ArrayList<StateEntity>();
			stateTempList.add(state);
		}
		countryStateMap.put(state.getCountryId(), stateTempList);
	}

	@Override
	public Country getCountryById(UUID countryId) {
		return countryMap.get(countryId);
	}

	@Override
	public StateEntity getStateById(UUID stateId) {
		return stateMap.get(stateId);
	}

	@Override
	public DistrictEntity getDistrictById(UUID districtId) {
		return districtMap.get(districtId);
	}

	@Override
	public List<DistrictEntity> getDistrictsByStateId(UUID stateId) {
		
		return stateDistrictMap.get(stateId);
	}

	@Override
	public DistrictEntity getDistrictByStateId(UUID stateId, String districtKey) {
		
		return districtMap.get(districtKey);
	}

	@Override
	public List<DistrictEntity> getDisgetDistrictsByStateIdtrictBykey(String districtKey) {
		
		return null;
	}

}
