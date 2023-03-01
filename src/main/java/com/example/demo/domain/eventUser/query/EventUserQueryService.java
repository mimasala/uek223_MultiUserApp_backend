package com.example.demo.domain.eventUser.query;

import com.example.demo.core.generic.AbstractQueryServiceImpl;
import com.example.demo.domain.event.Event;
import com.example.demo.domain.event.EventRepository;
import com.example.demo.domain.eventUser.EventUser;
import com.example.demo.domain.eventUser.EventUserRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventUserQueryService extends AbstractQueryServiceImpl<EventUser> {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventUserQueryService(EventUserRepository repository, EventRepository eventRepository, UserRepository userRepository) {
        super(repository);
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
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
                        .isAfter(LocalDateTime.ofEpochSecond(event_start.orElse(0), 0, ZoneOffset.of("+1"))))
                .toList();
    }

    private boolean areAnyUserRoles(String roleName, User user) {
        return !user.getRoles()
                .stream()
                .filter(role -> role.getName().equals(roleName))
                .toList()
                .isEmpty();
    }

    public boolean isUserAllowedToGetEventParticipants(UUID eventId, User user) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Unable to find event with id: " + eventId.toString()));

        if (event.getEventOwner().getId() == user.getUserId()) {
            return true;
        }
        if(areAnyUserRoles("ADMIN", user)) {
            return true;
        }

        return false;
    }

    public List<User> getAllParticipantsOfEvent(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Unable to find event with id: " + eventId.toString()));

        return ((EventUserRepository) repository).findAllByEvent(event)
                .stream()
                .map(EventUser::getUser)
                .toList();
    }
}
