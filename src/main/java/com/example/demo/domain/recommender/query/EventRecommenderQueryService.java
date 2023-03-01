package com.example.demo.domain.recommender.query;


import com.example.demo.domain.event.Event;
import com.example.demo.domain.event.EventRepository;
import com.example.demo.domain.eventUser.EventUserRepository;
import com.example.demo.domain.recommender.EventRecommendation;
import com.example.demo.domain.recommender.Gorse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class EventRecommenderQueryService {
    private final EventRepository eventRepository;

    private final EventUserRepository eventUserRepository;

    private final Gorse gorse;

    @Autowired
    public EventRecommenderQueryService(EventRepository eventRepository, EventUserRepository eventUserRepository, Gorse gorse) {
        this.eventRepository = eventRepository;
        this.eventUserRepository = eventUserRepository;
        this.gorse = gorse;
    }

    private boolean isCurrentUserEnrolledInEvent(String userId, Event event) {
        return !eventUserRepository.findAllByEvent(event)
                .stream()
                .filter(eventuser -> eventuser.getUser().getId().toString().equals(userId))
                .toList().isEmpty();
    }
    private List<Event> getEventsFromRecommendations(List<String> recommendations) {
        List<Event> events = new ArrayList<>();

        for ( String recommendation: recommendations ) {
            events.add(
                    eventRepository.findById(UUID.fromString(recommendation)).orElseThrow(() -> new RuntimeException("Failed to find Recommendation: " + recommendation))
            );
        }

        return events;
    }
    public List<EventRecommendation> getRecommendationForUser(String userId, int page, int pageLength) throws IOException {
        log.info("Getting recommendations for user: " + userId);
        List<String> recommendations = gorse.getRecommend(userId, pageLength, page);

        List<Event> events = getEventsFromRecommendations(recommendations);

        return events
                .stream()
                .map(event -> new EventRecommendation(
                        isCurrentUserEnrolledInEvent(userId, event),
                        event, UUID.fromString(userId)))
                .distinct()
                .toList();
    }
}
