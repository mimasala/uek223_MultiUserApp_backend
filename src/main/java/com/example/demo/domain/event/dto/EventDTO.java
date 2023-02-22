package com.example.demo.domain.event.dto;

import com.example.demo.core.generic.AbstractDTO;
import com.example.demo.domain.eventUser.EventUser;
import com.example.demo.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
public class EventDTO extends AbstractDTO {
    private String eventName;
    private Set<EventUser> eventParticipants;
    private Integer participantsLimit;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private String description;
    private User eventOwner;
}
