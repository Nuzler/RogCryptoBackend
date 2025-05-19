package com.example.RogCryptoBackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RogCryptoBackend.model.Youtube;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/videos")
public class youtubeController {

    private final String feedUrl="https://www.youtube.com/feeds/videos.xml?channel_id=UCGSlCDyupzWjzS0QHO6LOug";

    @GetMapping("")
    public List<Youtube> getVideos(){
        
        List <Youtube> newYoutube =new ArrayList<>(); 
        try{
             HttpURLConnection conn = (HttpURLConnection) new URL(feedUrl).openConnection();
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            try (XmlReader reader = new XmlReader(conn)){
                 SyndFeedInput input = new SyndFeedInput();
                 SyndFeed feed = input.build(reader);
                  for (SyndEntry entry : feed.getEntries()) {
                    String link = entry.getLink();
                    String title = entry.getTitle();
                    String publishedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(entry.getPublishedDate());
                    String videoId = link.substring(link.indexOf("v=") + 2);
                    String thumbnail = "https://img.youtube.com/vi/" + videoId + "/hqdefault.jpg";
                    
                    newYoutube.add(new Youtube(link, title, publishedDate,thumbnail));
                    
                  }
            }
                
           
        }catch (Exception e) {
            e.printStackTrace();}


         return newYoutube.stream().limit(6).collect(Collectors.toList());
    }
    
}
