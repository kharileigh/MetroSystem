package com.five.service;

import com.five.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface UserService {

    boolean loginCheck(int userId, String userPassword);

    boolean balanceCheck(int userId);
    User updateBalance(int userid, double price);
}
