package com.domain.booking_service.service;

import com.domain.booking_service.config.BookingConstants;
import com.domain.booking_service.dto.BookingDTO;
import com.domain.booking_service.mapper.BookingMapper;
import com.domain.booking_service.model.BookedSeat;
import com.domain.booking_service.model.Booking;
import com.domain.booking_service.model.Seat;
import com.domain.booking_service.model.Show;
import com.domain.booking_service.repository.BookedSeatRepository;
import com.domain.booking_service.repository.BookingRepository;
import com.domain.booking_service.repository.SeatRepository;
import com.domain.booking_service.repository.ShowRepository;
import com.domain.booking_service.exception.BookingServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final BookedSeatRepository bookedSeatRepository;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;

    public BookingService(BookingRepository bookingRepository, BookedSeatRepository bookedSeatRepository,
                         SeatRepository seatRepository, ShowRepository showRepository) {
        this.bookingRepository = bookingRepository;
        this.bookedSeatRepository = bookedSeatRepository;
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
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
    public BookingDTO bookTickets(String userName, Long showId, List<String> seatIds, double seatPrice) {
        logger.info("Booking seats for showId: {}, seatIds: {}, seatPrice: {}", showId, seatIds, seatPrice);

        // Fetch show details for discount calculation
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new BookingServiceException(BookingConstants.ERROR_SHOW_NOT_FOUND, HttpStatus.NOT_FOUND));

        // Check if seats are already booked
        List<BookedSeat> alreadyBooked = bookedSeatRepository.findByShowIdAndSeatNumberIn(showId, seatIds);

        if (!alreadyBooked.isEmpty()) {
            List<String> bookedSeats = alreadyBooked.stream()
                    .map(BookedSeat::getSeatNumber)
                    .collect(Collectors.toList());
            throw new BookingServiceException(BookingConstants.ERROR_SEATS_NOT_AVAILABLE + bookedSeats, HttpStatus.BAD_REQUEST);
        }

        List<Seat> selectedSeats = seatRepository.findAllById(seatIds);

        // Calculate discounts
        double totalPrice = calculatePrice(seatPrice, selectedSeats.size(), show.getShowTime());

        // Create booking
        Booking booking = new Booking();
        booking.setUserName(userName);
        booking.setShow(show);
        booking.setSeats(selectedSeats.stream().map(Seat::getSeatNumber).collect(Collectors.toList()));
        booking.setStatus(BookingConstants.BOOKING_STATUS_CONFIRMED);
        booking.setTotalPrice(totalPrice);

        seatRepository.saveAll(selectedSeats);
        logger.info("Seats booked successfully: {}", selectedSeats);

        Booking saved = bookingRepository.save(booking);
        logger.info("Booking created: {}", booking);

        return BookingMapper.toDTO(saved);
    }

    /**
     * Calculates the total price with applicable discounts
     *
     * @param seatPrice price per seat
     * @param numSeats number of seats being booked
     * @param showTime the show time
     * @return total price after discounts
     */
    private double calculatePrice(double seatPrice, int numSeats, LocalTime showTime) {
        double basePrice = seatPrice * numSeats;
        double discount = 0;

        // Third ticket discount: 50% off every third ticket
        if (numSeats >= BookingConstants.MINIMUM_TICKETS_FOR_THIRD_DISCOUNT) {
            int discountedSeats = numSeats / BookingConstants.MINIMUM_TICKETS_FOR_THIRD_DISCOUNT;
            double thirdTicketDiscount = discountedSeats * seatPrice * BookingConstants.THIRD_TICKET_DISCOUNT_PERCENTAGE;
            discount += thirdTicketDiscount;
            logger.info("Third ticket discount applied: {}", thirdTicketDiscount);
        }

        // Afternoon show discount: 20% off if show is after 12:00
        if (showTime != null && showTime.isAfter(BookingConstants.AFTERNOON_SHOW_START_TIME)) {
            double afternoonDiscount = basePrice * BookingConstants.AFTERNOON_SHOW_DISCOUNT_PERCENTAGE;
            discount += afternoonDiscount;
            logger.info("Afternoon show discount applied: {}", afternoonDiscount);
        }

        double totalPrice = basePrice - discount;
        logger.info("Price calculation - Base: {}, Discount: {}, Total: {}", basePrice, discount, totalPrice);

        return totalPrice;
    }
}
