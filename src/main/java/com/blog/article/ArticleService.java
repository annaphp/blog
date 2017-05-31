package com.blog.article;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ArticleService {
	
	@Autowired
	ArticleRepo repository;
	
	public Article create(Article article){
		return repository.saveAndFlush(article);
	}
	
	public List<Article> findAll(){
		return repository.findAll();
	}
	
	public Article findById(Long id){
		return repository.findOne(id);
	}
	
	public List<Article> findByUserId(Long id){
		return repository.findAllByUserId(id);
	}
	
	

}
