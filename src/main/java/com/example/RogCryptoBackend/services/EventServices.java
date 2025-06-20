package com.example.RogCryptoBackend.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RogCryptoBackend.model.Event;
import com.example.RogCryptoBackend.repository.EventRepository;



@Service
public class EventServices {
    
    @Autowired
    EventRepository eventRepository;

    public List<Event> addEvent(List<Event> requset){
        
       return eventRepository.saveAll(requset);
    } 


    public List<Event> getEvents(){

        return eventRepository.findAll();
    }

}
