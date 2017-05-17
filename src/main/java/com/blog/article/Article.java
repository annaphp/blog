package com.blog.article;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

@Entity
public class Article {
	
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	@Length(max=4000)
	private String content;
	

	public Article() {}
	
	public Article(String title, String content) {
		this.title = title;
		this.content = content;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}