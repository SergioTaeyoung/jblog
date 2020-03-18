package com.douzone.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;

	public int join(BlogVo bVo) {
		System.out.println(bVo.getBlogId() + bVo.getTitle() + bVo.getLogo() + "service");
		return blogRepository.insert(bVo);		
	}

	public BlogVo getBlog(BlogVo vo) {
		return blogRepository.myBlog(vo);		
	}



}
