package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;

	// /id/categoryNo/postNo
	@RequestMapping({ "", "/{pathNo1}", "/{pathNo1}/{pathNo2}" })
	public String blog(@PathVariable("id") String id, BlogVo vo, CategoryVo cVo, Model model, PostVo pVo,
			@PathVariable Optional<Long> pathNo1, @PathVariable Optional<Long> pathNo2) {
		
		vo.setBlogId(id);
		cVo.setId(id);
		
		Long postNo = 0L;
		Long categoryNo = 0L;

		if (pathNo2.isPresent()) {
			Long firstCategoryNo = blogService.getFirstCategoryNo(id);
			model.addAttribute("caNo", firstCategoryNo);

			postNo = pathNo2.get();
			categoryNo = pathNo1.get();

			pVo.setNo(postNo);
			pVo = blogService.getPost(pVo);
			model.addAttribute("post", pVo);

			List<PostVo> pList = blogService.getPostList(categoryNo);
			model.addAttribute("pList", pList);

			BlogVo blogVo = blogService.getBlog(vo);
			List<CategoryVo> list = blogService.getCategory(cVo);

			model.addAttribute("list", list);
//			model.addAttribute("title", vo.getTitle());
//			model.addAttribute("logo", vo.getLogo());
			model.addAttribute("blogVo", blogVo);

		} else if (pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
			model.addAttribute("caNo", categoryNo);

			Long cateNo = blogService.getMinNo(categoryNo);
			pVo.setNo(cateNo);
			pVo = blogService.getPost(pVo);
			model.addAttribute("post", pVo);

			List<PostVo> pList = blogService.getPostList(categoryNo);
			model.addAttribute("pList", pList);

			BlogVo blogVo = blogService.getBlog(vo);
			List<CategoryVo> list = blogService.getCategory(cVo);

			model.addAttribute("list", list);
//			model.addAttribute("title", vo.getTitle());
//			model.addAttribute("logo", vo.getLogo());
			model.addAttribute("blogVo", blogVo);

		} else {

			// 첫번째로 만들어진 카테고리 번호
			Long firstCategoryNo = blogService.getFirstCategoryNo(id);
			model.addAttribute("caNo", firstCategoryNo);
			Long cateNo = blogService.getMinNo(firstCategoryNo);
			pVo.setNo(cateNo);
			pVo = blogService.getPost(pVo);
			model.addAttribute("post", pVo);
			System.err.println(firstCategoryNo);
			// 첫번째 포스트 리스트
			List<PostVo> pList = blogService.getPostList(firstCategoryNo);
			model.addAttribute("pList", pList);

			BlogVo blogVo = blogService.getBlog(vo);
			List<CategoryVo> list = blogService.getCategory(cVo);

			model.addAttribute("list", list);
//			model.addAttribute("title", vo.getTitle());
//			model.addAttribute("logo", vo.getLogo());
			model.addAttribute("blogVo", blogVo);
		}
		return "blog/blog-main";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String admin() {
		return "blog/blog-admin-basic";
	}

	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String category(@PathVariable("id") String id, CategoryVo vo, Model model) {
		vo.setId(id);

		List<CategoryVo> npList = blogService.numberofPost(vo);

		int total = blogService.getTotal(id);

		model.addAttribute("totalCount", total);
		model.addAttribute("npList", npList);

		return "blog/blog-admin-category";
	}

	@RequestMapping(value = "/delete/{vo.no}/{vo.name}")
	public String delete(@PathVariable("vo.no") int no, @PathVariable("vo.name") String name) {
		if (!name.equals("기타"))
			blogService.categoryDelete(no);
		return "redirect:/{id}/category";
	}

	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(@PathVariable("id") String id, BlogVo vo, CategoryVo cVo, Model model) {
		cVo.setId(id);
		List<CategoryVo> list = blogService.getCategory(cVo);

		model.addAttribute("list", list);

		return "blog/blog-admin-write";
	}

	@RequestMapping(value = "/postwrite", method = RequestMethod.POST)
	public String write2(@PathVariable("id") String id,
			@RequestParam(value = "title", defaultValue = "true") String title,
			@RequestParam(value = "content", defaultValue = "true") String contents,
			@RequestParam(value = "category", required = true, defaultValue = "") Long cateNo, CategoryVo cVo,
			PostVo pVo, Model model) {

		pVo.setTitle(title);
		pVo.setContents(contents);
		pVo.setCategoryNo(cateNo);

		blogService.addPost(pVo);

		return "redirect:/{id}/write";
	}

	@RequestMapping(value = "/basicupdate", method = RequestMethod.POST)
	public String basicUpdate(@PathVariable("id") String id,
			@RequestParam(value = "title", defaultValue = "true") String title,
			@RequestParam(value = "logo-file", defaultValue = "true") String logo, BlogVo vo) {

		vo.setTitle(title);
		vo.setLogo(logo);
		vo.setBlogId(id);

		blogService.basicUpdate(vo);

		return "blog/blog-admin-basic";
	}

	@RequestMapping(value = "/cateupdate", method = RequestMethod.POST)
	public String cateUpdate(@PathVariable("id") String id,
			@RequestParam(value = "name", defaultValue = "true") String name,
			@RequestParam(value = "desc", defaultValue = "true") String description, CategoryVo vo) {
		if ("기타".equals(name)) {
			return "redirect:/{id}/category";
		} else {
			vo.setName(name);
			vo.setDescription(description);
			vo.setId(id);
			blogService.cateInsert(vo);
		}

		return "redirect:/{id}/category";
	}

}
