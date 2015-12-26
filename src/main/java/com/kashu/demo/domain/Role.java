package com.kashu.demo.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="TB_ROLE",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role"}))
public class Role implements Serializable {
	
	private Long id;
	private User user;
	private String role;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name="user_id")
	//@JoinColumn(name="user_id",nullable=false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name="role")
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
