package com.five.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.five.entity.MetroSystem;
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
        
        //the remaining balance from user.3 should be £9
        BigDecimal balance = new BigDecimal("9");
        
        		
        //check that the remaining balance was set correctly
        when(restTemplate.getForEntity("http://localhost:8084/users/" + 3 + "/" + price.negate(), HttpMethod.PUT, User.class).getBody()).thenReturn(3, price.negate()));
//        when(metroDao.searchMetroSystemByUserIdAndDestinationSwipeOutDateTime(3, outDateTime)).thenReturn(metroSystem);
//        assertEquals(balance, metroService.saveTransaction(metroSystem, 3).getRemainingBalance());
	}
	
	//TESTING THE 'SHOW TRANSACTION HISTORY' METHOD:
	//TESTING THE 'GET ALL STATIONS' METHOD:
}
