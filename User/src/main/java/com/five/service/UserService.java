package com.five.service;

import com.five.entity.User;

import java.math.BigDecimal;


public interface UserService {

    User loginCheck(String userName, String userPassword);

    boolean balanceCheck(int userId);
    
    User updateBalance(int userid, BigDecimal amount);

    User createUser(User user);
}
