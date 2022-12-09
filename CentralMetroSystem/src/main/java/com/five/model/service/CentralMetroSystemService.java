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
    
    public User updateBalance(int userId, double amount);
    
    // RETURNS PRICE OF ROUTE
    public double checkRoute(String sourceStation, String destinationStation);
    
    public MetroSystem calculateTravelCost(int userId, double starterBalance, double remainingBalance, double price, String sourceStation, String destinationStation, Date sourceSwipeInDateAndTime, Date destinationSwipeOutDateAndTime);
    
    public MetroSystemList showTransactionHistory(int userId);
    
}
