package com.example.demo.domain.event;

import com.example.demo.core.generic.AbstractEntity;
import com.example.demo.domain.eventUser.EventUser;
import com.example.demo.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "events")
@Entity
public class Event extends AbstractEntity {
    private String eventName;
    @OneToMany(fetch = FetchType.EAGER)
    private Set<EventUser> eventParticipants;
    private Integer participantsLimit;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private String description;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.EAGER)
    private User eventOwner;
}
