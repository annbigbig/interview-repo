package com.kashu.demo.service;

import com.kashu.demo.dao.common.IOperations;
import com.kashu.demo.domain.User;

public interface IUserService extends IOperations<User>{
   User findByUsername(final String username);
}
