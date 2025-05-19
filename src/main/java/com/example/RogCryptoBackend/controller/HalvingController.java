package com.example.RogCryptoBackend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RogCryptoBackend.services.HalvingScraperService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // enable CORS for frontend access
public class HalvingController {

    @Autowired
    private HalvingScraperService halvingScraperService;

    @GetMapping("/halving-countdown")
    public String getHalvingCountdown() {
        return halvingScraperService.getCountdown();
    }
}
