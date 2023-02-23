package com.example.demo.domain.event.command;


import com.example.demo.domain.event.dto.EventDTO;
import com.example.demo.domain.event.dto.EventMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController("/event")
public class EventCommandController {
    private final EventCommandService eventCommandService;
    private final EventMapper eventMapper;

    @Autowired
    public EventCommandController(EventCommandService eventCommandService, EventMapper eventMapper) {
        this.eventCommandService = eventCommandService;
        this.eventMapper = eventMapper;
    }

    @PostMapping("")
    @Operation(summary = "Create event")
    public ResponseEntity<EventDTO> createEvent(@Valid @RequestBody EventDTO eventDTO) {
        return ResponseEntity
                .ok()
                .body(eventMapper.toDTO(eventCommandService
                        .createEvent(eventMapper.fromDTO(eventDTO))));
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update event")
    public ResponseEntity<EventDTO> updateEvent(@Valid @RequestBody EventDTO eventDTO, @PathVariable UUID id) {
        return ResponseEntity
                .ok()
                .body(eventMapper.toDTO(eventCommandService
                        .updateEvent(eventMapper.fromDTO(eventDTO), id)));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete event")
    public ResponseEntity<UUID> deleteEvent(@PathVariable UUID id) {
        return ResponseEntity
                .ok()
                .body(eventCommandService.deleteEvent(id));
    }
}
