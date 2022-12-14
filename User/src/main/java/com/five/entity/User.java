package com.five.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@Entity
public class User {

	@Id
	private int userId;
	private String userName;
	private String userPassword;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal userBalance;
        
        // default constructor
        public User() {
        
        }

	//constructor without userId so that the database can auto-increment
	public User(String userName, String userPassword, BigDecimal userBalance) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.userBalance = userBalance;
	}


}
