package com.kashu.demo.dao.impl;

import org.springframework.stereotype.Repository;

import com.kashu.demo.dao.IRoleDao;
import com.kashu.demo.dao.common.AbstractHibernateDao;
import com.kashu.demo.domain.Role;

@Repository
public class RoleDao extends AbstractHibernateDao<Role> implements IRoleDao {
	
	public RoleDao(){
		super();
		setClazz(Role.class);
	}
	
}
