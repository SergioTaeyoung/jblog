package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.vo.CategoryVo;

@RestController("BlogApiController")
@RequestMapping("/api/category")
public class BlogController {

	@Autowired
	private BlogService blogService;

	@GetMapping("/{id}")
	public JsonResult list(@PathVariable("id") String id, CategoryVo vo) {
		vo.setId(id);
		// List<CategoryVo> list = blogService.getCategory(vo);
		List<CategoryVo> npList = blogService.numberofPost(vo);

		return JsonResult.success(npList);
	}

	@DeleteMapping("/delete/{no}")
	public JsonResult delete(@PathVariable("no") int no) {
		System.err.println("controller no: " +  no);
		boolean result = blogService.categoryDelete(no);
		System.err.println(result+"=========");
		return JsonResult.success(result ? no : -1);
	}

}
