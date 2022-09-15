package com.library.LibraryApplication.repository;

import com.library.LibraryApplication.domain.Reservation;
import com.library.LibraryApplication.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Optional<Reservation> findByScancodeAndStatus(String scancode, Status status);
    @Query(value = "SELECT cust FROM reservation WHERE isbn = :isbn AND status = :status", nativeQuery = true)
    Optional<Integer> findFirstByIsbnAndStatus(@Param("isbn") String isbn, @Param("status") Status status);
}
