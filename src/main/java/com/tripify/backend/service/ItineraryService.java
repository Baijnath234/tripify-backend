package com.tripify.backend.service;

import com.tripify.backend.dto.itinerary.CreateItineraryRequest;
import com.tripify.backend.dto.itinerary.ItineraryResponse;
import com.tripify.backend.dto.itinerary.UpdateItineraryRequest;
import com.tripify.backend.entity.Itinerary;
import com.tripify.backend.entity.ItineraryStatus;
import com.tripify.backend.entity.User;
import com.tripify.backend.repository.ItineraryRepository;
import com.tripify.backend.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final UserRepository userRepository;

    public ItineraryService(
            ItineraryRepository itineraryRepository,
            UserRepository userRepository
    ) {
        this.itineraryRepository = itineraryRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ItineraryResponse createItinerary(CreateItineraryRequest request) {

        User user = getAuthenticatedUser();

        validateDates(
                request.getStartDate(),
                request.getEndDate()
        );

        Itinerary itinerary = new Itinerary();

        itinerary.setUser(user);
        itinerary.setTitle(request.getTitle().trim());
        itinerary.setDestination(request.getDestination().trim());
        itinerary.setStartDate(request.getStartDate());
        itinerary.setEndDate(request.getEndDate());
        itinerary.setTravelers(request.getTravelers());
        itinerary.setBudget(request.getBudget());
        itinerary.setStatus(ItineraryStatus.DRAFT);

        Itinerary savedItinerary =
                itineraryRepository.save(itinerary);

        return new ItineraryResponse(savedItinerary);
    }

    @Transactional(readOnly = true)
    public List<ItineraryResponse> getMyItineraries() {

        User user = getAuthenticatedUser();

        return itineraryRepository
                .findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(ItineraryResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ItineraryResponse getMyItinerary(String id) {

        User user = getAuthenticatedUser();

        Itinerary itinerary =
                itineraryRepository
                        .findByIdAndUserId(id, user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Itinerary not found"
                                )
                        );

        return new ItineraryResponse(itinerary);
    }

    @Transactional
    public ItineraryResponse updateItinerary(
            String id,
            UpdateItineraryRequest request
    ) {

        User user = getAuthenticatedUser();

        validateDates(
                request.getStartDate(),
                request.getEndDate()
        );

        Itinerary itinerary =
                itineraryRepository
                        .findByIdAndUserId(id, user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Itinerary not found"
                                )
                        );

        itinerary.setTitle(request.getTitle().trim());
        itinerary.setDestination(request.getDestination().trim());
        itinerary.setStartDate(request.getStartDate());
        itinerary.setEndDate(request.getEndDate());
        itinerary.setTravelers(request.getTravelers());
        itinerary.setBudget(request.getBudget());

        Itinerary updatedItinerary =
                itineraryRepository.save(itinerary);

        return new ItineraryResponse(updatedItinerary);
    }

    @Transactional
    public void deleteItinerary(String id) {

        User user = getAuthenticatedUser();

        Itinerary itinerary =
                itineraryRepository
                        .findByIdAndUserId(id, user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Itinerary not found"
                                )
                        );

        itineraryRepository.delete(itinerary);
    }

    @Transactional
    public ItineraryResponse updateStatus(
            String id,
            ItineraryStatus status
    ) {

        User user = getAuthenticatedUser();

        Itinerary itinerary =
                itineraryRepository
                        .findByIdAndUserId(id, user.getId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Itinerary not found"
                                )
                        );

        itinerary.setStatus(status);

        Itinerary updatedItinerary =
                itineraryRepository.save(itinerary);

        return new ItineraryResponse(updatedItinerary);
    }

    private User getAuthenticatedUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new RuntimeException("User is not authenticated");
        }

        String email = authentication.getName();

        return userRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Authenticated user not found"
                        )
                );
    }

    private void validateDates(
            java.time.LocalDate startDate,
            java.time.LocalDate endDate
    ) {

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException(
                    "End date cannot be before start date"
            );
        }
    }
}