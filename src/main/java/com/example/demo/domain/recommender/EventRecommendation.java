package com.example.demo.domain.recommender;


import com.example.demo.domain.eventUser.EventUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventRecommendation {
    private String eventName;
    private boolean isCurrentUserEnrolled;
    private String imageUrl;


    public EventRecommendation(EventUser eventUser, UUID userId) {
        isCurrentUserEnrolled = eventUser.getUser().getId().equals(userId);
        eventName = eventUser.getEvent().getEventName();
        imageUrl = "https://hips.hearstapps.com/hmg-prod/images/wolf-dog-breeds-siberian-husky-1570411330.jpg? er,top&resize=1200:*";
    }
}
