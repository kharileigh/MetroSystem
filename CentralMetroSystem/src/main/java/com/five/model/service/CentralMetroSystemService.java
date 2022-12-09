/**
 *
 * @author kharileigh, lidija, stephanie, priyanka, vic
 */

package com.five.model.service;

import com.five.entity.MetroSystem;
import com.five.entity.MetroSystemList;
import com.five.entity.User;
import java.math.BigDecimal;
import java.util.Date;


public interface CentralMetroSystemService {
    
    public User loginCheck(int userId, String userPassword);
    
    public String balanceCheck(int userId);
    
    public User updateBalance(int userId, BigDecimal amount);
    
    // RETURNS PRICE OF ROUTE
    public BigDecimal checkRoute(String sourceStation, String destinationStation);
    
    public MetroSystem calculateTravelCost(int userId, BigDecimal starterBalance, BigDecimal remainingBalance, BigDecimal price, String sourceStation, String destinationStation, Date sourceSwipeInDateAndTime, Date destinationSwipeOutDateAndTime);
    
    public MetroSystemList showTransactionHistory(int userId);
    
}
