package com.example.RogCryptoBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RogCryptoBackend.model.Event;
import com.example.RogCryptoBackend.services.EventServices;



@RequestMapping
@RestController
@CrossOrigin(origins = {"http://localhost:5174" , "http://localhost:5173"})
public class EventController {
    
    @Autowired
    EventServices eventServices;
    
    @PostMapping("/eventupload")
    public ResponseEntity<?> addEvent(@RequestBody List<Event> request){
        
        
        return ResponseEntity.ok(eventServices.addEvent(request));
        
    }

    
    @GetMapping("/getEvent")
    public List<Event> getEvents(){

        return eventServices.getEvents();
    }
   
}
