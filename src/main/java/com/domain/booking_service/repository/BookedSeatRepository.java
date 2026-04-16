package com.domain.booking_service.repository;

import com.domain.booking_service.model.BookedSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookedSeatRepository extends JpaRepository<BookedSeat, Long> {

    List<BookedSeat> findByShowIdAndSeatNumberIn(Long showId, List<String> seatNumbers);
}
