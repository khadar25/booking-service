package com.domain.booking_service.repository;

import com.domain.booking_service.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}

