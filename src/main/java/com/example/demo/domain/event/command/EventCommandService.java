package com.example.demo.domain.event.command;

import com.example.demo.domain.event.Event;
import com.example.demo.domain.event.EventRepository;
import com.example.demo.domain.event.asyncEvents.EventCreatedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EventCommandService {
    private final EventRepository eventRepository;
    private final ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    public EventCommandService(EventRepository eventRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.eventRepository = eventRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEventCreationEvent(Event event) {
        System.out.println("Publishing custom event. ");
        EventCreatedMessage customSpringEvent = new EventCreatedMessage(this, event, LocalDateTime.now());
        applicationEventPublisher.publishEvent(customSpringEvent);

    }
    public Event createEvent(Event fromDTO) {
        publishEventCreationEvent(fromDTO);
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
