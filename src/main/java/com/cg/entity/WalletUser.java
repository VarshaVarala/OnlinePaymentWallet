package com.cg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
	@Table(name="WalletUser")

	public class WalletUser {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(length=10)
	private Integer user_Id;
	@Column(length=10)
	private String user_Name;
	@Column(length=10)
	private String password;
	@Column(length=10)
	private String phone_Number;
	@Column(length=10)
	private String login_Name;
	
	
    public Integer getUser_Id() {
		return user_Id;
	}
	public void setUser_Id(Integer user_Id) {
		this.user_Id = user_Id;
	}
	public String getUser_Name() {
		return user_Name;
	}
	public void setUser_Name(String user_Name) {
		this.user_Name = user_Name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone_Number() {
		return phone_Number;
	}
	public void setPhone_Number(String phone_Number) {
		this.phone_Number = phone_Number;
	}
	public String getLogin_Name() {
		return login_Name;
	}
	public void setLogin_Name(String login_Name) {
		this.login_Name = login_Name;
	}

	}
