package com.blog.web;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

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

import com.blog.article.Article;
import com.blog.article.ArticleService;
import com.blog.image.Image;
import com.blog.image.ImageService;
import com.blog.user.Role;
import com.blog.user.User;
import com.blog.user.UserService;

@Controller
public class BlogController {
	
	@Autowired
	ArticleService articleService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ImageService imageService;
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String allArticles(){
		return "main";
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
	public String save(@ModelAttribute Article article,@RequestParam("file") MultipartFile file,
			           Principal principal)throws IOException{
		article.setUser(userService.byUserName(principal.getName()));
		Article a = articleService.create(article);
		Image image = imageService.findByArticleId(a.getId());
		if(image != null){
			imageService.delete(image);
			imageService.create(file, a.getId());
		}else{
			imageService.create(file, a.getId());
		}
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
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
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
		Image image = imageService.findByArticleId(id);
		model.addAttribute("image", image);
		model.addAttribute("article", article);
		return "form";
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public String deletePost(@PathVariable("id") Long id){
	    Image image = imageService.findByArticleId(id);
	   try{
	    	imageService.delete(image);
	   }catch(IOException exception){}
	   
		articleService.delete(id);
		return "redirect:/home";
	}
	
}
