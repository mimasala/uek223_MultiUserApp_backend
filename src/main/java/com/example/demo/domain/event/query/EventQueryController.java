package com.example.demo.domain.event.query;

import com.example.demo.domain.event.dto.EventDTO;
import com.example.demo.domain.event.dto.EventMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/event")
public class EventQueryController {
    private final EventQueryService eventQueryService;
    private final EventMapper eventMapper;
    @Autowired
    public EventQueryController(EventQueryService eventQueryService, EventMapper eventMapper) {
        this.eventQueryService = eventQueryService;
        this.eventMapper = eventMapper;
    }

    @GetMapping()
    @Operation(summary = "Get all events")
    public ResponseEntity<List<EventDTO>> getEvents() {
        return ResponseEntity.ok().body(eventMapper.toDTOs(eventQueryService.getEvents()));
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get event by id")
    public ResponseEntity<EventDTO> getEvent(@PathVariable UUID id) {
        return ResponseEntity.ok().body(eventMapper.toDTO(eventQueryService.getEvent(id)));
    }
}
