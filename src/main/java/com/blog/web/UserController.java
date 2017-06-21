package com.blog.web;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.blog.article.Article;
import com.blog.article.ArticleService;
import com.blog.image.Image;
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
		public String login(Model model){
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
		
		if(userService.usernameTaken(user)){
			errors.rejectValue("username", "Match", "This username is taken.");
			model.addAttribute("userToRegister", user);
			return "register";
		}
		 userService.create(user, Role.ROLE_OWNER);	//this method checks if user already exists	
		 model.addFlashAttribute("messages","You have been successfully registered! Please login.");
		// model.addFlashAttribute("messages",Arrays.asList("You have been successfully registered! Please log in."));

		 return "redirect:/user/login";
	}
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String home(Model model, Principal principal){
		User currentUser = userService.byUserName(principal.getName());
		model.addAttribute("articles", articleService.findByUserId(currentUser.getId()));
		return "home";
	}
	
	@RequestMapping(value="article_form")
	public String articleForm(Model model){
		model.addAttribute("article", new Article());
		return "form";
	}
	
	@RequestMapping(value="save", method=RequestMethod.POST)
	public String saveArticle(@ModelAttribute Article article,@RequestParam("file") MultipartFile file,
			           Principal principal, RedirectAttributes model)throws IOException{
		article.setUser(userService.byUserName(principal.getName()));
		Article a = articleService.create(article);
		Image image = imageService.findByArticleId(a.getId());
		if(image != null){
			imageService.delete(image);
			imageService.create(file, a.getId());
		}else{
			imageService.create(file, a.getId());
		}
		model.addFlashAttribute("messages", "Your post has been successfuly saved!");
		return "redirect:/user/home";
	}
	
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public String editArticleForm(@PathVariable("id") Long id, Model model){
		Article article = articleService.findById(id);
		Image image = imageService.findByArticleId(id);
		model.addAttribute("image", image);
		model.addAttribute("article", article);
		return "form";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String deletePost(@PathVariable("id") Long id, RedirectAttributes model ){
	    Image image = imageService.findByArticleId(id);
	   try{
	    	imageService.delete(image);
	   }catch(IOException exception){}
	   
		articleService.delete(id);
		model.addFlashAttribute("messages", "Your post has been successfully deleted!");
		return "redirect:/user/home";
	}

}
