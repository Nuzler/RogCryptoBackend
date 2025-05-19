package com.example.RogCryptoBackend.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RogCryptoBackend.model.NewsItem;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/news")
public class NewsController {
    
    private final String feedUrl = "https://cointelegraph.com/rss";

    @GetMapping("/all")
    public List<NewsItem> getAllNews() {
        return getNews(0); // 0 = no limit
    }

    @GetMapping("/home")
    public List<NewsItem> getTopThreeNews() {
        return getNews(3);
    }

    
    public List<NewsItem> getNews(int limit) {
        System.out.println("Fetching news from feed: " + feedUrl);
        List<NewsItem> newsItems = new ArrayList<>();
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(feedUrl).openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            try (XmlReader reader = new XmlReader(conn)) {
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(reader);

                for (SyndEntry entry : feed.getEntries()) {
                    String title = entry.getTitle();
                    String link = entry.getLink();
                    String author = entry.getAuthor();
                    String rawDesc = entry.getDescription() != null ? entry.getDescription().getValue() : "";
                    String imageUrl = extractImageUrl(rawDesc);
                    String description = cleanDescription(rawDesc);
                    String publishedDate = entry.getPublishedDate() != null ? entry.getPublishedDate().toString() : "";
                    String category = entry.getCategories().isEmpty() ? "Uncategorized" : entry.getCategories().get(0).getName();

                    newsItems.add(new NewsItem(title, link, imageUrl, category, author, publishedDate, description,rawDesc));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return (limit > 0) ? newsItems.stream().limit(limit).collect(Collectors.toList()) : newsItems;
        
    }

      private String extractImageUrl(String html) {
        try {
            Document doc = Jsoup.parse(html);
            Element img = doc.selectFirst("img");
            return img != null ? img.attr("src") : "";
        } catch (Exception e) {
            return "";
        }
    }

    private String cleanDescription(String html) {
        try {
            Document doc = Jsoup.parse(html);
            doc.select("img").remove(); // remove image tags
            return doc.text(); // get plain text
        } catch (Exception e) {
            return html;
        }
    }


    
    
}
