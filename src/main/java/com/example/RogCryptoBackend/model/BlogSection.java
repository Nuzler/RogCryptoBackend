package com.example.RogCryptoBackend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;



@Data
@Entity
@Table(name="content")
public class BlogSection {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer contentId;
    @Lob
    @Column(columnDefinition = "TEXT")
    public String content;
    public Integer Position;
    public String type;
    
    @ManyToOne
    @JoinColumn(name = "news_id")
    public Blog blog ;
    
}
