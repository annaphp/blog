package com.blog.web;

import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String showMainPage(){
		return "main";
	}
}
