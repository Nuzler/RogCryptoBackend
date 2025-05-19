package com.example.RogCryptoBackend.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.RogCryptoBackend.dto.BlogRequest;
import com.example.RogCryptoBackend.services.BlogServices;




@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class BlogController {

    @Autowired
    BlogServices blogServices;
    
    @PostMapping("/upload")
    public ResponseEntity<String> storeNews(@RequestParam("title") String title,@RequestParam("imgUrl") String imgUrl,@RequestParam("file") MultipartFile file ) throws IOException{

      blogServices.storeNews(title,imgUrl,file);


        return ResponseEntity.ok("ok");
    }

    
    @PostMapping("/store")
    public ResponseEntity<?> storeContent(@RequestBody BlogRequest request){

        
        return ResponseEntity.ok(blogServices.storeContent(request));
    }

    
    @GetMapping("/GetNews") 
    public List<Map<String,Object>> getNews(){

        return blogServices.getNews();
    }
    
    
    @GetMapping("/GetContents")
    public List<BlogRequest> getcontent(){
        return blogServices.getcontent();
    }
    
    @DeleteMapping("/NewsDelete/{NewsId}")
    public ResponseEntity<String> deleteNews(@PathVariable Integer NewsId) {
      
           blogServices.deleteNews(NewsId);
        return ResponseEntity.ok("deleted");
    }
}
