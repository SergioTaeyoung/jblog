package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;

@Repository
public class BlogRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public int insert(BlogVo bVo) {		
		return sqlSession.insert("blog.insert", bVo);
	}

	public BlogVo myBlog(BlogVo vo) {		
		return sqlSession.selectOne("blog.myBlog", vo);
	}

	public int basicUpdate(BlogVo vo) {
		return sqlSession.update("blog.basicUpdate", vo);
	}

	public int cateInsert(CategoryVo vo) {
		return sqlSession.insert("category.insert", vo);
		
	}

	public int getTotal(String id) {
		return sqlSession.selectOne("category.total", id);
		
	}

}
