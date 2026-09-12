package com.tripify.backend.controller;

import com.tripify.backend.dto.itinerary.CreateItineraryRequest;
import com.tripify.backend.dto.itinerary.ItineraryResponse;
import com.tripify.backend.dto.itinerary.UpdateItineraryRequest;
import com.tripify.backend.entity.ItineraryStatus;
import com.tripify.backend.service.ItineraryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/itineraries")
public class ItineraryController {

    private final ItineraryService itineraryService;

    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;
    }

    @PostMapping
    public ResponseEntity<ItineraryResponse> createItinerary(
            @Valid @RequestBody CreateItineraryRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itineraryService.createItinerary(request));
    }

    @GetMapping
    public ResponseEntity<List<ItineraryResponse>> getMyItineraries() {
        return ResponseEntity.ok(
                itineraryService.getMyItineraries()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItineraryResponse> getMyItinerary(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(
                itineraryService.getMyItinerary(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItineraryResponse> updateItinerary(
            @PathVariable String id,
            @Valid @RequestBody UpdateItineraryRequest request
    ) {
        return ResponseEntity.ok(
                itineraryService.updateItinerary(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItinerary(
            @PathVariable String id
    ) {
        itineraryService.deleteItinerary(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ItineraryResponse> updateStatus(
            @PathVariable String id,
            @RequestParam ItineraryStatus status
    ) {
        return ResponseEntity.ok(
                itineraryService.updateStatus(id, status)
        );
    }
}