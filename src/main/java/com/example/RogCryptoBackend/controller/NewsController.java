package com.example.RogCryptoBackend.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jdom2.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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

    private final String feedUrl = "https://beincrypto.com/feed/";

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
                     String imageUrl = extractMediaContentImage(entry);
                     String contentEncoded = extractContentEncodedWithJsoup(feedUrl, link);
                      
                    String description = entry.getDescription().getValue();
                    String publishedDate = entry.getPublishedDate() != null ? entry.getPublishedDate().toString() : "";
                    String category = entry.getCategories().isEmpty() ? "Uncategorized" : entry.getCategories().get(0).getName();

                    newsItems.add(new NewsItem(title, link, imageUrl, category, author, publishedDate, description,contentEncoded ,rawDesc));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (limit > 0) ? newsItems.stream().limit(limit).collect(Collectors.toList()) : newsItems;
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

    private String extractMediaContentImage(SyndEntry entry) {
    List<Element> foreignMarkup = entry.getForeignMarkup();
    for (Element elem : foreignMarkup) {
        if ("content".equals(elem.getName()) && "media".equals(elem.getNamespacePrefix())) {
            String url = elem.getAttributeValue("url");
            if (url != null && !url.isEmpty()) {
                return url;
            }
        }
    }
    return null;
}


private String extractContentEncodedWithJsoup(String feedUrl, String entryLink) {
    try {
        Document doc = Jsoup.connect(feedUrl)
                            .parser(org.jsoup.parser.Parser.xmlParser())
                            .userAgent("Mozilla/5.0")
                            .timeout(10000)
                            .get();

        for (org.jsoup.nodes.Element item : doc.select("item")) {
            String link = item.select("link").text().trim();
            if (link.equals(entryLink)) {
                org.jsoup.nodes.Element contentEncoded = item.selectFirst("content\\:encoded");  // <--- escape colon
                return contentEncoded != null ? contentEncoded.text() : "";
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "";
}


}

