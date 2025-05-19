package com.example.RogCryptoBackend.dto;

import java.util.List;

import lombok.Data;

@Data
public class BlogRequest {
     
    private Integer newsId;
    private String title;
    public String imgUrl;
    public List<Section>section;
 
    @Data
    public static class Section{
        private String content;
        private String type;
        private Integer position;
        
    }



    
}
