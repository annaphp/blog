package com.blog.image;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.blog.article.Article;
@Entity
public class Image {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
	private Article article;
	
	private String fileName;
	private String contentType;
	private long size;
	
	
	public Image(Article article, String fileName, String contentType, long size) {
		this.article = article;
		this.fileName = fileName;
		this.contentType = contentType;
		this.size = size;
	}
	
	
	public Image(String fileName) {
		this.fileName = fileName;
	}
	
	public Image() {}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "Image [id=" + id + ", fileName=" + fileName + "]";
	}
}
