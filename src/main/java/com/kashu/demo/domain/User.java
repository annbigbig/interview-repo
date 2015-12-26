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
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="TB_USER")
public class User implements Serializable {
	private Long id;
	private String username;
	private String password;
	private boolean enabled;
	private Set<Tweet> tweets;
	private Set<Role> roles;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="username",unique=true)
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
	@Column(name="enabled",columnDefinition = "tinyint default true")
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	@JsonIgnore
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.EAGER,mappedBy="user")
	public Set<Tweet> getTweets() {
		return tweets;
	}
	public void setTweets(Set<Tweet> tweets) {
		this.tweets = tweets;
	}
	@JsonIgnore
	@OneToMany(cascade={CascadeType.PERSIST,CascadeType.REMOVE},fetch = FetchType.EAGER,mappedBy="user")
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
