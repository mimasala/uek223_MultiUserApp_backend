package com.example.demo.domain.eventUser.query;

import com.example.demo.domain.event.Event;
import com.example.demo.domain.event.dto.EventDTO;
import com.example.demo.domain.event.dto.EventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/eventUser")
public class EventUserQueryController {
    private final EventUserQueryService eventUserQueryService;

    private final EventMapper eventMapper;

    @Autowired
    public EventUserQueryController(EventUserQueryService eventUserQueryService, EventMapper eventMapper) {
        this.eventUserQueryService = eventUserQueryService;
        this.eventMapper = eventMapper;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<EventDTO>> getAllEventsOfUser(@PathVariable("userId") UUID userId,
                                                             @RequestParam(value = "event_start", required = false) Optional<Integer> eventStart) {
        List<Event> eventsOfUser = eventUserQueryService.getAllEventsOfUser(userId, eventStart);

        return ResponseEntity.ok()
                .body(eventMapper.toDTOs(eventsOfUser));
    }
}
