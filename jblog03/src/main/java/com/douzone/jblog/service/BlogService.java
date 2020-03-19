package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.repository.PostRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Service
public class BlogService {

	@Autowired
	private BlogRepository blogRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private PostRepository postRepository;

	public int join(BlogVo bVo) {		
		return blogRepository.insert(bVo);
	}

	public BlogVo getBlog(BlogVo vo) {
		return blogRepository.myBlog(vo);
	}

	public void basicUpdate(BlogVo vo) {
		blogRepository.basicUpdate(vo);
	}
	
	public void defaultCategory(CategoryVo vo) {
		categoryRepository.defaultCategory(vo);
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

	public int addPost(PostVo pVo) {
		return postRepository.addPost(pVo);
	}

	public List<CategoryVo> numberofPost(CategoryVo vo) {
		return categoryRepository.getNumberOfPost(vo);
	}

	public void categoryDelete(int no) {
		categoryRepository.categoryDelete(no);		
	}

	public Long getFirstCategoryNo(String id) {
		return categoryRepository.getFirstCategoryNo(id);		
	}

	public List<PostVo> getPostList(Long firstCategoryNo) {
		return postRepository.getPostList(firstCategoryNo);
	}

	public PostVo getPost(PostVo vo) {
		return postRepository.getPost(vo);		
	}
	
	public Long getMinNo(Long cateNo) {
		return postRepository.getMinNo(cateNo);
	}
	


	
	

}
