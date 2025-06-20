package com.example.RogCryptoBackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class NewsItem {

    private String title;
    private String link;
    private String imageUrl;
    private String category;
    private String author;
    private String publishedDate;
    private String description;
    private String contentEncoded;
    private String rawDesc;
   
    
}
