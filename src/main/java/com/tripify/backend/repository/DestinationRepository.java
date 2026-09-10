package com.tripify.backend.repository;

import com.tripify.backend.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, String> {

    List<Destination> findByActiveTrueOrderByNameAsc();

    List<Destination> findByActiveTrueAndPopularTrueOrderByNameAsc();
}