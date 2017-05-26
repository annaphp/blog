package com.blog.web;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.blog.article.Article;
import com.blog.article.ArticleService;
import com.blog.user.Role;
import com.blog.user.User;
import com.blog.user.UserService;

@Controller
public class BlogController {
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	UserService userService;

	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String home(Model model){
		System.out.println("controller");
		model.addAttribute("articles", articleService.findAll());
		return "home";
	}
	
	@RequestMapping(value="article_form")
	public String articleForm(Model model){
		model.addAttribute("article", new Article());
		return "form";
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public String save(@ModelAttribute Article article, Principal principal){
		article.setUser(userService.byUserName(principal.getName()));
		System.out.println("in save asticle ------------> " + article);
		articleService.create(article);
		
		
		return "redirect:/home";
	}
	
	@RequestMapping(value="/")
	public String main(){
		return "main";
		
	}
	
	@RequestMapping(value="/login")
	public String login(){
		return "login";
	}
	
	@RequestMapping(value="/register")
	public String registerForm(Model model){
		model.addAttribute("user", new User());
		return "register";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@Valid User user, Errors errors){
		if(errors.hasErrors()){
			return "register";
		}
		if(userService.byUserName(user.getUsername()) != null){
			//return error message into view
			return "register";
		}
	    userService.create(user, Role.ROLE_OWNER);		
		return "redirect:/login";
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String editForm(@PathVariable("id") Long id, Model model){
		Article article = articleService.findById(id);
		System.out.println("asticle ------------> " + article);
		model.addAttribute("article", article);
		return "form";
	}
	
	
}
