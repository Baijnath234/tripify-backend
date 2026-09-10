package com.tripify.backend.service;

import com.tripify.backend.dto.destination.CreateDestinationRequest;
import com.tripify.backend.dto.destination.DestinationResponse;
import com.tripify.backend.dto.destination.UpdateDestinationRequest;
import com.tripify.backend.entity.Destination;
import com.tripify.backend.repository.DestinationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.tripify.backend.dto.destination.DestinationPageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationService(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @Transactional(readOnly = true)
    public List<DestinationResponse> getAllDestinations() {
        return destinationRepository.findAll()
                .stream()
                .map(DestinationResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public DestinationResponse getDestinationById(String id) {
        Destination destination = findDestinationById(id);
        return new DestinationResponse(destination);
    }

    @Transactional
    public DestinationResponse createDestination(
            CreateDestinationRequest request) {

        Destination destination = new Destination();

        mapCreateRequestToEntity(request, destination);

        Destination savedDestination =
                destinationRepository.save(destination);

        return new DestinationResponse(savedDestination);
    }

    @Transactional
    public DestinationResponse updateDestination(
            String id,
            UpdateDestinationRequest request) {

        Destination destination = findDestinationById(id);

        mapUpdateRequestToEntity(request, destination);

        Destination updatedDestination =
                destinationRepository.save(destination);

        return new DestinationResponse(updatedDestination);
    }

    @Transactional
    public void deleteDestination(String id) {

        Destination destination = findDestinationById(id);

        destinationRepository.delete(destination);
    }

    @Transactional(readOnly = true)
    public List<DestinationResponse> getPublicDestinations() {
        return destinationRepository.findByActiveTrueOrderByNameAsc()
                .stream()
                .map(DestinationResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<DestinationResponse> getPopularDestinations() {
        return destinationRepository.findByActiveTrueAndPopularTrueOrderByNameAsc()
                .stream()
                .map(DestinationResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public DestinationResponse getPublicDestinationById(String id) {
        Destination destination = destinationRepository.findById(id)
                .orElseThrow(() ->
                        new DestinationNotFoundException(
                                "Destination not found with id: " + id
                        )
                );

        if (!destination.isActive()) {
            throw new DestinationNotFoundException(
                    "Destination not found with id: " + id
            );
        }

        return new DestinationResponse(destination);
    }

    @Transactional(readOnly = true)
    public DestinationPageResponse getAdminDestinations(
            String search,
            String country,
            Boolean active,
            Boolean popular,
            int page,
            int size) {

        if (page < 0) {
            page = 0;
        }

        if (size < 1) {
            size = 10;
        }

        if (size > 100) {
            size = 100;
        }

        search = normalizeFilter(search);
        country = normalizeFilter(country);

        Pageable pageable = PageRequest.of(page, size);

        Page<Destination> destinationPage =
                destinationRepository.searchAdminDestinations(
                        search,
                        country,
                        active,
                        popular,
                        pageable
                );

        return new DestinationPageResponse(
                destinationPage.getContent()
                        .stream()
                        .map(DestinationResponse::new)
                        .toList(),
                destinationPage.getNumber(),
                destinationPage.getSize(),
                destinationPage.getTotalElements(),
                destinationPage.getTotalPages()
        );
    }

    private String normalizeFilter(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        return value.trim();
    }

    private Destination findDestinationById(String id) {
        return destinationRepository.findById(id)
                .orElseThrow(() ->
                        new DestinationNotFoundException(
                                "Destination not found with id: " + id
                        )
                );
    }

    private void mapCreateRequestToEntity(
            CreateDestinationRequest request,
            Destination destination) {

        destination.setName(request.getName());
        destination.setCountry(request.getCountry());
        destination.setCity(request.getCity());
        destination.setDescription(request.getDescription());
        destination.setImageUrl(request.getImageUrl());
        destination.setBestTimeToVisit(request.getBestTimeToVisit());
        destination.setAverageBudget(request.getAverageBudget());
        destination.setCurrency(request.getCurrency());
        destination.setActive(request.isActive());
        destination.setPopular(request.isPopular());
    }

    private void mapUpdateRequestToEntity(
            UpdateDestinationRequest request,
            Destination destination) {

        destination.setName(request.getName());
        destination.setCountry(request.getCountry());
        destination.setCity(request.getCity());
        destination.setDescription(request.getDescription());
        destination.setImageUrl(request.getImageUrl());
        destination.setBestTimeToVisit(request.getBestTimeToVisit());
        destination.setAverageBudget(request.getAverageBudget());
        destination.setCurrency(request.getCurrency());
        destination.setActive(request.isActive());
        destination.setPopular(request.isPopular());
    }
}