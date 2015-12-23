package com.kashu.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kashu.demo.dao.IRoleDao;
import com.kashu.demo.dao.common.IOperations;
import com.kashu.demo.domain.Role;
import com.kashu.demo.service.IRoleService;
import com.kashu.demo.service.common.AbstractService;

@Service
public class RoleService extends AbstractService<Role> implements IRoleService {
    @Autowired
    private IRoleDao dao;

    public RoleService() {
        super();
    }

    // API

    @Override
    protected IOperations<Role> getDao() {
        return dao;
    }
}
