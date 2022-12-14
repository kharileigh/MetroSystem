package com.five.service;

import com.five.entity.User;
import com.five.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    private static final BigDecimal MINIMUM_BALANCE = new BigDecimal("6.00");



    @Override
    public User loginCheck(String userName, String userPassword) {
        try {
            User user = userDao.getUserByUserNameAndUserPassword(userName, userPassword);
            if (user != null)
                return user;
            return null;
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public boolean balanceCheck(int userId) {
        User user = userDao.findById(userId).orElse(null);
        if (user.getUserBalance().compareTo(MINIMUM_BALANCE) >= 0)
            return true;
        else
            return false;
    }

    @Override
    public User updateBalance(int userId, BigDecimal amount) {
        User user = userDao.getById(userId);
        
        if (user != null) {
            user.setUserBalance(user.getUserBalance().add(amount));
            userDao.save(user);
            return user;
            
        } else {
            return null;
        }
    }

    @Override
    public User createUser(User user) throws SQLIntegrityConstraintViolationException {
        if (getUserByUserName(user.getUserName()) == null) {
            userDao.save(user);
            return user;
        } else {
            return null;
        }
    }

    @Override
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

}
