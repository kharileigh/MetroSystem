package com.five.client;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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

import com.five.entity.Station;
import com.five.entity.StationList;
import com.five.persistence.StationDao;
import com.five.service.StationServiceImpl;

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
class StationApplicationTests {

	//declare and instantiate a new 'StationServiceImpl' object:
	@InjectMocks
	private StationServiceImpl stationServiceImpl;
	
	//declare and instantiate a new 'StationDao' object:
	@Mock
	private StationDao stationDao;

	//declare and initialise a new 'AutoCloseable' object called 'autoC':
	private AutoCloseable autoC;
	
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
	
	//TESTING THE 'GET ALL STATIONS' METHOD:
	@DisplayName("1. Testing 'getAllStations' - true")
	@Test
	void testGetAllStationsTrue() {
		List<Station> stationList = new ArrayList<>();
		
		stationList.add(new Station(1, "Bank"));
		stationList.add(new Station(2, "Lewisham"));
		stationList.add(new Station(3, "Croydon"));
		stationList.add(new Station(4, "Harlow"));
		stationList.add(new Station(5, "Euston"));
		
		when(stationDao.findAll()).thenReturn(stationList);
		
		assertEquals(stationList, stationServiceImpl.getAllStations().getStations());
	}
	
	//TESTING THE 'GET ALL STATIONS' METHOD - passed:
	@DisplayName("2. Testing 'getAllStations' - false")
	@Test
	void testGetAllStationsFalse() {
		StationList stationList = new StationList();
		when(stationDao.findAll()).thenReturn(null);
		assertEquals(stationList, stationServiceImpl.getAllStations());
	}
	
	//TESTING THE 'GET STATION BY STATION NAME' METHOD:
	@DisplayName("3. Testing 'getStationByStationName' - true")
	@Test
	void testGetStationByStationNameTrue() {
		Station station = new Station(2, "Lewisham");
		when(stationDao.getStationByStationName("Lewisham")).thenReturn(station);
		assertEquals(station, stationServiceImpl.getStationByStationName("Lewisham"));
	}
	
//	"Testing 'getStationByStationName' - false" - not needed due to the way our function is presented in web
	
	//TESTING 'CHECK ROUTE' METHOD:
	@DisplayName("4. Testing 'checkRoute' - true")
	@Test
	void testCheckRouteTrue() {
		Station sourceStation = new Station(1, "Bank");
		Station destinationStation = new Station(3, "Croydon");
		
		when(stationDao.getStationByStationName("Bank")).thenReturn(sourceStation);
		when(stationDao.getStationByStationName("Croydon")).thenReturn(destinationStation);
		
		BigDecimal result;
		 
		result = stationServiceImpl.checkRoute("Bank", "Croydon");
		
		String sResult = String.valueOf(result);
				
		BigDecimal expected = new BigDecimal(3);
		
		String sExpected = String.valueOf(expected) + ".00";
		
		assertEquals(sResult, sExpected);
	}
	
//	Testing 'checkRoute' - false" - not needed due to the way our function is presented in web
	
}
