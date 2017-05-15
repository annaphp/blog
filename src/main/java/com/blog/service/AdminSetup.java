package com.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.blog.user.Role;
import com.blog.user.User;
import com.blog.user.UserService;


@Service
@Profile({"production", "dev"})
public class AdminSetup {
	
	@Autowired
	UserService userService;
	
	@Scheduled(fixedRate = Long.MAX_VALUE)
	public void setUpAdmin(){
		User u = userService.create(new User("ss@ss.ss", "asdfasdf"), Role.ROLE_OWNER);
		System.out.println("***************admin account set up" + u);
	}
}