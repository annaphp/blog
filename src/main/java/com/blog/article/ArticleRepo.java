package com.blog.article;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blog.user.User;

public interface ArticleRepo extends JpaRepository<Article, Long>{
	
	//@Query("select a from Article s join fetch where s.user")
	public List<Article> findAllByUserId( User user);

}
