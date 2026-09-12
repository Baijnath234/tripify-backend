package com.tripify.backend.controller;

import com.tripify.backend.dto.itinerary.activity.CreateItineraryActivityRequest;
import com.tripify.backend.dto.itinerary.activity.ItineraryActivityResponse;
import com.tripify.backend.dto.itinerary.activity.UpdateItineraryActivityRequest;
import com.tripify.backend.service.ItineraryActivityService;
import com.tripify.backend.dto.itinerary.activity.ReorderItineraryActivitiesRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itineraries/{itineraryId}/activities")
public class ItineraryActivityController {

    private final ItineraryActivityService activityService;

    public ItineraryActivityController(
            ItineraryActivityService activityService
    ) {
        this.activityService = activityService;
    }

    @PostMapping
    public ResponseEntity<ItineraryActivityResponse> createActivity(
            @PathVariable String itineraryId,
            @Valid @RequestBody CreateItineraryActivityRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(activityService.createActivity(itineraryId, request));
    }

    @GetMapping
    public ResponseEntity<List<ItineraryActivityResponse>> getActivities(
            @PathVariable String itineraryId
    ) {
        return ResponseEntity.ok(
                activityService.getActivities(itineraryId)
        );
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ItineraryActivityResponse> getActivity(
            @PathVariable String itineraryId,
            @PathVariable String activityId
    ) {
        return ResponseEntity.ok(
                activityService.getActivity(itineraryId, activityId)
        );
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<ItineraryActivityResponse> updateActivity(
            @PathVariable String itineraryId,
            @PathVariable String activityId,
            @Valid @RequestBody UpdateItineraryActivityRequest request
    ) {
        return ResponseEntity.ok(
                activityService.updateActivity(
                        itineraryId,
                        activityId,
                        request
                )
        );
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<Void> deleteActivity(
            @PathVariable String itineraryId,
            @PathVariable String activityId
    ) {
        activityService.deleteActivity(itineraryId, activityId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/reorder")
    public ResponseEntity<List<ItineraryActivityResponse>> reorderActivities(
            @PathVariable String itineraryId,
            @Valid @RequestBody ReorderItineraryActivitiesRequest request
    ) {
        return ResponseEntity.ok(
                activityService.reorderActivities(
                        itineraryId,
                        request
                )
        );
    }
}