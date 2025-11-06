package com.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dto.CountryDTO;
import com.app.entity.Country;


@Repository
public class CountryDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public Country get(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		 Country obj = session.get(Country.class, id);
		 return obj;
	}

	public Country findByNameExcludeId(String countryName, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Country.class);
		crit.add(Restrictions.eq("countryName", countryName));
		crit.add(Restrictions.ne("id", id));
		List<Country> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}

	public void saveOrUpdate(Country country) {
		Session session = sessionFactory.getCurrentSession();
		session.update(country);
		
	}

	public Country findByCodeExcludeId(String countryCode, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Country.class);
		crit.add(Restrictions.eq("countryCode", countryCode));
		crit.add(Restrictions.ne("id", id));
		List<Country> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}
	public Object findAll() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT c.id,c.name,c.short_name,c.country_code "
				+" FROM tb_country c"
				+" ORDER BY c.name ASC" ;

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CountryDTO> list = new ArrayList<CountryDTO>();
		CountryDTO obj = null;
		for (Object[] row : rows) {
			obj = new CountryDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setCountryName(row[1].toString());
			obj.setShortName(row[2].toString());
			obj.setCountryCode(row[3].toString());
			list.add(obj);
		}
		
		return list;
	}
}
