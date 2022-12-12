/**
 *
 * @author kharileigh
 */

package com.five.entity;


import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class User {
    
    private int userId;
    private String userName;
    private String userPassword;
    private double userBalance;
    
    // default constructor
    public User() {
    
    }

    //constructor without userId so that the database can auto-increment
    public User(String userName, String userPassword, double userBalance) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userBalance = userBalance;
    }
    
}
