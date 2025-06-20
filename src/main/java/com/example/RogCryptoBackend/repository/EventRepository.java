package com.example.RogCryptoBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RogCryptoBackend.model.Event;

public  interface EventRepository extends JpaRepository<Event, Integer>{
    
}
