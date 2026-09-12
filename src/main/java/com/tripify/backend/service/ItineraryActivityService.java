package com.tripify.backend.service;

import com.tripify.backend.dto.itinerary.activity.CreateItineraryActivityRequest;
import com.tripify.backend.dto.itinerary.activity.ItineraryActivityResponse;
import com.tripify.backend.dto.itinerary.activity.UpdateItineraryActivityRequest;
import com.tripify.backend.entity.Itinerary;
import com.tripify.backend.entity.ItineraryActivity;
import com.tripify.backend.entity.User;
import com.tripify.backend.repository.ItineraryActivityRepository;
import com.tripify.backend.repository.ItineraryRepository;
import com.tripify.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tripify.backend.dto.itinerary.activity.ReorderItineraryActivitiesRequest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ItineraryActivityService {

    private final ItineraryActivityRepository activityRepository;
    private final ItineraryRepository itineraryRepository;
    private final UserRepository userRepository;

    public ItineraryActivityService(
            ItineraryActivityRepository activityRepository,
            ItineraryRepository itineraryRepository,
            UserRepository userRepository
    ) {
        this.activityRepository = activityRepository;
        this.itineraryRepository = itineraryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ItineraryActivityResponse createActivity(
            String itineraryId,
            CreateItineraryActivityRequest request
    ) {
        Itinerary itinerary = getOwnedItinerary(itineraryId);

        validateActivity(request.getActivityDate(),
                request.getStartTime(),
                request.getEndTime(),
                itinerary);

        ItineraryActivity activity = new ItineraryActivity();

        activity.setItinerary(itinerary);
        activity.setActivityDate(request.getActivityDate());
        activity.setStartTime(request.getStartTime());
        activity.setEndTime(request.getEndTime());
        activity.setTitle(request.getTitle().trim());
        activity.setType(request.getType());
        activity.setLocation(request.getLocation());
        activity.setDescription(request.getDescription());
        activity.setEstimatedCost(request.getEstimatedCost());
        activity.setOrderIndex(request.getOrderIndex());

        ItineraryActivity saved = activityRepository.save(activity);

        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<ItineraryActivityResponse> getActivities(
            String itineraryId
    ) {
        getOwnedItinerary(itineraryId);

        return activityRepository
                .findByItineraryIdOrderByActivityDateAscStartTimeAscOrderIndexAsc(itineraryId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ItineraryActivityResponse getActivity(
            String itineraryId,
            String activityId
    ) {
        getOwnedItinerary(itineraryId);

        ItineraryActivity activity = activityRepository
                .findByIdAndItineraryId(activityId, itineraryId)
                .orElseThrow(() ->
                        new RuntimeException("Activity not found"));

        return mapToResponse(activity);
    }

    @Transactional
    public ItineraryActivityResponse updateActivity(
            String itineraryId,
            String activityId,
            UpdateItineraryActivityRequest request
    ) {
        Itinerary itinerary = getOwnedItinerary(itineraryId);

        ItineraryActivity activity = activityRepository
                .findByIdAndItineraryId(activityId, itineraryId)
                .orElseThrow(() ->
                        new RuntimeException("Activity not found"));

        validateActivity(request.getActivityDate(),
                request.getStartTime(),
                request.getEndTime(),
                itinerary);

        activity.setActivityDate(request.getActivityDate());
        activity.setStartTime(request.getStartTime());
        activity.setEndTime(request.getEndTime());
        activity.setTitle(request.getTitle().trim());
        activity.setType(request.getType());
        activity.setLocation(request.getLocation());
        activity.setDescription(request.getDescription());
        activity.setEstimatedCost(request.getEstimatedCost());
        activity.setOrderIndex(request.getOrderIndex());

        ItineraryActivity updated = activityRepository.save(activity);

        return mapToResponse(updated);
    }

    @Transactional
    public void deleteActivity(
            String itineraryId,
            String activityId
    ) {
        getOwnedItinerary(itineraryId);

        ItineraryActivity activity = activityRepository
                .findByIdAndItineraryId(activityId, itineraryId)
                .orElseThrow(() ->
                        new RuntimeException("Activity not found"));

        activityRepository.delete(activity);
    }

    @Transactional
    public List<ItineraryActivityResponse> reorderActivities(
            String itineraryId,
            ReorderItineraryActivitiesRequest request
    ) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );

        Itinerary itinerary = itineraryRepository
                .findByIdAndUserId(itineraryId, currentUser.getId())
                .orElseThrow(() ->
                        new RuntimeException("Itinerary not found")
                );

        List<String> activityIds = request.getActivityIds();

        if (activityIds == null || activityIds.isEmpty()) {
            throw new RuntimeException("Activity list cannot be empty");
        }

        Set<String> uniqueActivityIds = new HashSet<>(activityIds);

        if (uniqueActivityIds.size() != activityIds.size()) {
            throw new RuntimeException(
                    "Duplicate activity IDs are not allowed"
            );
        }

        List<ItineraryActivity> activities = activityIds.stream()
                .map(activityId ->
                        activityRepository
                                .findByIdAndItineraryId(
                                        activityId,
                                        itinerary.getId()
                                )
                                .orElseThrow(() ->
                                        new RuntimeException(
                                                "Activity not found: " + activityId
                                        )
                                )
                )
                .toList();

        for (int index = 0; index < activities.size(); index++) {
            activities.get(index).setOrderIndex(index);
        }

        List<ItineraryActivity> savedActivities =
                activityRepository.saveAll(activities);

        return savedActivities.stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Itinerary getOwnedItinerary(String itineraryId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return itineraryRepository
                .findByIdAndUserId(itineraryId, user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Itinerary not found"));
    }

    private void validateActivity(
            java.time.LocalDate activityDate,
            java.time.LocalTime startTime,
            java.time.LocalTime endTime,
            Itinerary itinerary
    ) {

        if (activityDate.isBefore(itinerary.getStartDate())
                || activityDate.isAfter(itinerary.getEndDate())) {

            throw new IllegalArgumentException(
                    "Activity date must be within the itinerary dates"
            );
        }

        if (startTime != null
                && endTime != null
                && endTime.isBefore(startTime)) {

            throw new IllegalArgumentException(
                    "End time cannot be before start time"
            );
        }
    }

    private ItineraryActivityResponse mapToResponse(
            ItineraryActivity activity
    ) {

        return new ItineraryActivityResponse(
                activity.getId(),
                activity.getItinerary().getId(),
                activity.getActivityDate(),
                activity.getStartTime(),
                activity.getEndTime(),
                activity.getTitle(),
                activity.getType(),
                activity.getLocation(),
                activity.getDescription(),
                activity.getEstimatedCost(),
                activity.getOrderIndex(),
                activity.getCreatedAt(),
                activity.getUpdatedAt()
        );
    }
}