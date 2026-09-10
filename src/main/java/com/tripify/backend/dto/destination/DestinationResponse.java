package com.tripify.backend.dto.destination;

import com.tripify.backend.entity.Destination;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DestinationResponse {

    private String id;
    private String name;
    private String country;
    private String city;
    private String description;
    private String imageUrl;
    private String bestTimeToVisit;
    private BigDecimal averageBudget;
    private String currency;
    private boolean active;
    private boolean popular;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DestinationResponse() {
    }

    public DestinationResponse(Destination destination) {
        this.id = destination.getId();
        this.name = destination.getName();
        this.country = destination.getCountry();
        this.city = destination.getCity();
        this.description = destination.getDescription();
        this.imageUrl = destination.getImageUrl();
        this.bestTimeToVisit = destination.getBestTimeToVisit();
        this.averageBudget = destination.getAverageBudget();
        this.currency = destination.getCurrency();
        this.active = destination.isActive();
        this.popular = destination.isPopular();
        this.createdAt = destination.getCreatedAt();
        this.updatedAt = destination.getUpdatedAt();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getBestTimeToVisit() {
        return bestTimeToVisit;
    }

    public BigDecimal getAverageBudget() {
        return averageBudget;
    }

    public String getCurrency() {
        return currency;
    }

    public boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }
}