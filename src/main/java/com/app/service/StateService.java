package com.app.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.StateDAO;
import com.app.dto.StateResponse;
import com.app.entity.StateEntity;
import com.app.repository.StateRepositry;

import lombok.AllArgsConstructor;
import lombok.NonNull;
@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class StateService {
	private @NonNull StateRepositry  stateRepositry;
	private @NonNull StateDAO stateDAO ;
	
	public void save(StateEntity state) {
		stateRepositry.save(state);
	}

	
	public List<StateResponse> findAll() {
		return stateDAO.findAll();
	}

	public void getById(UUID id) {
		stateRepositry.findById(id);
	}

public void delete(UUID stateId) {
		stateRepositry.deleteById(stateId);
	}


	public StateEntity findByShortName(String shortName) {
		return stateRepositry.findByShortName(shortName);
	}


	public StateEntity findByStateName(String stateName) {
		return stateRepositry.findByStateName(stateName);
	}


	public StateEntity get(UUID id) {
		
		return stateDAO.get(id);
	}


	public StateEntity findByNameExcludeId(String stateName, UUID id) {
		
		return stateDAO.findByNameExcludeId(stateName,id);
	}
 
	public void update(StateEntity state) {
		stateRepositry.saveAndFlush(state);
	}


	public Object findById(UUID id) {
		return stateRepositry.findById(id);
	}


	public Object getStatesByCountryId(UUID countryId) {
		return stateDAO.getStatesByCountryId(countryId);
	}

}
