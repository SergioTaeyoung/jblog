package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;

@Repository
public class BlogRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public int insert(BlogVo bVo) {
		System.out.println(bVo.getBlogId() + bVo.getTitle() + bVo.getLogo() +"repository");
		return sqlSession.insert("blog.insert", bVo);
	}

	public BlogVo myBlog(BlogVo vo) {		
		return sqlSession.selectOne("blog.myBlog", vo);
	}

}
