package com.tripify.backend.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "trip_requests",
        indexes = {
                @Index(name = "idx_trip_request_user_id", columnList = "user_id"),
                @Index(name = "idx_trip_request_itinerary_id", columnList = "itinerary_id"),
                @Index(name = "idx_trip_request_status", columnList = "status")
        }
)
public class TripRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * User who submitted the trip request.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Itinerary submitted by the user.
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "itinerary_id", nullable = false, unique = true)
    private Itinerary itinerary;

    /**
     * Current lifecycle status of the trip request.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TripRequestStatus status = TripRequestStatus.NOT_SUBMITTED;

    /**
     * Additional requirements or instructions provided by the user.
     */
    @Column(length = 3000)
    private String customerNotes;

    /**
     * Internal notes added by the admin/travel team.
     */
    @Column(length = 5000)
    private String adminNotes;

    /**
     * When the user submitted the itinerary for processing.
     */
    private LocalDateTime submittedAt;

    /**
     * When an admin first reviewed the request.
     */
    private LocalDateTime reviewedAt;

    /**
     * When the request was finalized.
     */
    private LocalDateTime finalizedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();

        createdAt = now;
        updatedAt = now;

        if (status == null) {
            status = TripRequestStatus.NOT_SUBMITTED;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public TripRequest() {
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Itinerary getItinerary() {
        return itinerary;
    }

    public TripRequestStatus getStatus() {
        return status;
    }

    public String getCustomerNotes() {
        return customerNotes;
    }

    public String getAdminNotes() {
        return adminNotes;
    }

    public LocalDateTime getSubmittedAt() {
        return submittedAt;
    }

    public LocalDateTime getReviewedAt() {
        return reviewedAt;
    }

    public LocalDateTime getFinalizedAt() {
        return finalizedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    public void setStatus(TripRequestStatus status) {
        this.status = status;
    }

    public void setCustomerNotes(String customerNotes) {
        this.customerNotes = customerNotes;
    }

    public void setAdminNotes(String adminNotes) {
        this.adminNotes = adminNotes;
    }

    public void setSubmittedAt(LocalDateTime submittedAt) {
        this.submittedAt = submittedAt;
    }

    public void setReviewedAt(LocalDateTime reviewedAt) {
        this.reviewedAt = reviewedAt;
    }

    public void setFinalizedAt(LocalDateTime finalizedAt) {
        this.finalizedAt = finalizedAt;
    }
}