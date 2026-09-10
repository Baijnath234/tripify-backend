package com.tripify.backend.controller;

import com.tripify.backend.dto.destination.CreateDestinationRequest;
import com.tripify.backend.dto.destination.DestinationResponse;
import com.tripify.backend.dto.destination.UpdateDestinationRequest;
import com.tripify.backend.service.DestinationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tripify.backend.dto.destination.DestinationPageResponse;


@RestController
@RequestMapping("/api/admin/destinations")
public class AdminDestinationController {

    private final DestinationService destinationService;

    public AdminDestinationController(
            DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping
    public ResponseEntity<DestinationPageResponse> getAllDestinations(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) Boolean popular,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(
                destinationService.getAdminDestinations(
                        search,
                        country,
                        active,
                        popular,
                        page,
                        size
                )
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