package com.blog.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blog.article.Article;
import com.blog.article.ArticleService;
import com.blog.image.ImageService;
import com.blog.user.User;
import com.blog.user.UserService;

@Controller
@RequestMapping(value="/read")
public class ReaderConroller {
	@Autowired
	ArticleService articleService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ImageService imageService;
	
	@RequestMapping(value="/article/{id}", method=RequestMethod.GET)
	public String showArticle(@PathVariable("id") Long id, Model model){
		Article article = articleService.findById(id);
		model.addAttribute("article", article);
		return "post";
	}
		
	@RequestMapping(value="/{userName}", method=RequestMethod.GET)
	public String showPublicBlog(@PathVariable("userName") String UserName, Model model){
		User user = userService.byUserName(UserName);
		List<Article> articles = articleService.findByUserId(user.getId());
		model.addAttribute("articles", articles);
		model.addAttribute("user", user);
		return "public_blog";	
	}
	
}
