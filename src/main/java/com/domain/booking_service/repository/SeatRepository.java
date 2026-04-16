package com.domain.booking_service.repository;

import com.domain.booking_service.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllById(List<String> seatIds);
}

