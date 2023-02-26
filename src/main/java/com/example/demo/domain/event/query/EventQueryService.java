package com.example.demo.domain.event.query;


import com.example.demo.core.exception.EventNotFoundException;
import com.example.demo.core.generic.AbstractQueryServiceImpl;
import com.example.demo.domain.event.Event;
import com.example.demo.domain.event.EventRepository;
import io.gorse.gorse4j.Feedback;
import io.gorse.gorse4j.Gorse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventQueryService extends AbstractQueryServiceImpl<Event> {
    private final Gorse client;

    @Autowired
    public EventQueryService(EventRepository repository, Gorse client) {
        super(repository);
        this.client = client;
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

    public Event getEvent(UUID id, String userId) throws IOException {
        List<Feedback> feedbacks = List.of(
                new Feedback("viewEvent", userId, id.toString(),
                        LocalDateTime.now().toString())
        );
        client.insertFeedback(feedbacks);
        return ((EventRepository) repository)
                .findById(id)
                .orElseThrow(() -> new EventNotFoundException("event with id: " + id + " not found"));
    }

}
