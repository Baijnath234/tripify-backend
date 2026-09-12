package com.tripify.backend.dto.itinerary.activity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ItineraryActivityResponse {

    private String id;
    private String itineraryId;
    private LocalDate activityDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String title;
    private String type;
    private String location;
    private String description;
    private Double estimatedCost;
    private Integer orderIndex;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ItineraryActivityResponse(
            String id,
            String itineraryId,
            LocalDate activityDate,
            LocalTime startTime,
            LocalTime endTime,
            String title,
            String type,
            String location,
            String description,
            Double estimatedCost,
            Integer orderIndex,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.itineraryId = itineraryId;
        this.activityDate = activityDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.type = type;
        this.location = location;
        this.description = description;
        this.estimatedCost = estimatedCost;
        this.orderIndex = orderIndex;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getItineraryId() {
        return itineraryId;
    }

    public LocalDate getActivityDate() {
        return activityDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public Double getEstimatedCost() {
        return estimatedCost;
    }

    public Integer getOrderIndex() {
        return orderIndex;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}