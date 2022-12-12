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
    @Autowired
    String start;
    @Autowired
    String stop;
    
    //Landing Page
    @RequestMapping("/")
    public ModelAndView getLandingPage() {
    	return new ModelAndView("LandingPage");
    }
    
    
    
    // LOGIN PAGE 
    @RequestMapping("/login")
    public ModelAndView loginPageController() {
    
        return new ModelAndView("Login");
    }
    

    // CREATE ACCOUNT PAGE 
    @RequestMapping("/newUser")
    public ModelAndView newUserPageController() {
    
        return new ModelAndView("NewUser");
    }
    
     //Create HOME page
    @RequestMapping("/home")
    public ModelAndView HomePageontroller() {
    
        return new ModelAndView("HomePage");
    }
    
    //User Swipes In Page
    @RequestMapping("/swipesInPage")
    public ModelAndView swipepesInPageController() {
    	return new ModelAndView("swipesInPage");
    }
    
    
    //Swipes in method
    @RequestMapping("/swipesIn")
    public ModelAndView swipesInController(@ModelAttribute("user") User user, @ModelAttribute("station") Station station, HttpServletRequest request, HttpSession session) {
    	MetroSystem metroSystem =  new MetroSystem();
    	
    	ModelAndView modelAndView=new ModelAndView();
    	
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
    
    
    //User Swipes Out Page
    @RequestMapping("/swipesOutPage")
    public ModelAndView swipepesOutPageController() {
    	return new ModelAndView("swipesOutPage");
    }
    
  //Swipes out method
    @RequestMapping("/swipesOut")
    public ModelAndView swipesOutController(@RequestParam("adjacent_cost") double adjacent_cost, @RequestParam("userBalance")  double userBalance, @RequestParam("stationName") String stationName, @RequestParam("userId") int userId, HttpServletRequest request, HttpSession session) {
    	
    	ModelAndView modelAndView=new ModelAndView();
    	
    	String message = null;
    	String stop = null;
    	
    	if(metroSystemService.balanceCheck(userId) != null)
    		
    		stop = request.getParameter("stationName");
    	
    		metroSystemService.checkRoute(start, stop);
    	
    	
    	message = "You have successfully swiped out at" + stationName + " with current balance a s" + ;
    	
    	if (metroSystemService.balanceCheck(userId) != null) {
    		
    		if(userBalance <= 6)
    			message = "The amount" + userBalance + " is less than required";
    	} else 
    		
    	
    		
    	
    		modelAndView.addObject("message", message);
    	    session.setAttribute("stop", stop);
    		modelAndView.setViewName("Output");
    		
    		return modelAndView;
    
    
    }   

    
    
}
