package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.FileUploadService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {

	@Autowired
	private BlogService blogService;
	@Autowired
	private FileUploadService fileUploadService;
	
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
	public String admin(
			@PathVariable("id") String id, 
			BlogVo vo,
			Model model) {
		
		vo.setBlogId(id);		
		BlogVo blogVo = blogService.getBlog(vo);			
		
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-admin-basic";
	}

	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String category(@PathVariable("id") String id, BlogVo bVo, CategoryVo vo, Model model) {
		vo.setId(id);

		List<CategoryVo> npList = blogService.numberofPost(vo);
		bVo.setBlogId(id);
		BlogVo blogVo = blogService.getBlog(bVo);
		model.addAttribute("blogVo", blogVo);

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

		vo.setBlogId(id);
		BlogVo blogVo = blogService.getBlog(vo);
		model.addAttribute("blogVo", blogVo);
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
			@ModelAttribute BlogVo vo,
			@RequestParam("logo-file") MultipartFile multipartFile) {

		String url = fileUploadService.restore(multipartFile);
		
		vo.setBlogId(id);
		vo.setLogo(url);
		blogService.basicUpdate(vo);

		return "redirect:/{id}";
	}

	@RequestMapping(value = "/cateupdate", method = RequestMethod.POST)
	public String cateUpdate(@PathVariable("id") String id,
			@RequestParam(value = "name", required = true , defaultValue = "") String name,
			@RequestParam(value = "desc",required = true , defaultValue = "") String description, CategoryVo vo) {
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
	
	@RequestMapping(value = "/category-spa", method = RequestMethod.GET)
	public String categorySpa(@PathVariable("id") String id, Model model, BlogVo vo) {
		vo.setBlogId(id);
		BlogVo blogVo = blogService.getBlog(vo);
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-admin-category-spa";
	}

}
