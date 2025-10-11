package com.app.dao;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.entity.Role;
import com.app.enumeration.UserType;

@Repository
public class RoleDAO {
	@Autowired
	private SessionFactory sessionFactory;
	public Role findByNameExcludeId(UserType roleName, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Role.class);
		crit.add(Restrictions.eq("roleName", roleName));
		crit.add(Restrictions.ne("id", id));
		List<Role> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}
}
