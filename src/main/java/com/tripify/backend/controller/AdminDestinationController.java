package com.tripify.backend.controller;

import com.tripify.backend.dto.destination.CreateDestinationRequest;
import com.tripify.backend.dto.destination.DestinationResponse;
import com.tripify.backend.dto.destination.UpdateDestinationRequest;
import com.tripify.backend.service.DestinationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/destinations")
public class AdminDestinationController {

    private final DestinationService destinationService;

    public AdminDestinationController(
            DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public ResponseEntity<List<DestinationResponse>> getAllDestinations() {
        return ResponseEntity.ok(
                destinationService.getAllDestinations()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DestinationResponse> getDestinationById(
            @PathVariable String id) {

        return ResponseEntity.ok(
                destinationService.getDestinationById(id)
        );
    }

    @PostMapping
    public ResponseEntity<DestinationResponse> createDestination(
            @Valid @RequestBody CreateDestinationRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(destinationService.createDestination(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DestinationResponse> updateDestination(
            @PathVariable String id,
            @Valid @RequestBody UpdateDestinationRequest request) {

        return ResponseEntity.ok(
                destinationService.updateDestination(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(
            @PathVariable String id) {

        destinationService.deleteDestination(id);

        return ResponseEntity.noContent().build();
    }
}