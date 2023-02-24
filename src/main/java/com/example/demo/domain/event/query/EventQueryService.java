package com.example.demo.domain.event.query;


import com.example.demo.core.exception.EventNotFoundException;
import com.example.demo.core.generic.AbstractQueryServiceImpl;
import com.example.demo.domain.event.Event;
import com.example.demo.domain.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventQueryService extends AbstractQueryServiceImpl<Event> {
    @Autowired
    public EventQueryService(EventRepository repository) {
        super(repository);
    }
    public boolean hasCapacityLeftForEnrollment(Event event) {
        return event.getEventParticipants().size() < event.getParticipantsLimit();
    }

    public List<Event> getEvents(Optional<UUID> userId) {
        return  ((EventRepository) repository)
                .findAll()
                .stream()
                .filter(event -> userId.isEmpty() || event.getEventOwner().getId().equals(userId.get()))
                .toList();
    }

    public Event getEvent(UUID id) {
        return ((EventRepository) repository).findById(id).orElseThrow(() -> new EventNotFoundException("event with id: " + id + " not found"));
    }

    public double getPageCount(Integer pageLength) {
        if(pageLength == 0) {
            return 1;
        }
        return Math.ceil(repository.count() / (float) pageLength);
    }
}
