package com.douzone.jblog.controller;

import javax.servlet.http.HttpServletRequest;

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
	
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String category() {
		return "blog/blog-admin-category";
	}
	
	@RequestMapping(value = "/wirte", method = RequestMethod.GET)
	public String write() {
		return "blog/blog-admin-write";
	}
	
	@RequestMapping(value = "/basicupdate", method = RequestMethod.POST)
	public String basicUpdate(
			@PathVariable("id") String id,
			HttpServletRequest httpServletRequest,			
			BlogVo vo) {
		
		String title = httpServletRequest.getParameter("title");
		System.out.println(title + " : title ");
		String logo = httpServletRequest.getParameter("logo");
		vo.setTitle(title);
		vo.setLogo(logo);
		vo.setBlogId(id);
		
		blogService.basicUpdate(vo);
		
		return "blog/blog-admin-basic";
	}

}
