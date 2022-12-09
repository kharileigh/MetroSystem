/**
 *
 * @author kharileigh, lidija, vic, stephanie, priyanka
 */
package com.five.model.service;

import com.five.entity.MetroSystem;
import com.five.entity.MetroSystemList;
import com.five.entity.User;
import com.five.model.persistence.CentralMetroSystemDao;
import java.math.BigDecimal;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CentralMetroSystemServiceImpl implements CentralMetroSystemService {
    
    
    // INJECT DEPENDENCY
    @Autowired
    private CentralMetroSystemDao metroDao;
    private RestTemplate restTemplate;
    

    @Override
    public User loginCheck(int userId, String userPassword) {
        
        User user = restTemplate.getForObject("http://localhost:8084/users/" + userId + "/" + userPassword, User.class);
        
        return user;
        
    }
    

    @Override
    public String balanceCheck(int userId) {
        
        String balanceStatus = restTemplate.getForObject("http://localhost:8084/users/" + userId, String.class);
        
        return balanceStatus;
    }
    
    

    @Override
    public User updateBalance(int userId, BigDecimal amount) {
        
        User user = restTemplate.getForObject("http://localhost:8084/users/" + userId + "/" + amount, User.class);
        
        return user;
    }

    
    @Override
    public BigDecimal checkRoute(String sourceStation, String destinationStation) {
        
        BigDecimal route = restTemplate.getForObject("http://localhost:8082/stations/" + sourceStation + "/" + destinationStation, BigDecimal.class);
        
        return route;
        
    }
    

    @Override
    public MetroSystem calculateTravelCost(int userId, BigDecimal starterBalance, BigDecimal remainingBalance, BigDecimal price, String sourceStation, String destinationStation, Date sourceSwipeInDateAndTime, Date destinationSwipeOutDateAndTime) {
        
        
        MetroSystem metroSystem = new MetroSystem();
        
        metroSystem.setUserId(userId);
        metroSystem.setStarterBalance(starterBalance);
        metroSystem.setRemainingBalance(remainingBalance);
        metroSystem.setPrice(price);
        metroSystem.setSourceStation(sourceStation);
        metroSystem.setDestinationStation(destinationStation);
        metroSystem.setSourceSwipeInDateAndTime(sourceSwipeInDateAndTime);
        metroSystem.setDestinationSwipeOutDateAndTime(destinationSwipeOutDateAndTime);
        
        // SAVES TO DATABASE
        metroDao.save(metroSystem);
        
        return metroSystem;
    }

    
    @Override
    public MetroSystemList showTransactionHistory(int userId) {
        
        MetroSystemList metroSystemList = new MetroSystemList();
        
        // SETS FARES AND UPDATES DATABASE
        metroSystemList.setFares(metroDao.searchMetroSystemByUserId(userId));
        
        return metroSystemList;
    }
    
}
