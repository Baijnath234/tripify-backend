package com.tripify.backend.dto.itinerary;

import com.tripify.backend.entity.Itinerary;
import com.tripify.backend.entity.ItineraryStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ItineraryResponse {

    private String id;
    private String userId;
    private String title;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer travelers;
    private Double budget;
    private ItineraryStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ItineraryResponse(Itinerary itinerary) {
        this.id = itinerary.getId();
        this.userId = itinerary.getUser().getId();
        this.title = itinerary.getTitle();
        this.destination = itinerary.getDestination();
        this.startDate = itinerary.getStartDate();
        this.endDate = itinerary.getEndDate();
        this.travelers = itinerary.getTravelers();
        this.budget = itinerary.getBudget();
        this.status = itinerary.getStatus();
        this.createdAt = itinerary.getCreatedAt();
        this.updatedAt = itinerary.getUpdatedAt();
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Integer getTravelers() {
        return travelers;
    }

    public Double getBudget() {
        return budget;
    }

    public ItineraryStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}