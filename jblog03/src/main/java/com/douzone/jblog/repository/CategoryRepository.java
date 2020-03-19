package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;

@Repository
public class CategoryRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public int defaultCategory(CategoryVo vo) {
		return sqlSession.insert("category.default", vo);		
	}

	public int cateInsert(CategoryVo vo) {
		return sqlSession.insert("category.insert", vo);		
	}

	public int getTotal(String id) {
		return sqlSession.selectOne("category.total", id);
		
	}

	public List<CategoryVo> myCategory(CategoryVo vo) {
		return sqlSession.selectList("category.myList", vo);
	}

	public List<CategoryVo> getNumberOfPost(CategoryVo vo) {
		return sqlSession.selectList("category.getNP", vo);
	}

	public int categoryDelete(int no) {
		return sqlSession.delete("category.delete", no);
	}

}
