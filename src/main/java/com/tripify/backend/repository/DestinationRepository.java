package com.tripify.backend.repository;

import com.tripify.backend.entity.Destination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, String> {

    // Existing public API
    List<Destination> findByActiveTrueOrderByNameAsc();

    List<Destination> findByActiveTrueAndPopularTrueOrderByNameAsc();

    // Admin search + filtering + pagination
    @Query("""
    SELECT d
    FROM Destination d
    WHERE
        (
            :search IS NULL
            OR LOWER(d.name) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%'))
            OR LOWER(d.city) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%'))
            OR LOWER(d.country) LIKE LOWER(CONCAT('%', CAST(:search AS string), '%'))
        )
        AND (
            :country IS NULL
            OR LOWER(d.country) = LOWER(CAST(:country AS string))
        )
        AND (:active IS NULL OR d.active = :active)
        AND (:popular IS NULL OR d.popular = :popular)
    ORDER BY d.name ASC
    """)
    Page<Destination> searchAdminDestinations(
            @Param("search") String search,
            @Param("country") String country,
            @Param("active") Boolean active,
            @Param("popular") Boolean popular,
            Pageable pageable
    );
}