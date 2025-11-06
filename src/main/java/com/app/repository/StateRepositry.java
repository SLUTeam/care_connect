package com.app.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.config.WriteableRepository;
import com.app.entity.StateEntity;
@Repository
public interface StateRepositry extends  WriteableRepository<StateEntity, UUID> {

	StateEntity findByShortName(String shortName);

	StateEntity findByStateName(String stateName);

}
