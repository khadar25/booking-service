package com.domain.booking_service.service;

import com.domain.booking_service.dto.BookingDTO;
import com.domain.booking_service.mapper.BookingMapper;
import com.domain.booking_service.model.Booking;
import com.domain.booking_service.model.Seat;
import com.domain.booking_service.model.Show;
import com.domain.booking_service.repository.BookingRepository;
import com.domain.booking_service.repository.SeatRepository;
import com.domain.booking_service.exception.BookingServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final SeatRepository seatRepository;

    public BookingService(BookingRepository bookingRepository, SeatRepository seatRepository) {
        this.bookingRepository = bookingRepository;
        this.seatRepository = seatRepository;
    }

    /**
     * Books seats for a user for a given show and seat selection.
     * <p>
     * This method checks seat availability, locks seats, creates a booking, and returns the booking details as a DTO.
     * Throws BookingServiceException if any seat is not available.
     *
     * @param userName   the user name making the booking
     * @param showId     the show ID for which seats are being booked
     * @param seatIds    the list of seat IDs to book
     * @param seatPrice  the price per seat
     * @return BookingDTO containing booking details
     * @throws BookingServiceException if any seat is not available
     */
    @Transactional
    public BookingDTO bookSeats(String userName, Long showId, List<Long> seatIds, double seatPrice) {
        logger.info("Booking seats for userName: {}, showId: {}, seatIds: {}, seatPrice: {}", userName, showId, seatIds, seatPrice);
        List<Seat> selectedSeats = seatRepository.findAllById(seatIds);
        for (Seat seat : selectedSeats) {
            if (!"AVAILABLE".equals(seat.getStatus())) {
                logger.error("Seat {} is not available", seat.getId());
                throw new BookingServiceException("Seat " + seat.getId() + " is not available", HttpStatus.BAD_REQUEST);
            }
            seat.setStatus("BOOKED");
        }
        seatRepository.saveAll(selectedSeats);
        logger.info("Seats booked successfully: {}", selectedSeats);
        Booking booking = new Booking();
        booking.setUserName(userName);
        Show show = new Show();
        show.setId(showId);
        booking.setShow(show);
        booking.setSeats(selectedSeats);
        booking.setStatus("CONFIRMED");
        booking.setTotalPrice(seatPrice * selectedSeats.size());
        logger.info("Booking created: {}", booking);
        Booking saved = bookingRepository.save(booking);
        return BookingMapper.toDTO(saved);
    }
}
