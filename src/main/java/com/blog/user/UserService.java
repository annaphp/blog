package com.blog.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	UserRepo userRepo;

	
	@Autowired
	PasswordEncoder encoder;

	public User byUserName(String username) {
		return userRepo.findByUsername(username);
	}

	public List<User> findAll() {
		return userRepo.findAll();
	}
	
	/**
	 * Creates new user and hashes password.
	 * @param user user with plain text password
	 * @return 
	 */
	public User create(User user, Role inRole) {
		if( ! usernameTaken(user)){
			user.setRole(inRole);
			user.setPassword(encoder.encode(user.getPassword()));
			user = saveAndFlush(user);
			return user;
		}	
		return null;
	}
	
	

	public boolean usernameTaken(User user) {
		return findAll().stream().map( u -> u.getUsername()).collect(Collectors.toSet()).contains(user.getUsername());
	}
	
	/**
	 * Updates user and rehashes password.
	 * @param user user with plain text password
	 */
	public void rehashPassword(User user){
		user.setPassword(encoder.encode(user.getPassword()));
		userRepo.saveAndFlush(user);
	}

	public User byId(Long id) {
		return userRepo.findOne(id);
	}

	@Secured("ROLE_ADMIN")
	public void delete(Long id) {
		userRepo.delete(id);	
	}


	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		UserDetails details = byUserName(userName);
		if (details == null) throw new UsernameNotFoundException("user not found: "+userName);
		return details;
	}

	public User saveAndFlush(User user) {
		return userRepo.saveAndFlush(user);
		
	}
	
	public User currentUser(){
		return byUserName(
				SecurityContextHolder.getContext().getAuthentication().getName()
				);
	}


}
