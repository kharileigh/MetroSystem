package com.five.service;

import com.five.entity.User;


public interface UserService {

    User loginCheck(String userName, String userPassword);

    boolean balanceCheck(int userId);
    
    User updateBalance(int userid, double amount);

    User createUser(User user);
}
