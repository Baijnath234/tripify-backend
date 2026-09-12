package com.tripify.backend.dto.itinerary.activity;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class ReorderItineraryActivitiesRequest {

    @NotEmpty
    private List<String> activityIds;

    public ReorderItineraryActivitiesRequest() {
    }

    public List<String> getActivityIds() {
        return activityIds;
    }

    public void setActivityIds(List<String> activityIds) {
        this.activityIds = activityIds;
    }
}