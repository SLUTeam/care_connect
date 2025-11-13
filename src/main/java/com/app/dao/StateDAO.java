package com.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dto.StateResponse;
import com.app.entity.StateEntity;


@Repository
public class StateDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public List<StateResponse> findAll() {
		
		Session session = sessionFactory.getCurrentSession();

		String sqlQuery ="SELECT s.id,s.name as stateName,s.short_name,c.id as countryId,c.name " 
				 +" FROM tb_state s "
				 +" INNER JOIN tb_country c ON c.id=s.country_id "; 
				
//				"SELECT s.id,s.state_name,s.short_name,c.id as countryId,c.name  "
//				+ " FROM tb_state s INNER JOIN tb_country c ON c.id=s.country_id "
//				+"WHERE s.status='ACTIVE'";


				
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<StateResponse> list = new ArrayList<StateResponse>();
		StateResponse obj = null;
		for (Object[] columns : rows) {
			obj = new StateResponse();
			obj.setId(UUID.fromString(columns[0].toString()));
			obj.setStateName(columns[1].toString());
			obj.setShortName(columns[2].toString());
			obj.setCountryId(UUID.fromString(columns[3].toString()));
			obj.setCountryName((columns[4].toString()));
			list.add(obj);
		}
		return list;
	}
//	public List<StateEntity> getStatesByCountryId(UUID countryId) {
//		Session session = sessionFactory.getCurrentSession();
//		Criteria crit = session.createCriteria(StateEntity.class);
//		crit.add(Restrictions.eq("countryId", countryId));
//		List<StateEntity> stateList = crit.list();
//		if(null != stateList && !stateList.isEmpty()) {
//			return stateList;
//		}
//		return null;
//	}
	public List<StateEntity> getStatesByCountryId(UUID countryId) {
	    Session session = sessionFactory.getCurrentSession();
	    Criteria crit = session.createCriteria(StateEntity.class);
	    crit.add(Restrictions.eq("countryId", countryId));
	    crit.addOrder(Order.asc("stateName")); 
	    List<StateEntity> stateList = crit.list();
	    if (stateList != null && !stateList.isEmpty()) {
	        return stateList;
	    }
	    return stateList;
	}
	public StateEntity get(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		StateEntity obj = session.get(StateEntity.class, id);
		return obj;
	}
	

	public StateEntity findByNameExcludeId(String stateName, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(StateEntity.class);
		crit.add(Restrictions.eq("stateName", stateName));
		crit.add(Restrictions.ne("id", id));
		List<StateEntity> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}
	

	public void saveAndFlush(StateEntity state) {
		Session session = sessionFactory.getCurrentSession();
		session.update(state);
	}
		
	
	}

