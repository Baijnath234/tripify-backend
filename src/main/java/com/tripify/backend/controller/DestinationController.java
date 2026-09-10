package com.tripify.backend.controller;

import com.tripify.backend.dto.destination.DestinationResponse;
import com.tripify.backend.service.DestinationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(
            DestinationService destinationService
    ) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public ResponseEntity<List<DestinationResponse>> getDestinations() {
        return ResponseEntity.ok(
                destinationService.getPublicDestinations()
        );
    }

    @GetMapping("/popular")
    public ResponseEntity<List<DestinationResponse>> getPopularDestinations() {
        return ResponseEntity.ok(destinationService.getPopularDestinations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationResponse> getDestinationById(
            @PathVariable String id
    ) {
        return ResponseEntity.ok(
                destinationService.getPublicDestinationById(id)
        );
    }
}