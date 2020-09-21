package com.cg.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.service.DisplayBalServiceI;


@RestController
@RequestMapping("/User")
@CrossOrigin("http://localhost:4200")
public class DisplayBalanceController { 
	
	@Autowired    // service interface object
	DisplayBalServiceI displayBalServiceI;
	
	//include get mapping of account balance


}
