package com.example.RogCryptoBackend.services;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class HalvingScraperService {

    public String getCountdown() {
        try {
            Document doc = Jsoup.connect("https://www.bitcoinblockhalf.com/").get();
            // Adjust this selector based on actual site
            Elements countdown = doc.select("#counter"); 
            return countdown.text();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error fetching countdown";
        }
    }
}