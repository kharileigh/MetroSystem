package com.five.service;

import com.five.entity.User;
import com.five.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;


    @Override
    public boolean loginCheck(int userId, String userPassword) {
        try {
            User user = userDao.getUserByUserIdAndUserPassword(userId, userPassword);
            if (user != null)
                return true;
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean balanceCheck(int userId) {
        User user = userDao.findById(userId).orElse(null);
        if (user.getUserBalance() >= 6)
            return true;
        else
            return false;
    }

    @Override
    public User updateBalance(int userId, double amount) {
        User user = userDao.getById(userId);
        user.setUserBalance(user.getUserBalance() + amount);
        userDao.save(user);
        return user;
    }
}
