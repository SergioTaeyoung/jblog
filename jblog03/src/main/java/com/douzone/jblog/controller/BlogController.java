package com.douzone.jblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;

@Controller
@RequestMapping("/{id}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String blog(@PathVariable("id") String id, BlogVo vo, Model model) {
		vo.setBlogId(id);
		vo = blogService.getBlog(vo);
		model.addAttribute("title", vo.getTitle());
		model.addAttribute("logo", vo.getLogo());		
		return "blog/blog-main";
	}
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin() {
		return "blog/blog-admin-basic";
	}

}
