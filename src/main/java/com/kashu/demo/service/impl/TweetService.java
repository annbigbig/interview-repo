package com.kashu.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.kashu.demo.dao.ITweetDao;
import com.kashu.demo.dao.common.IOperations;
import com.kashu.demo.domain.Tweet;
import com.kashu.demo.service.ITweetService;
import com.kashu.demo.service.common.AbstractService;

public class TweetService extends AbstractService<Tweet> implements ITweetService {
    @Autowired
    private ITweetDao dao;

    public TweetService() {
        super();
    }

    // API

    @Override
    protected IOperations<Tweet> getDao() {
        return dao;
    }
}
