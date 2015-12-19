package com.kashu.demo.dao.impl;

import org.springframework.stereotype.Repository;
import com.kashu.demo.domain.Tweet;
import com.kashu.demo.dao.common.AbstractHibernateDao;
import com.kashu.demo.dao.ITweetDao;

@Repository
public class TweetDao extends AbstractHibernateDao<Tweet> implements ITweetDao {
	
	public TweetDao(){
		super();
		setClazz(Tweet.class);
	}

}
