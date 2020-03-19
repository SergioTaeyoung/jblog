package com.douzone.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;


import com.douzone.jblog.service.BlogService;


@Controller
public class MainController {

	@RequestMapping({"", "/main"})
	public String index() {		
		return "main/index";
	
	}
	

}
