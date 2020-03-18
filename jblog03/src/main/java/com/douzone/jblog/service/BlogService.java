package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	public int join(BlogVo bVo) {
		System.out.println(bVo.getBlogId() + bVo.getTitle() + bVo.getLogo() + "service");
		return blogRepository.insert(bVo);		
	}

	public BlogVo getBlog(BlogVo vo) {
		return blogRepository.myBlog(vo);		
	}

	public void basicUpdate(BlogVo vo) {
		blogRepository.basicUpdate(vo);		
	}

	public void cateInsert(CategoryVo vo) {
		categoryRepository.cateInsert(vo);
		
	}

	public int getTotal(String id) {
		return categoryRepository.getTotal(id);		
	}

	public List<CategoryVo> getCategory(CategoryVo vo) {
		return categoryRepository.myCategory(vo);
		
	}


}
