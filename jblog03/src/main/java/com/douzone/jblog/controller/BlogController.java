package com.douzone.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.douzone.jblog.service.BlogService;

@Controller
public class BlogController {
	
	@Autowired
	private BlogService blogService;

}
