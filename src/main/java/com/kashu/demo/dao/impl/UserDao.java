package com.kashu.demo.dao.impl;

import org.springframework.stereotype.Repository;

import com.kashu.demo.domain.User;
import com.kashu.demo.dao.common.AbstractHibernateDao;
import com.kashu.demo.dao.IUserDao;

@Repository
public class UserDao extends AbstractHibernateDao<User> implements IUserDao {
	
	public UserDao(){
		super();
		setClazz(User.class);
	}

	@Override
	public User findByUsername(String username) {
		return (User) getCurrentSession().createQuery("from User where username=:username").setString("username", username).uniqueResult();
	}

}
