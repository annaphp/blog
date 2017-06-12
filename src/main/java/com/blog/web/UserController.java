package com.blog.web;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blog.article.ArticleService;
import com.blog.image.ImageService;
import com.blog.user.Role;
import com.blog.user.User;
import com.blog.user.UserService;

@Controller
@RequestMapping(value="/user")
public class UserController {
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ImageService imageService;
	
    @RequestMapping(value="/login")
		public String login(){
		return "login";
	}
		
	@RequestMapping(value="/register", method=RequestMethod.GET)
		public String registerForm(Model model){
		model.addAttribute("userToRegister", new User());
		return "register";
	}
		
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@Valid @ModelAttribute("userToRegister") User user, Errors errors, RedirectAttributes model){
		if(errors.hasErrors()){
		model.addAttribute("userToRegister", user);
		return "register";
	}
//			if(userService.byUserName(user.getUsername()) != null){
//				//return error message into view when user with such name exists
//				return "register";
//			}
		 userService.create(user, Role.ROLE_OWNER);	//this method checks if user already exists	
		 model.addFlashAttribute("messages",Arrays.asList("You have been successfully registered!"));
		 return "redirect:/login";
	}

}
