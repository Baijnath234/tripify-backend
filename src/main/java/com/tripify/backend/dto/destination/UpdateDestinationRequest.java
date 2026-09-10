package com.tripify.backend.dto.destination;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class UpdateDestinationRequest {

    @NotBlank(message = "Destination name is required")
    @Size(max = 150, message = "Destination name must not exceed 150 characters")
    private String name;

    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must not exceed 100 characters")
    private String country;

    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must not exceed 100 characters")
    private String city;

    @NotBlank(message = "Description is required")
    private String description;

    @Size(max = 500, message = "Image URL must not exceed 500 characters")
    private String imageUrl;

    @Size(max = 100, message = "Best time to visit must not exceed 100 characters")
    private String bestTimeToVisit;

    @NotNull(message = "Average budget is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Average budget cannot be negative")
    private BigDecimal averageBudget;

    @Size(max = 10, message = "Currency must not exceed 10 characters")
    private String currency;

    private boolean active = true;

    private boolean popular = false;

    public UpdateDestinationRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBestTimeToVisit() {
        return bestTimeToVisit;
    }

    public void setBestTimeToVisit(String bestTimeToVisit) {
        this.bestTimeToVisit = bestTimeToVisit;
    }

    public BigDecimal getAverageBudget() {
        return averageBudget;
    }

    public void setAverageBudget(BigDecimal averageBudget) {
        this.averageBudget = averageBudget;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPopular() {
        return popular;
    }

    public void setPopular(boolean popular) {
        this.popular = popular;
    }
}