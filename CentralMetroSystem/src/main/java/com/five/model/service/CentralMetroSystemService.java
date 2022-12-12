/**
 *
 * @author kharileigh, lidija, stephanie, priyanka, vic
 */

package com.five.model.service;

import com.five.entity.MetroSystem;
import com.five.entity.MetroSystemList;
import com.five.entity.User;
import java.time.LocalDate;
import java.util.Date;


public interface CentralMetroSystemService {
    
    public User loginCheck(String userName, String userPassword);
    
    public User addNewUser(String userName, String userPassword, double userBalance);
    
    public String balanceCheck(int userId);
    
    public User updateBalance(int userId, double amount);
    
    // RETURNS PRICE OF ROUTE
    public double checkRoute(String sourceStation, String destinationStation);
    
    public MetroSystem calculateTravelCost(int userId, double starterBalance, double remainingBalance, double price, String sourceStation, String destinationStation, LocalDate sourceSwipeInDateAndTime, LocalDate destinationSwipeOutDateAndTime);
    
    public MetroSystemList showTransactionHistory(int userId);
    
}
