package com.example.demo.domain.event.query;


import com.example.demo.core.exception.EventNotFoundException;
import com.example.demo.core.generic.AbstractQueryServiceImpl;
import com.example.demo.domain.event.Event;
import com.example.demo.domain.event.EventRepository;
import com.example.demo.domain.recommender.Gorse;
import io.gorse.gorse4j.Feedback;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
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
        log.info(String.format("Up-serting viewing-feedback of event(%s) by user(%s)", id.toString(), userId));
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
