package com.five.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.five.entity.MetroSystem;
import com.five.entity.MetroSystemList;
import com.five.entity.User;
import com.five.model.persistence.CentralMetroSystemDao;
import com.five.model.service.CentralMetroSystemServiceImpl;

/**
*
* @author Vic, Khari, Steph, Lidjia, Priyanka
*
*/

//@SpringBootTest
//can use '@DisplayName()' to name the test sequences:
@DisplayName("Testing StationService with Mockito and Annotations")
//use '@RunWith' to initialise and allow the explicit usage of MockitoAnnotations:
@RunWith(MockitoJUnitRunner.class)
class ClientApplicationTests {

	@InjectMocks
	private CentralMetroSystemServiceImpl metroService;
	
	@Mock
	private CentralMetroSystemDao metroDao;

	private AutoCloseable autoC;
	
	@Mock
    private RestTemplate restTemplate;
	
	//the first run in the sequence:
	@BeforeEach
	void setUp() throws Exception {
		autoC = MockitoAnnotations.openMocks(this);
	}
	
	//the last run in the sequence:
	@AfterEach
	void tearDown() throws Exception {
		//use 'autoC.close()' to close the resources:
		autoC.close(); 
	}
	
	//TESTING THE 'SAVE TRANSACTION' METHOD:
	@DisplayName("1. Testing 'saveTransaction' - true")
	@Test
	void testSaveTransationTrue() {
		MetroSystem metroSystem = new MetroSystem();
		
		MetroSystem metroSystem2 = new MetroSystem();
		
		//set source station
		metroSystem.setSourceStation("Bank");
		
		//set balance
		metroSystem.setStarterBalance(new BigDecimal("12"));
		
		//set source swipe in date and time
		LocalDateTime inLocalDateTime = LocalDateTime.now();
        String inDateTime = inLocalDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
		
		metroSystem.setSourceSwipeInDateTime(inDateTime);
		
		//set userID
		metroSystem.setUserId(3);
		
		//set destination station
		metroSystem.setDestinationStation("Croydon");
		
		//set destination swipe out date and time:
		LocalDateTime outLocalDateTime = LocalDateTime.now();
        String outDateTime = outLocalDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
		
        metroSystem.setDestinationSwipeOutDateTime(outDateTime);
        
        //set the price:
        BigDecimal price = new BigDecimal("3");
        
        metroSystem.setPrice(price);
        
        metroSystem2 = metroSystem;
        
        //the remaining balance from user.3 should be £9
        BigDecimal balance = new BigDecimal("9");
        
        //set the remaining balance into the other metroSystem object
        metroSystem2.setRemainingBalance(balance);
        
        //set update balance into a variable
        metroService.updateBalance(3, price.negate());
        
        HttpHeaders headers = new HttpHeaders();
        
        HttpEntity<User> entity = new HttpEntity<User>(headers);
        
        User user = new User();
        user.setUserId(3);
        user.setUserName("Lidija");
        user.setUserBalance(balance);
        
        //check that the remaining balance was set correctly
        when(restTemplate.exchange("http://localhost:8084/users/" + 3 + "/" + price, HttpMethod.PUT, entity, User.class).getBody()).thenReturn(user);
        when(metroDao.searchMetroSystemByUserIdAndDestinationSwipeOutDateTime(3, outDateTime)).thenReturn(metroSystem2);
        assertEquals(balance, metroService.saveTransaction(metroSystem, 3).getRemainingBalance());
	}
	
		//TESTING THE 'SHOW TRANSACTION HISTORY' METHOD:
		@DisplayName("2. Testing 'showTransactionHistory'")
		@Test
		void testShowTransactionHistory() {
			List<MetroSystem> metroSystemList = new ArrayList<>();
			
			 BigDecimal starterBalance = new BigDecimal("12");
			 BigDecimal remainingBalance = new BigDecimal("9");
			 BigDecimal price = new BigDecimal("3");
			 
			 LocalDateTime inLocalDateTime = LocalDateTime.now();
		     String inDateTime = inLocalDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
		     
		     LocalDateTime outLocalDateTime = LocalDateTime.now();
		     String outDateTime = outLocalDateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
			
		     metroSystemList.add(new MetroSystem(1, 3, starterBalance, remainingBalance, price, "Bank", "Croydon", inDateTime, outDateTime));
		     
		     MetroSystemList metroSystemLists = new MetroSystemList();
		     metroSystemLists.setFares(metroSystemList);
		     
		     //check the show transaction methods:
		     when(metroDao.searchMetroSystemByUserId(3)).thenReturn(metroSystemList);
		     assertEquals(metroSystemLists, metroService.showTransactionHistory(3));
	
	}
}
