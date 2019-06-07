package com.umbum.pos.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.umbum.pos.model.Event;
import com.umbum.pos.repository.EventRepo;

@Service
public class EventService {
    private final EventRepo eventRepo;

    public EventService(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    public List<Event> getEvents(long productId) {
        return eventRepo.readAll(productId);
    }
}
