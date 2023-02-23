package com.example.demo.domain.eventUser.command;

import com.example.demo.core.exception.NotCheckedException;
import com.example.demo.core.generic.StatusOr;
import com.example.demo.domain.eventUser.EventUser;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@RestController
@RequestMapping("/eventUser")
public class EventUserCommandController {
    private final EventUserCommandService eventUserCommandService;

    @Autowired
    public EventUserCommandController(EventUserCommandService eventUserCommandService) {
        this.eventUserCommandService = eventUserCommandService;
    }

    @PostMapping
    public ResponseEntity<String> signUserUpForEvent(@RequestParam("user_id") UUID userId,
                                                     @RequestParam("event_id") UUID eventId) throws NotCheckedException {
        StatusOr<EventUser> eventRegistration = eventUserCommandService.registerUserForEvent(userId, eventId);

        if(!eventRegistration.isOkAndPresent()) {
            return ResponseEntity
                    .status(eventRegistration.getStatus())
                    .body(eventUserCommandService.getMessageForEventRegistration(eventRegistration.getStatus()));
        }

        return ResponseEntity
                .status(eventRegistration.getStatus())
                .body(new Gson().toJson(eventRegistration.getItem()));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUserFromEvent(@RequestParam("user_id") UUID userId,
                                                      @RequestParam("event_id") UUID eventId) {
        HttpStatus status = eventUserCommandService.deleteUserFromEvent(userId, eventId);

        return ResponseEntity.status(status)
                .body(eventUserCommandService.getMessageForUserEventDeletion(status));
    }
}
