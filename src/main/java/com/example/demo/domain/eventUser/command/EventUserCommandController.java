package com.example.demo.domain.eventUser.command;

import com.example.demo.core.adapter.LocalDateTimeAdapter;
import com.example.demo.core.exception.NotCheckedException;
import com.example.demo.core.generic.StatusOr;
import com.example.demo.domain.eventUser.EventUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Validated
@RestController
@Log4j2
@RequestMapping("/eventUser")
public class EventUserCommandController {
    private final EventUserCommandService eventUserCommandService;

    @Autowired
    public EventUserCommandController(EventUserCommandService eventUserCommandService) {
        this.eventUserCommandService = eventUserCommandService;
    }

    @PostMapping
    @Operation(summary = "Create EventUser")
    @PreAuthorize("hasAuthority('USER_MODIFY') || @userPermissionEvaluator.isUser(authentication.principal.user, #userId)" +
            "|| @userPermissionEvaluator.isEventOwner(authentication.principal.user, #eventId)")
    public ResponseEntity<String> signUserUpForEvent(@RequestParam("user_id") UUID userId,
                                                     @RequestParam("event_id") UUID eventId) throws NotCheckedException, IOException {
        log.info(String.format("Enrolling user: %s in event %s", userId.toString(), eventId.toString()));
        StatusOr<EventUser> eventRegistration = eventUserCommandService.registerUserForEvent(userId, eventId);

        if(!eventRegistration.isOkAndPresent()) {
            return ResponseEntity
                    .status(eventRegistration.getStatus())
                    .body(eventUserCommandService.getMessageForEventRegistration(eventRegistration.getStatus()));
        }

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();

        return ResponseEntity
                .status(eventRegistration.getStatus())
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(gson.toJson(eventRegistration.getItem()));
    }

    @DeleteMapping
    @Operation(summary = "Delete EventUser")
    @PreAuthorize("hasRole('ADMIN') || (@userPermissionEvaluator.isUser(authentication.principal.user, #userId) ||" +
            "@userPermissionEvaluator.isEventOwner(authentication.principal.user, #eventId))")
    public ResponseEntity<String> deleteUserFromEvent(@RequestParam("user_id") UUID userId,
                                                      @RequestParam("event_id") UUID eventId) {
        log.info(String.format("De-enrolling user: %s and event: %s", userId.toString(), eventId.toString()));

        HttpStatus status = eventUserCommandService.deleteUserFromEvent(userId, eventId);

        return ResponseEntity.status(status)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(eventUserCommandService.getMessageForUserEventDeletion(status));
    }
}
