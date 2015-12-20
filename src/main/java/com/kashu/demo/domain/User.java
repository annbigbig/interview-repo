package com.kashu.demo.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TB_USER")
public class User implements Serializable {
	private Long id;
	private String username;
	private String password;
	//private Set<Tweet> tweets;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="username")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	/*
	@JsonIgnore
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user",fetch = FetchType.EAGER)
	public Set<Tweet> getTweets() {
		return tweets;
	}
	public void setTweets(Set<Tweet> tweets) {
		this.tweets = tweets;
	}
	*/
}
