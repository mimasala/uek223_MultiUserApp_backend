package com.example.demo.domain.event;

import com.example.demo.core.generic.AbstractEntity;
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
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> eventParticipants;
    private Integer participantsLimit;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String location;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    private User eventOwner;
}
