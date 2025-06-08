package com.example.RogCryptoBackend.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.RogCryptoBackend.dto.BlogRequest;
import com.example.RogCryptoBackend.model.Blog;
import com.example.RogCryptoBackend.model.BlogSection;
import com.example.RogCryptoBackend.repository.BlogRepository;
import com.example.RogCryptoBackend.repository.BlogSectionRepository;

@Service
public class BlogServices {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    BlogSectionRepository blogSectionRepository;

    public Blog storeNews(String title,String imgUrl,MultipartFile file) throws IOException{
        
        Blog blog = new Blog();

        
        blog.setTitle(title);
        blog.setImageUrl(file.getBytes());
        blog.setImgUrl(imgUrl);

        return blogRepository.save(blog);
        
    }

    public Blog storeContent(BlogRequest request){
        Blog blog=new Blog();
        blog.setTitle(request.getTitle());
        blog.setImgUrl(request.getImgUrl());
        blogRepository.save(blog);
 
        for(BlogRequest.Section sec : request.getSection()){
            BlogSection section=new BlogSection();
            section.setBlog(blog);
            section.setType(sec.getType());
            section.setContent(sec.getContent());
            section.setPosition(sec.getPosition());
            blogSectionRepository.save(section);


        }

        return blog;
    }

    public List<Map<String,Object>> getNews(){

        return blogRepository.findAll().stream().map(blog->{
            Map<String,Object>map=new HashMap<>();
           
            map.put("title",blog.getTitle());
            map.put("imgUrl",blog.getImgUrl());
            
            return map;

        }).collect(Collectors.toList());
    }


    public List<BlogRequest> getcontent(){

        List<BlogRequest> responses = new ArrayList<>();
        List<Blog> blogs=blogRepository.findAll();
       

        for(Blog blog:blogs){
         BlogRequest blogRequest = new BlogRequest();
         blogRequest .setNewsId(blog.getNewsId());
         blogRequest.setTitle(blog.getTitle());
         blogRequest.setImgUrl(blog.getImgUrl());
       
         List<BlogSection> blogSections=blogSectionRepository.findByBlog_NewsId(blog.getNewsId());
         List<BlogRequest.Section> sections = new ArrayList<>();
        for(BlogSection section:blogSections){
            BlogRequest.Section sec = new BlogRequest.Section();
            sec.setContent(section.getContent());
            sec.setPosition(section.getPosition());
            sec.setType(section.getType());
            sections.add(sec);
        }

        blogRequest.setSection(sections);
        responses.add(blogRequest);

    }

        return responses;
    }

    public String deleteNews(Integer NewsId){

        System.out.print("News Id Is"+NewsId);

        blogSectionRepository.deleteAllByBlogNewsId(NewsId);
        blogRepository.deleteById(NewsId);

        return"ok";


    }
    
}
