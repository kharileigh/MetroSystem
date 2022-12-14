package com.five.service;

import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


import com.five.entity.User;
import com.five.persistence.UserDao;
import com.five.service.UserServiceImpl;

//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserApplicationTests {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserDao userDao;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() throws Exception {

        /*
         * tells mockito to scan the test class instance
         * for all the fields annotated with @Mock
         * and initialize those fields as mocks
         *
         */
        autoCloseable=MockitoAnnotations.openMocks(this);
    }


    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }


    @Test
    void testUserLoginCheckOne() {
        //Specifying the behavior of the mock

        User user = new User( "Alison", "hello", new BigDecimal("5"));

        user.setUserId(1);

        when(userDao.getUserByUserNameAndUserPassword("Alison", "hello")).thenReturn(user);

        assertEquals(user, userServiceImpl.loginCheck("Alison", "hello"));
    }


    @Test
    void testUserLoginCheckTwo() {
        //Specifying the behavior of the mock

        User user = new User( "Alis", "hel", new BigDecimal("5"));

        when(userDao.getUserByUserNameAndUserPassword("Alis", "hel")).thenReturn(null);

        assertNull(userServiceImpl.loginCheck("Alis", "hel"));
    }



    @Test
    void testBalanceCheckOne() {

        User user = new User(1, "Alison", "hello", new BigDecimal("5"));

        when(userDao.findById(1)).thenReturn(Optional.ofNullable(user));

        assertFalse(userServiceImpl.balanceCheck(1));
    }


    @Test
    void testBalanceCheckTwo() {

        User user = new User(2, "Khari", "password", new BigDecimal("7"));

        when(userDao.findById(2)).thenReturn(Optional.ofNullable(user));

        assertTrue(userServiceImpl.balanceCheck(2));
    }

    @Test
    void testUpdateBalanceOne() {

        User user = new User(1, "Alison", "hello", new BigDecimal("10"));

        when(userDao.getById(1)).thenReturn(user);
        assertEquals(user, userServiceImpl.updateBalance(1, new BigDecimal("5")));

    }

    @Test
    void testCreateUserOne() throws SQLIntegrityConstraintViolationException {

        User user = new User(1, "Alison", "hello", new BigDecimal("5"));

        when(userDao.getUserByUserName("Alison")).thenReturn(user);
        assertEquals(null, userServiceImpl.createUser(user));

    }

    @Test
    void testCreateUserTwo() throws SQLIntegrityConstraintViolationException {

        User user = new User(8, "Ali", "hi", new BigDecimal("5"));

        when(userDao.getUserByUserName("Ali")).thenReturn(null);
        assertEquals(user, userServiceImpl.createUser(user));

    }


    @Test
    void contextLoads() {
    }

}