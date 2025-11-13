package com.app.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.app.entity.Country;
import com.app.entity.DistrictEntity;
import com.app.entity.StateEntity;
@Service
public interface CountryStateService {
	public List<Country> getCountryList();
	public List<StateEntity> getStatesByCountryId(UUID countryId);
	public Map<UUID,List<StateEntity>> getCountryStateMap();
	public List<DistrictEntity> getDistrictsByStateId(UUID stateId);
	public DistrictEntity getDistrictByStateId(UUID stateId, String districtKey);
	public List<DistrictEntity> getDisgetDistrictsByStateIdtrictBykey(String districtKey);
	public DistrictEntity getDistrictByStateIdAndDistrictID(UUID stateId, UUID districtId);
	public Country getCountryById(UUID countryId);
	public StateEntity getStateById(UUID stateId);
	public DistrictEntity getDistrictById(UUID districtId);
	public void pushCountry(Country countryOBj);
	public void pushState(StateEntity state);
	

}
