package com.example.RogCryptoBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RogCryptoBackend.model.Blog;

public interface  BlogRepository extends JpaRepository<Blog, Integer> {
    
}
