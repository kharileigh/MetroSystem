/**
 *
 * @author kharileigh, lidija, vic, stephanie, priyanka
 */

package com.five.controller;

import com.five.entity.MetroSystem;
import com.five.entity.Station;
import com.five.entity.User;
import com.five.model.service.CentralMetroSystemService;
import java.time.LocalDateTime;
import java.time.*;
import java.util.ArrayList;
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
    
    String start;
    String stop;
    
 
    
    
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
    
    // CHANGE TO STATIONS REST API
    //------- LISTS ALL THE STATIONS
    @ModelAttribute("stations")
    public List <Station> getStation() {
        List<Station> list = new ArrayList<Station>();
        list.add(new Station(1, "Bank"));
        list.add(new Station(2, "Lewisham"));
        list.add(new Station(3, "Croydon"));
        list.add(new Station(4, "Harlow"));
        list.add(new Station(5, "Euston"));
        
        return list;
        
    }
    
    
    
    // NEED TO FIGURE OUT SETTING DATE & TIME & PRICE
    //==========================================================================
    //-------- SWIPES IN PAGE
    @RequestMapping("/swipesInPage")
    public ModelAndView swipepesInPageController() {
    	return new ModelAndView("SwipesInPage");
    }
    
    
    //--------- SWIPES IN METHOD
    // get User starter balance -> set metrosystem
    // get User source station - set ms
    // get User date & time -> set ms
    @RequestMapping("/swipesIn")
    public ModelAndView swipesInController(@RequestParam("stationName") String stationName, HttpServletRequest request, HttpSession session) {
    	MetroSystem metroSystem =  new MetroSystem();
    	
    	ModelAndView modelAndView = new ModelAndView();
        
        LocalDateTime localDateTime = LocalDateTime.now();
        
        // gets User from session
        User user = (User)session.getAttribute("user");
    	
    	String message = null;
    	
    	if(metroSystemService.balanceCheck(user.getUserId()) != null) {
    		if(user.getUserBalance() <= 6)
                    message = "The amount" + user.getUserBalance() + " is less than required";
    	} else 
    		start = stationName;
    		
                // set Metro System entity - Source Station
    		metroSystem.setSourceStation(start);
    	
    		message = "You have successfully swiped in at" + start;
                
                // set Metro System entity - Starter Balance 
                metroSystem.setStarterBalance(user.getUserBalance());
                
                // set Metro System entity - Source Date & Time
                metroSystem.setSourceSwipeInDateTime(localDateTime);
                
                // set Metro System entity - userId
                metroSystem.setUserId(user.getUserId());
                
    	
    		modelAndView.addObject("message", message);
    		session.setAttribute("start", start);
                session.setAttribute("metroSystem", metroSystem);
                
                // MIGHT NEED TO CHANGE VIEW TO SWIPE OUT PAGE
    		modelAndView.setViewName("Output");
    		
    		return modelAndView;
    }
    
    // NEED TO FIGURE OUT SETTING DATE & TIME & PRICE - MUST REDIRECT TO USER TRANSACTION PAGE (breakdown of journey and cost)
    //==========================================================================
    //--------- SWIPES OUT PAGE
    @RequestMapping("/swipesOutPage")
    public ModelAndView swipepesOutPageController() {
    	return new ModelAndView("swipesOutPage");
    }
    
    //--------- SWIPES OUT METHOD
    // NEED TO MAP DATA OF TRANSACTION OBJECT TO PRINT USER TICKET VIEW
    @RequestMapping("/swipesOut")
    public ModelAndView swipesOutController(@RequestParam("stationName") String stationName, HttpServletRequest request, HttpSession session) {
    	
        // getting Metro System source data from session
        MetroSystem metroSystem = (MetroSystem)session.getAttribute("metroSystem");
        
        // getting User from session
        User user = (User)session.getAttribute("user");
        
    	ModelAndView modelAndView = new ModelAndView();
        
        LocalDateTime localDateTime = LocalDateTime.now();
    	
    	String message = null;
    	String stop = null;
    		
        stop = stationName;

        // set Metro System entity - Source Date & Time, Destination Station
        metroSystem.setDestinationStation(stop);
        metroSystem.setDestinationSwipeOutDateTime(localDateTime);

        // set Metro System entity - Price
        Double price = metroSystemService.checkRoute(metroSystem.getSourceStation(), stop);
        
        metroSystem.setPrice(price);

        // calculates single transaction of User's journey
        MetroSystem transaction = metroSystemService.saveTransaction(metroSystem, user.getUserId());

        message = "You have successfully swiped out at" + stop + " with current balance a " +  metroSystem.getRemainingBalance();


        modelAndView.addObject("message", message);
        modelAndView.addObject("transactions", transaction);
        session.setAttribute("user", user);
        session.setAttribute("stop", stop);
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
    
    
    
    //==========================================================================
    //------- SHOW ALL TRANSACTIONS
    
    
    
    //==========================================================================
    //-------  SHOW TRANSACTIONS BY USER ID
}

