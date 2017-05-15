package com.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blog.article.Article;
import com.blog.article.ArticleService;

@Controller
public class BlogController {
	
	@Autowired
	ArticleService service;

	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String home(Model model){
		System.out.println("controller");
		model.addAttribute("articles", service.findAll());
		return "home";
	}
	
	@RequestMapping(value="article_form")
	public String articleForm(Model model){
		model.addAttribute("article", new Article());
		return "form";
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public String save(@ModelAttribute Article article){
		service.create(article);
		return "redirect:/home";
	}
}
