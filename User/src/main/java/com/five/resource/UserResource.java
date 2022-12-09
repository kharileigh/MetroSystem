package com.five.resource;

import com.five.entity.User;
import com.five.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class UserResource {

    @Autowired
    UserService userService;

    @GetMapping(path="/users/{userId}/{userPassword}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User loginCheckResource(@PathVariable("userId") int userId, @PathVariable("userPassword") String userPassword) {
        return userService.loginCheck(userId, userPassword);
    }

    @PostMapping(path = "/users/", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String createUserResource(@RequestBody User user) {
        if (userService.createUser(user) == null)
            return "User was not added.";
        else
            return "User successfully added";
    }

    @GetMapping(path = "/users/{userId}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String balanceCheckResource(@PathVariable("userId") int userId) {
        if (userService.balanceCheck(userId))
            return "Balance is OK";
        else
            return "Balance is below limit";
    }

    @PutMapping(path = "/users/{userId}/{amount}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String updateBalanceResource(@PathVariable("userId") int userId, @PathVariable("amount") double amount) {
        if (userService.updateBalance(userId, amount))
            return "Balance successfully updated";
        else
            return "Something went wrong.";
    }

}
