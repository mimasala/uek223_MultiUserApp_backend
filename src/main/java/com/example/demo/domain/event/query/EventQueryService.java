package com.example.demo.domain.event.query;

import com.example.demo.core.generic.AbstractQueryServiceImpl;
import com.example.demo.domain.event.Event;
import com.example.demo.domain.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventQueryService extends AbstractQueryServiceImpl<Event> {
    @Autowired
    public EventQueryService(EventRepository repository) {
        super(repository);
    }
    public boolean hasCapacityLeftForEnrollment(Event event) {
        return event.getEventParticipants().size() < event.getParticipantsLimit();
    }
}
