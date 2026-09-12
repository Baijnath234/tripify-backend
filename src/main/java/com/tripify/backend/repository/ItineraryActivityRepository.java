package com.tripify.backend.repository;

import com.tripify.backend.entity.ItineraryActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItineraryActivityRepository
        extends JpaRepository<ItineraryActivity, String> {

    List<ItineraryActivity> findByItineraryIdOrderByActivityDateAscStartTimeAscOrderIndexAsc(
            String itineraryId
    );

    Optional<ItineraryActivity> findByIdAndItineraryId(
            String id,
            String itineraryId
    );

    void deleteByItineraryId(String itineraryId);
}