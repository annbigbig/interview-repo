package com.kashu.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kashu.demo.dao.IUserDao;
import com.kashu.demo.dao.common.IOperations;
import com.kashu.demo.domain.User;
import com.kashu.demo.service.IUserService;
import com.kashu.demo.service.common.AbstractService;

public class UserService extends AbstractService<User> implements IUserService {
    @Autowired
    private IUserDao dao;

    public UserService() {
        super();
    }

    // API

    @Override
    protected IOperations<User> getDao() {
        return dao;
    }
}
