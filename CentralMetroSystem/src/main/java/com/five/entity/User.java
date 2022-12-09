/**
 *
 * @author kharileigh
 */

package com.five.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    private int userId;
    private String userName;
    private String userPassword;
    private BigDecimal userBalance;

    //constructor without userId so that the database can auto-increment
    public User(String userName, String userPassword, BigDecimal userBalance) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userBalance = userBalance;
    }
    
}
