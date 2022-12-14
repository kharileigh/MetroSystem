package com.five.resource;

import com.five.entity.User;
import com.five.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;


@RestController
public class UserResource {

    @Autowired
    UserService userService;

    @GetMapping(path="/users/{userName}/{userPassword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User loginCheckResource(@PathVariable("userName") String userName, @PathVariable("userPassword") String userPassword) {
        return userService.loginCheck(userName, userPassword);
    }

    @PostMapping(path = "/newUser", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createUserResource(@RequestBody User user) throws SQLIntegrityConstraintViolationException {
        try {
            User result = userService.createUser(user);
            if (result == null)
                return "User was not added.";
            else
                return "User successfully added.";
        } catch (SQLIntegrityConstraintViolationException e) {
            return "User was not added.";
        }
//        if (userService.createUser(user) == null)
//            return "User was not added.";
//        else
//            return "User successfully added";
    }

    @GetMapping(path = "/users/{userId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String balanceCheckResource(@PathVariable("userId") int userId) {
        if (userService.balanceCheck(userId))
            return "Balance is OK";
        else
            return "Balance is below limit";
    }

    @PutMapping(path = "/users/{userId}/{amount}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User updateBalanceResource(@PathVariable("userId") int userId, @PathVariable("amount") BigDecimal amount) {
 
       return userService.updateBalance(userId, amount);
    }

}
