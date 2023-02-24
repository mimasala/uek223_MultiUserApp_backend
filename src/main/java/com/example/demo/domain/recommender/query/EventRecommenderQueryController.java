package com.example.demo.domain.recommender.query;

import com.example.demo.domain.recommender.EventRecommendation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Operation(summary = "Get Recommendations for User")
    @PreAuthorize(
            "hasAuthority('USER_MODIFY') && @userPermissionEvaluator.isUser(authentication.principal.user, #userId)") //TODO (mimasala): check if this is correct
    public ResponseEntity<List<EventRecommendation>> getRecommendationsForUser(@PathVariable("userId") String userId,
                                                                               @RequestParam("page") int page,
                                                                               @RequestParam("pageLength") int pageLength) {
        return ResponseEntity.ok().body(
                eventRecommenderQueryService.getRecommendationForUser(userId, page, pageLength)
        );
    }
}
