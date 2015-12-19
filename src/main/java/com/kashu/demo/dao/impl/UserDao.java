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

}
