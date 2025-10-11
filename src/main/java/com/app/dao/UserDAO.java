package com.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dto.UserResposeDTO;
import com.app.entity.User;

@Repository
public class UserDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public List<UserResposeDTO> findAll() {
		
	
			Session session = sessionFactory.getCurrentSession();
			String sqlQuery = "SELECT u.id,u.full_name,u.user_name,u.phone_no,u.email,u.address_line,u.role_id,r.role_name  FROM tb_users u "
					+ "	INNER JOIN tb_roles r ON r.id=u.role_id WHERE u.status='ACTIVE'";

			Query query = session.createSQLQuery(sqlQuery);
			List<Object[]> rows = query.list();
			List<UserResposeDTO> list = new ArrayList<UserResposeDTO>();
			UserResposeDTO obj = null;
			for (Object[] row : rows) {
				obj = new UserResposeDTO();
				obj.setId(UUID.fromString(row[0].toString()));
				obj.setFullName(row[1].toString());  
				obj.setUserName(row[2].toString());
				obj.setEmail(row[4].toString());
				obj.setPhoneNo(row[3].toString());
				obj.setAddress(row[5].toString());
				obj.setUserRoleId(UUID.fromString(row[6].toString()));
				obj.setRoleName(row[7].toString());
				list.add(obj);
			}
			
			return list;
		}
	public List<UserResposeDTO> findById(UUID id) {
		
		
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT u.id,u.full_name,u.user_name,u.phone_no,u.email,u.address_line,u.role_id,r.role_name  FROM tb_users u "
				+ "	INNER JOIN tb_roles r ON r.id=u.role_id WHERE u.id='"+id+"'";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<UserResposeDTO> list = new ArrayList<UserResposeDTO>();
		UserResposeDTO obj = null;
		for (Object[] row : rows) {
			obj = new UserResposeDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setFullName(row[1].toString());  
			obj.setUserName(row[2].toString());
			obj.setEmail(row[4].toString());
			obj.setPhoneNo(row[3].toString());
			obj.setAddress(row[5].toString());
			obj.setUserRoleId(UUID.fromString(row[6].toString()));
			obj.setRoleName(row[7].toString());
			list.add(obj);
		}
		
		return list;
	}
	public User findByUserName(String userName) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("userName", userName));
		List<User> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}
	public User findByUser(String userName, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("userName", userName));
		crit.add(Restrictions.ne("id", id));
		List<User> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}
	public User findByUserPhone(String phoneNo, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("phoneNo", phoneNo));
		crit.add(Restrictions.ne("id", id));
		List<User> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}
	public User findByPhone(String phoneNo) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("phoneNo", phoneNo));
		List<User> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}
	public User getUserByUserNameOrEmail(String userNameOrEmail) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(User.class);
		LogicalExpression orExp = Restrictions.or(Restrictions.eq("userName", userNameOrEmail),
				Restrictions.eq("email", userNameOrEmail));
		crit.add(orExp);
		List<User> userList = crit.list();
		if (null != userList && !userList.isEmpty()) {
			return userList.get(0);
		}
		return null;
	}
	public User findByUserEmail(String email, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(User.class);
		crit.add(Restrictions.eq("email", email));
		crit.add(Restrictions.ne("id", id));
		List<User> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}
}
