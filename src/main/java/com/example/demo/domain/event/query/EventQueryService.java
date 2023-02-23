package com.example.demo.domain.event.query;

import com.example.demo.domain.event.Event;
import com.example.demo.domain.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventQueryService {
    private final EventRepository eventRepository;
    @Autowired
    public EventQueryService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public Event getEvent(UUID id) {
        return eventRepository.findById(id).orElseThrow(); //TODO: handle not found
    }
}
