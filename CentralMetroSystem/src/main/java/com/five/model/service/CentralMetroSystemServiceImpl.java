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
        
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
                
            URI url = new URI("http://localhost:8084/newUser");

            
            newUser.setUserName(userName);
            newUser.setUserPassword(userPassword);
            newUser.setUserBalance(userBalance);
            HttpEntity<User> requestEntity = new HttpEntity<>(newUser, headers);

            ResponseEntity<User> responseEntity = restTemplate.postForEntity(url, requestEntity, User.class);


            // check user has been added
            if(!("User not added").equals(responseEntity)) {
                
                return loginCheck(userName, userPassword);
                
            } else {
                
                return null;
                
                // user successfully added - immediately logs user in
            }
            
        } catch (URISyntaxException ex) {
        
        }
        
        return newUser;
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
        
        User user = restTemplate.getForObject("http://localhost:8084/users/" + userId + "/" + amount, User.class);
        
        return user;
    }

    
    @Override
    public double checkRoute(String sourceStation, String destinationStation) {
        
        double route = restTemplate.getForObject("http://localhost:8082/stations/" + sourceStation + "/" + destinationStation, Double.class);
        
        return route;
        
    }
    

    @Override
    public MetroSystem calculateTravelCost(int userId, double starterBalance, double remainingBalance, double price, String sourceStation, String destinationStation, LocalDate sourceSwipeInDateAndTime, LocalDate destinationSwipeOutDateAndTime) {
        
        
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
