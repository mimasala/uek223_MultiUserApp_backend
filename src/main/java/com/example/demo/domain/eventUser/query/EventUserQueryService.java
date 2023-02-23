package com.example.demo.domain.eventUser.query;

import com.example.demo.core.generic.AbstractQueryServiceImpl;
import com.example.demo.domain.event.Event;
import com.example.demo.domain.eventUser.EventUser;
import com.example.demo.domain.eventUser.EventUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.UUID;

@Service
public class EventUserQueryService extends AbstractQueryServiceImpl<EventUser> {

    @Autowired
    public EventUserQueryService(EventUserRepository repository) {
        super(repository);
    }

    private List<Event> getAllEventsOfUser(UUID user) {
        return ((EventUserRepository) repository)
                .findAll()
                .stream()
                .map(EventUser::getEvent)
                .toList();
    }

    List<Event> getAllEventsOfUser(UUID userId,
                                   Optional<Integer> event_start) {
        return getAllEventsOfUser(userId)
                .stream()
                .filter(event -> event.getStartDate()
                        .isAfter(LocalDateTime.ofEpochSecond(event_start.orElse(0), 0, ZoneOffset.of("CET"))))
                .toList();
    }
}
