package com.blog.article;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ArticleRepo extends JpaRepository<Article, Long>{
	
	@Query("select a from Article a join fetch  a.user u where u.id = ?1")
	public List<Article> findAllByUserId( Long id);

}
