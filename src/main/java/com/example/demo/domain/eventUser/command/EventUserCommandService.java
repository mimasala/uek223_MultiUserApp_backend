package com.example.demo.domain.eventUser.command;

import com.example.demo.core.generic.AbstractCommandServiceImpl;
import com.example.demo.core.generic.StatusOr;
import com.example.demo.domain.event.Event;
import com.example.demo.domain.event.EventRepository;
import com.example.demo.domain.event.query.EventQueryService;
import com.example.demo.domain.eventUser.EventUser;
import com.example.demo.domain.eventUser.EventUserRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class EventUserCommandService extends AbstractCommandServiceImpl<EventUser> {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventUserRepository eventUserRepository;
    private final EventQueryService eventQueryService;

    @Autowired
    protected EventUserCommandService(EventUserRepository repository, UserRepository userRepository, EventRepository eventRepository, EventUserRepository eventUserRepository, EventQueryService eventQueryService) {
        super(repository);
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.eventUserRepository = eventUserRepository;
        this.eventQueryService = eventQueryService;
    }

    StatusOr<EventUser> getEventUserIfValid(Optional<Event> event, Optional<User> user) {
        if (user.isEmpty() || event.isEmpty()) {
            return new StatusOr<>(HttpStatus.NOT_FOUND);
        }

        if (!eventQueryService.hasCapacityLeftForEnrollment(event.get())) {
            return new StatusOr<>(HttpStatus.TOO_MANY_REQUESTS);
        }
        return new StatusOr<>(HttpStatus.OK);
    }

    StatusOr<EventUser> registerUserForEvent(UUID userId, UUID eventId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Event> event = eventRepository.findById(eventId);

        StatusOr<EventUser> validationStatus = getEventUserIfValid(event, user);

        if( !validationStatus.isOk() ) {
            return validationStatus;
        }

        EventUser eventUser = new EventUser(user.get(), event.get());
        eventUser = eventUserRepository.save(eventUser);

        return new StatusOr<>(eventUser);
    }

    String getErrorMessageForEventRegistrationFailure(HttpStatus status) {
        if(!status.isError()) {
            return "The request succeeded.";
        }
        switch (status) {
            case TOO_MANY_REQUESTS -> {
                return "The number of participants for the have been succeeded.";
            }
            case NOT_FOUND -> {
                return "Either the user or the have not been found.";
            }
        }
        return "Can't create error message - HttpStatus is not known";
    }
}
