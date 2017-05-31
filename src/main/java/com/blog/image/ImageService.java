package com.blog.image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.article.Article;
import com.blog.article.ArticleRepo;

@Service
public class ImageService {
	
	private static final String UPLOAD_DIR = "/Users/anna/uploads";
	
	@Autowired
	ImageRepo imageRepo;
	
	@Autowired
	ArticleRepo  articleRepo;
	
	@Autowired
	ResourceLoader loader;
	
	public Resource get(Image image){
		return loader.getResource("file:" + UPLOAD_DIR + "/" + image.getId());
	}

	public Image create(MultipartFile file, Long articleId) throws IOException{
		if(!file.isEmpty()){
			System.out.println("*********inside if imageservice");
			Article article = articleRepo.findOne(articleId);
			Image image = imageRepo.saveAndFlush(new Image(article, file.getOriginalFilename(), 
					file.getContentType(), file.getSize()));
			System.out.println("+++++++++ image after it saved " + image);
			Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR, image.getId().toString()));
		}
		System.out.println("*********before");

		return null;
	}
	
	public void delete(Image image) throws IOException{
		if(image != null){
			imageRepo.delete(image);
			Files.deleteIfExists(Paths.get(UPLOAD_DIR, image.getId().toString()));
		}
	}
	
	public Image findByArticleId(Long articleId){
		return imageRepo.findFirstByArticleId(articleId);
	}
	
	public Image getById(Long id){
		return imageRepo.findOne(id);
	}
}