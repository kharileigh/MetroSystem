/**
 *
 * @author kharileigh, lidija, vic, stephanie, priyanka
 */
package com.five.model.service;

import com.five.entity.MetroSystem;
import com.five.entity.MetroSystemList;
import com.five.entity.User;
import com.five.model.persistence.CentralMetroSystemDao;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CentralMetroSystemServiceImpl implements CentralMetroSystemService {
    
    
    // INJECT DEPENDENCY
    @Autowired
    private CentralMetroSystemDao metroDao;
    
    @Autowired
    private RestTemplate restTemplate;
    
    
    @Override
    public User addNewUser(String userName, String userPassword, double userBalance) {
           
        User newUser = new User();
               
        newUser.setUserName(userName);
        newUser.setUserPassword(userPassword);
        newUser.setUserBalance(userBalance);


        String message = restTemplate.postForObject("http://localhost:8084/newUser", newUser, String.class);


        // check user has been added
        if(!("User not added").equals(message)) {

            return loginCheck(userName, userPassword);

        } else {

            return null;
        }
       
    }
    

    @Override
    public User loginCheck(String userName, String userPassword) {
        
        User user = restTemplate.getForObject("http://localhost:8084/users/" + userName + "/" + userPassword, User.class);
        
        return user;
        
    }
    

    @Override
    public String balanceCheck(int userId) {
        
        String balanceStatus = restTemplate.getForObject("http://localhost:8084/users/" + userId, String.class);
        
        return balanceStatus;
    }
    
    

    @Override
    public User updateBalance(int userId, double amount) {
        
        HttpHeaders headers = new HttpHeaders();
        
        HttpEntity<User> entity = new HttpEntity<User>(headers);
        
        User user = restTemplate.exchange("http://localhost:8084/users/" + userId + "/" + amount, HttpMethod.PUT, entity, User.class).getBody();
        
        return user;
    }

    
    @Override
    public double checkRoute(String sourceStation, String destinationStation) {
        
        double route = restTemplate.getForObject("http://localhost:8082/stations/" + sourceStation + "/" + destinationStation, Double.class);
        
        return route;
        
    }
    

    @Override
    public MetroSystem saveTransaction(MetroSystem metroSystem, int userId) {
 
        // deducts price from User's current balance
        updateBalance(userId, -metroSystem.getPrice());
        
        // saves to database
        metroDao.save(metroSystem);
        
        // gets single transaction 
        MetroSystem singleTransaction = metroDao.searchMetroSystemByUserIdAndDestinationSwipeOutDateTime(userId, metroSystem.getDestinationSwipeOutDateTime());
        
        singleTransaction.setRemainingBalance(singleTransaction.getStarterBalance() - singleTransaction.getPrice());
        
        return singleTransaction;
    }

    
    @Override
    public MetroSystemList showTransactionHistory(int userId) {
        
        MetroSystemList metroSystemList = new MetroSystemList();
        
        // SETS FARES AND UPDATES DATABASE
        metroSystemList.setFares(metroDao.searchMetroSystemByUserId(userId));
        
        return metroSystemList;
    }

    
    
}
