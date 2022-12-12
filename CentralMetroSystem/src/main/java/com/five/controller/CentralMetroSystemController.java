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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CentralMetroSystemController {
    
    @Autowired
    CentralMetroSystemService metroSystemService;
    
    @Autowired
    String start;
    
    @Autowired
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
    
    

    //==========================================================================
    //------ CREATE NEW USER PAGE
    @RequestMapping("/newUserPage")
    public ModelAndView newUserPageController() {
    
        return new ModelAndView("NewUser");
    }
    
    
    //==========================================================================
     //------ HOMEPAGE
    @RequestMapping("/home")
    public ModelAndView HomePageController() {
    
        return new ModelAndView("HomePage");
    }
    
    
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
    		
    		metroSystem.setSourceStation(start);
    	
    		request.getParameter("stationName");
    	
    		message = "You have successfully swiped in at" + start;
    	
    		modelAndView.addObject("message", message);
    		session.setAttribute("start", start);
    		modelAndView.setViewName("Output");
    		
    		return modelAndView;
    }
    
    
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
    
}

