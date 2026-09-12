package com.tripify.backend.entity;

public enum TripRequestStatus {

    NOT_SUBMITTED,

    SUBMITTED,

    UNDER_REVIEW,

    PROCESSING,

    BOOKING_IN_PROGRESS,

    READY_FOR_REVIEW,

    FINALIZED,

    CANCELLED;

    public boolean canTransitionTo(TripRequestStatus nextStatus) {

        if (nextStatus == null) {
            return false;
        }

        return switch (this) {

            case NOT_SUBMITTED ->
                    nextStatus == SUBMITTED;

            case SUBMITTED ->
                    nextStatus == UNDER_REVIEW
                            || nextStatus == CANCELLED;

            case UNDER_REVIEW ->
                    nextStatus == PROCESSING
                            || nextStatus == CANCELLED;

            case PROCESSING ->
                    nextStatus == BOOKING_IN_PROGRESS
                            || nextStatus == CANCELLED;

            case BOOKING_IN_PROGRESS ->
                    nextStatus == READY_FOR_REVIEW
                            || nextStatus == CANCELLED;

            case READY_FOR_REVIEW ->
                    nextStatus == FINALIZED;

            case FINALIZED ->
                    false;

            case CANCELLED ->
                    false;
        };
    }
}