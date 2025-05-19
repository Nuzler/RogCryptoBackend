package com.example.RogCryptoBackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.RogCryptoBackend.model.BlogSection;

import jakarta.transaction.Transactional;

public interface BlogSectionRepository extends JpaRepository<BlogSection, Integer>   {
    
  List<BlogSection> findByBlog_NewsId(Integer newsId);
  @Transactional
  @Modifying
  @Query("DELETE FROM BlogSection bs WHERE bs.blog.newsId = :newsId")
  void deleteAllByBlogNewsId(Integer newsId);

}
