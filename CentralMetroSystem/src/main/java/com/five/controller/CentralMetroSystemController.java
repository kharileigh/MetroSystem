/**
 *
 * @author kharileigh, lidija, vic, stephanie, priyanka
 */

package com.five.controller;

import com.five.entity.MetroSystem;
import com.five.entity.Station;
import com.five.entity.User;
import com.five.model.service.CentralMetroSystemService;

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
    
    // NEED TO FIGURE OUT SETTING DATE & TIME & PRICE
    //==========================================================================
    //-------- SWIPES IN PAGE
    @RequestMapping("/swipesInPage")
    public ModelAndView swipepesInPageController() {
    	return new ModelAndView("SwipesInPage");
    }
    
    
    //--------- SWIPES IN METHOD
    @RequestMapping("/swipesIn")
    public ModelAndView swipesInController(@ModelAttribute("user") User user, @ModelAttribute("station") Station station, HttpServletRequest request, HttpSession session) {
    	MetroSystem metroSystem =  new MetroSystem();
    	
    	ModelAndView modelAndView = new ModelAndView();
    	
    	String message = null;
    	
    	if(metroSystemService.balanceCheck(user.getUserId()) != null) {
    		if(user.getUserBalance() <= 6)
                    message = "The amount" +user.getUserBalance() + " is less than required";
    	} else 
    		start = station.getStationName();
    		
                // set 
    		metroSystem.setSourceStation(start);
                metroSystem.setStarterBalance(user.getUserBalance());
    	
    		request.getParameter("stationName");
    	
    		message = "You have successfully swiped in at" + start;
    	
    		modelAndView.addObject("message", message);
    		session.setAttribute("start", start);
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
    @RequestMapping("/swipesOut")
    public ModelAndView swipesOutController(@ModelAttribute("user") User user, @ModelAttribute("station") Station station, HttpServletRequest request, HttpSession session) {
    	
        MetroSystem metroSystem = new MetroSystem();
        
    	ModelAndView modelAndView = new ModelAndView();
    	
    	String message = null;
    	String stop = null;
    	
        // SUCCESSFULLY SWIPES OUT USER
    	if(metroSystemService.balanceCheck(user.getUserId()) != null) {
    		
    		stop = station.getStationName();
                
                metroSystem.setDestinationStation(stop);
    	
    		metroSystemService.checkRoute(start, stop);
                metroSystem.setRemainingBalance(user.getUserBalance());
    	
                message = "You have successfully swiped out at" + stop + " with current balance a " +  metroSystem.getRemainingBalance();
    	
        // FAILS TO SWIPE OUT USER	
    	} else {        
            
            message = "The amount" + user.getUserBalance() + " is less than required. Please top up";
        }
    	
            modelAndView.addObject("message", message);
            session.setAttribute("stop", stop);
            modelAndView.setViewName("Output");
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
    public ModelAndView topUpBalanceController(@ModelAttribute("user") User user, HttpServletRequest request) {
        
        ModelAndView modelAndView = new ModelAndView();
        
        double amount = Double.parseDouble(request.getParameter("amount"));
        
        String message = null;
        
        if(metroSystemService.updateBalance(user.getUserId(), amount) != null) {
        
            message = "Your Balance has now been increased by " + amount;
            
        } else {
        
            message = "Failed to top up your balance, please try again";
        }
        
        user.setUserBalance(amount);
        modelAndView.addObject("message", message);
        modelAndView.setViewName("Output");
        
        return modelAndView;
        
    }

    //==========================================================================
    //------- SHOW CURRENT BALANCE
    @RequestMapping("/showCurrentBalancePage")
    public ModelAndView login=PageController() {
    
        return new ModelAndView("ShowCurrentBalance");
    }
    
    
    //==========================================================================
    //------ PRINT USER TICKET
    
    
    
    //==========================================================================
    //------- SHOW ALL TRANSACTIONS
    
    
    
    //==========================================================================
    //-------  SHOW TRANSACTIONS BY DATE
}

