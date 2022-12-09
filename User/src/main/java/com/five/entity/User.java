package com.five.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

	@Id
	private int userId;
	private String userName;
	private String userPassword;
	private BigDecimal userBalance;

	//constructor without userId so that the database can auto-increment
	public User(String userName, String userPassword, BigDecimal userBalance) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.userBalance = userBalance;
	}
}
