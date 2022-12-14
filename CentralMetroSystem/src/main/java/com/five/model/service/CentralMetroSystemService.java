/**
 *
 * @author kharileigh, lidija, stephanie, priyanka, vic
 */

package com.five.model.service;

import com.five.entity.MetroSystem;
import com.five.entity.MetroSystemList;
import com.five.entity.Station;
import com.five.entity.StationList;
import com.five.entity.User;

import java.math.BigDecimal;


public interface CentralMetroSystemService {
    
    public User loginCheck(String userName, String userPassword);
    
    public User addNewUser(String userName, String userPassword, BigDecimal userBalance);
    
    public String balanceCheck(int userId);
    
    public User updateBalance(int userId, BigDecimal amount);

    public User updateBalancePositiveOnly(int userId, BigDecimal amount);
    // RETURNS PRICE OF ROUTE
    public BigDecimal checkRoute(String sourceStation, String destinationStation);
    
    // SAVES FULL OBJECT TO DATABASE
    public MetroSystem saveTransaction(MetroSystem metroSystem, int userId);
    
    public MetroSystemList showTransactionHistory(int userId);
    
    public StationList showAllStations();
    
    public Station getStationByStationName(String stationName);
    
}
