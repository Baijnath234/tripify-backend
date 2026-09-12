package com.tripify.backend.repository;

import com.tripify.backend.entity.Itinerary;
import com.tripify.backend.entity.ItineraryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItineraryRepository extends JpaRepository<Itinerary, String> {

    List<Itinerary> findByUserIdOrderByCreatedAtDesc(String userId);

    Optional<Itinerary> findByIdAndUserId(String id, String userId);

    List<Itinerary> findByUserIdAndStatusOrderByCreatedAtDesc(
            String userId,
            ItineraryStatus status
    );
}