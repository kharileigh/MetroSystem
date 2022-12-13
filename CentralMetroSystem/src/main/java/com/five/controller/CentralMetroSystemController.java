/**
 *
 * @author kharileigh, lidija, vic, stephanie, priyanka
 */

package com.five.controller;

import com.five.entity.MetroSystem;
import com.five.entity.Station;
import com.five.entity.StationList;
import com.five.entity.User;
import com.five.model.service.CentralMetroSystemService;
import java.time.LocalDateTime;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CentralMetroSystemController {
    
    @Autowired
    CentralMetroSystemService metroSystemService;
    
    //String start;
    //String stop;
    
 
    
    
    //----- LANDING PAGE
    @RequestMapping("/")
    public ModelAndView getLandingPage() {
    	return new ModelAndView("LandingPage");
    }
    
    
    //==========================================================================
    //----- LOGIN PAGE
    @RequestMapping("/loginPage")
    public ModelAndView loginPageController() {
    
        return new ModelAndView("Login");
    }
    
    // TO BE DONE
    @RequestMapping("/login")
    public ModelAndView loginController(@RequestParam("userName") String userName, @RequestParam("userPassword") String password, HttpSession session) {
    
        ModelAndView modelAndView = new ModelAndView();
        
        User user = metroSystemService.loginCheck(userName, password);
        
        String message;
        
        // successful login
        if(user != null) {
            
            modelAndView.setViewName("HomePage");
            modelAndView.addObject("user", user);
            session.setAttribute("user", user);
        
        // failed login
        } else {
        
            message = "Incorrect login details, please try again";
            modelAndView.addObject("message", message);
            modelAndView.setViewName("Login");
       
        }
            
        return modelAndView;
    }

    
    //==========================================================================
    //------ CREATE NEW USER PAGE
    @RequestMapping("/newUserPage")
    public ModelAndView newUserPageController() {
    
        return new ModelAndView("NewUser");
    }
    
    // TO BE DONE
    @RequestMapping("/newUser")
    public ModelAndView newUserController(@RequestParam("userName") String userName, @RequestParam("userPassword") String userPassword, @RequestParam("userBalance") String userBalance, HttpSession session) {
    
        ModelAndView modelAndView = new ModelAndView();
        
        User newUser = new User();
        
        newUser.setUserName(userName);
        newUser.setUserPassword(userPassword);
        
        Double balance = Double.parseDouble(userBalance);
        newUser.setUserBalance(balance);
        
        metroSystemService.addNewUser(newUser.getUserName(), newUser.getUserPassword(), newUser.getUserBalance());
        
        String message;
        
        // successfully added a new user
        if(newUser != null) {
            
            modelAndView.setViewName("HomePage");
            modelAndView.addObject("newUser", newUser);
            session.setAttribute("newUser", newUser);
        
        // falls to create new user
        } else {
        
            message = "Failed to Create New User, please try new details";
            modelAndView.addObject("message", message);
            modelAndView.setViewName("NewUser");
        
        }
        
    
        return modelAndView;
    }
    
    
    //==========================================================================
     //------ HOMEPAGE
    @RequestMapping("/home")
    public ModelAndView HomePageController() {
    
        return new ModelAndView("HomePage");
    }
    
    
    // NEED TO FIGURE OUT SETTING DATE & TIME & PRICE
    //==========================================================================
    //-------- SWIPES IN PAGE
    @RequestMapping("/swipesInPage")
    public ModelAndView swipepesInPageController() {
        
        ModelAndView modelAndView = new ModelAndView();
        
        StationList stations = new StationList();
        
        stations = metroSystemService.showAllStations();
        
        List<Station> allStations = stations.getStations();
        
        modelAndView.addObject("allStations", allStations);
        modelAndView.setViewName("SwipesInPage");
        
    	return modelAndView;
    }
    
    
    //--------- SWIPES IN METHOD
    @RequestMapping("/swipesIn")
    public ModelAndView swipesInController(@RequestParam("stationName") String stationName,  HttpSession session) {
    	MetroSystem metroSystem =  new MetroSystem();
    	
    	ModelAndView modelAndView = new ModelAndView();
        
        // get DateTime and format it to full localized format eg : Sunday, February 7, 2010
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTime = localDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
        
        // gets User from session
        User user = (User)session.getAttribute("user");
    	
    	String message = null;
    	
    	if(metroSystemService.balanceCheck(user.getUserId()).equals("Balance is below limit")) {	
            message = "The amount" + user.getUserBalance() + " is less than required";
            
    	} else {
    		String start = stationName;
    		
                // set Metro System entity - Source Station
    		metroSystem.setSourceStation(start);
    	
    		message = "You have successfully swiped in at : " + start;
                
                // set Metro System entity - Starter Balance 
                metroSystem.setStarterBalance(user.getUserBalance());
                
                // set Metro System entity - Source Date & Time
                metroSystem.setSourceSwipeInDateTime(dateTime);
                
                // set Metro System entity - userId
                metroSystem.setUserId(user.getUserId());
    		
        }
        
        modelAndView.addObject("message", message);
        modelAndView.addObject("metroSystem", metroSystem);
        session.setAttribute("metroSystem", metroSystem);
        
        // populating stations into dropdown menu
        StationList stations = new StationList();
        
        stations = metroSystemService.showAllStations();
        
        List<Station> allStations = stations.getStations();
        
        modelAndView.addObject("allStations", allStations);

        modelAndView.setViewName("SwipesOutPage");

        return modelAndView;
    }
    
    
    
    // NEED TO FIGURE OUT SETTING DATE & TIME & PRICE - MUST REDIRECT TO USER TRANSACTION PAGE (breakdown of journey and cost)
    //==========================================================================
    //--------- SWIPES OUT PAGE
    @RequestMapping("/swipesOutPage")
    public ModelAndView swipepesOutPageController() {
        
    	return new ModelAndView("SwipesOutPage");
    }
    
    //--------- SWIPES OUT METHOD
    // NEED TO MAP DATA OF TRANSACTION OBJECT TO PRINT USER TICKET VIEW
    @RequestMapping("/swipesOut")
    public ModelAndView swipesOutController(@RequestParam("stationName") String stationName, HttpSession session) {
    	
        // getting Metro System source data from session
        MetroSystem metroSystem = (MetroSystem)session.getAttribute("metroSystem");
        
        // getting User from session
        User user = (User)session.getAttribute("user");
        
    	ModelAndView modelAndView = new ModelAndView();
        
        // get DateTime and format it to full localized format eg : Sunday, February 7, 2010
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTime = localDateTime.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    	
    	String message = null;
    		
        String stop = stationName;

        // set Metro System entity - Source Date & Time, Destination Station
        metroSystem.setDestinationStation(stop);
        metroSystem.setDestinationSwipeOutDateTime(dateTime);
        metroSystem.setSourceStation(metroSystem.getSourceStation());
        

        // set Metro System entity - Price
        Double price = metroSystemService.checkRoute(metroSystem.getSourceStation(), stop);
        
        metroSystem.setPrice(price);

        // calculates single transaction of User's journey
        MetroSystem transaction = metroSystemService.saveTransaction(metroSystem, user.getUserId());

        message = "You have successfully swiped out at " + stop + " with current balance :  " +  metroSystem.getRemainingBalance();


        modelAndView.addObject("message", message);
        modelAndView.addObject("transactions", transaction);
        session.setAttribute("user", user);
        session.setAttribute("transactions", transaction);
        modelAndView.setViewName("PrintUserTicket");
        return modelAndView;
    
    }   

    
    //==========================================================================
    // TOP UP BALANCE PAGE
    @RequestMapping("/topUpBalancePage")
    public ModelAndView topUpBalancePageController() {
        
        return new ModelAndView("TopUpBalancePage");
    }
    
    // TOP UP BALANCE METHOD
    @RequestMapping("/topUpBalance")
    public ModelAndView topUpBalanceController(@RequestParam("amount") double amount, HttpServletRequest request, HttpSession session) {
        
        ModelAndView modelAndView = new ModelAndView();
        
        // getting User from session
        User user = (User)session.getAttribute("user");
        
        String message = null;
        
        if(metroSystemService.updateBalance(user.getUserId(), amount) != null) {
        
            message = "Your Balance has now been increased by " + amount;
            
        } else {
        
            message = "Failed to top up your balance, please try again";
        }
        
        user.setUserBalance(amount);
        session.setAttribute("user", user);
        modelAndView.addObject("message", message);
        modelAndView.setViewName("Output");
        
        return modelAndView;
        
    }

    
    //==========================================================================
    //------- SHOW CURRENT BALANCE
    @RequestMapping("/showCurrentBalancePage")
    public ModelAndView showCurrentBalancePageController() {
    
        return new ModelAndView("ShowCurrentBalance");
    }
    
    //------- SHOW CURRENT BALANCE
    @RequestMapping("/showCurrentBalance")
    public ModelAndView showCurrentBalanceController(HttpSession session) {
    
        ModelAndView modelAndView = new ModelAndView();
        
        // getting User from session
        User user = (User)session.getAttribute("user");
        
        user.getUserBalance();
        
        modelAndView.addObject("user", user);
        modelAndView.setViewName("ShowCurrentBalance");
        
        return modelAndView;
    }
    
    
    
    //==========================================================================
    //------ PRINT USER TICKET
    @RequestMapping("/printUserTicket")
    public ModelAndView printUserTicketController(HttpSession session) {
    
        ModelAndView modelAndView = new ModelAndView();
        
        // getting User from session
        User user = (User)session.getAttribute("user");
        
        // getting Metro System transaction from session
        MetroSystem transaction = (MetroSystem)session.getAttribute("transactions");
        
        modelAndView.addObject("transaction", transaction);
        modelAndView.setViewName("PrintUserTicket");
        
        return modelAndView;
        
    }
    
    
    //==========================================================================
    //------- SHOW ALL TRANSACTIONS
    
    
    
    //==========================================================================
    //-------  SHOW TRANSACTIONS BY USER ID
}

