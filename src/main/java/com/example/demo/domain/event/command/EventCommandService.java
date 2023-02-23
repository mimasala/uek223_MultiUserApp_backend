package com.example.demo.domain.event.command;

import com.example.demo.domain.event.Event;
import com.example.demo.domain.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EventCommandService {
    private final EventRepository eventRepository;
    @Autowired
    public EventCommandService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    public Event createEvent(Event fromDTO) {
        return eventRepository.save(fromDTO);
    }

    public Event updateEvent(Event fromDTO, UUID id) {
        fromDTO.setId(id);
        return eventRepository.save(fromDTO);
    }

    public UUID deleteEvent(UUID id) {
        eventRepository.deleteById(id);
        return id;
    }
}
