package com.five.service;

import com.five.entity.User;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;


public interface UserService {

    User loginCheck(String userName, String userPassword);

    boolean balanceCheck(int userId);
    
    User updateBalance(int userid, BigDecimal amount);

    User createUser(User user) throws SQLIntegrityConstraintViolationException;

    public User getUserByUserName(String userName);

}
