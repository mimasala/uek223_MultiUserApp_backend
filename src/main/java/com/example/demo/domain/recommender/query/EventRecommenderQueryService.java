package com.example.demo.domain.recommender.query;


import com.example.demo.domain.event.Event;
import com.example.demo.domain.event.EventRepository;
import com.example.demo.domain.eventUser.EventUserRepository;
import com.example.demo.domain.recommender.EventRecommendation;
import com.example.demo.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventRecommenderQueryService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private final EventUserRepository eventUserRepository;

    @Autowired
    public EventRecommenderQueryService(EventRepository eventRepository, UserRepository userRepository, EventUserRepository eventUserRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventUserRepository = eventUserRepository;
    }

    private boolean isCurrentUserEnrolledInEvent(String userId, Event event) {
        return !eventUserRepository.findAllByEvent(event)
                .stream()
                .filter(eventuser -> {
                    return eventuser.getUser().getId().toString().equals(userId);
                })
                .toList().isEmpty();
    }

    public List<EventRecommendation> getRecommendationForUser(String userId, int page, int pageLength) {
        return eventRepository.findAll(PageRequest.of(page, pageLength))
                .stream()
                .map(event -> new EventRecommendation(
                        isCurrentUserEnrolledInEvent(userId, event),
                        event, UUID.fromString(userId)))
                .distinct()
                .toList();
    }
}
