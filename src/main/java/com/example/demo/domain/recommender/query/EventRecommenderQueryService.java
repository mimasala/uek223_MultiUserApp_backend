package com.example.demo.domain.recommender.query;


import com.example.demo.domain.event.EventRepository;
import com.example.demo.domain.eventUser.EventUserRepository;
import com.example.demo.domain.recommender.EventRecommendation;
import com.example.demo.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    public List<EventRecommendation> getRecommendationForUser(String userId, int page, int pageLength) {
        return eventRepository.findAll(PageRequest.of(page, pageLength))
                .stream()
                .map(event -> {
                    return eventUserRepository.findAllByEvent(event);
                })
                .flatMap(Collection::stream)
                .map(eventUser -> {
                    return new EventRecommendation(eventUser, UUID.fromString(userId));
                })
                .toList();
    }
}
