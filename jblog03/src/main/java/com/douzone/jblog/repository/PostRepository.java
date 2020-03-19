package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.PostVo;

@Repository
public class PostRepository {

	@Autowired
	private SqlSession sqlSession;
	
	public int addPost(PostVo pVo) {		
		return sqlSession.insert("post.add", pVo);
	}

	public List<PostVo> getPostList(int firstCategoryNo) {
		return sqlSession.selectList("post.list", firstCategoryNo);
	}

	public PostVo getPost(PostVo vo) {
		return sqlSession.selectOne("post.post", vo);
	}

}
