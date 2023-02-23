package com.example.demo.domain.recommender.query;

import com.example.demo.domain.recommender.EventRecommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendation")
public class EventRecommenderQueryController {
    private final EventRecommenderQueryService eventRecommenderQueryService;

    @Autowired
    public EventRecommenderQueryController(EventRecommenderQueryService eventRecommenderQueryService) {
        this.eventRecommenderQueryService = eventRecommenderQueryService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<EventRecommendation>> getRecommendationsForUser(@PathVariable("userId") String userId,
                                                                               @RequestParam("page") int page,
                                                                               @RequestParam("pageLength") int pageLength) {

        return ResponseEntity.ok().body(
                eventRecommenderQueryService.getRecommendationForUser(userId, page, pageLength)
        );
    }
}
