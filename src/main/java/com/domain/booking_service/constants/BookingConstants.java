package com.domain.booking_service.constants;

import java.time.LocalTime;

/**
 * Constants for booking service business logic
 */
public final class BookingConstants {

    private BookingConstants() {
        // Private constructor to prevent instantiation
    }

    // Discount constants
    public static final double THIRD_TICKET_DISCOUNT_PERCENTAGE = 0.50; // 50% off on third ticket
    public static final double AFTERNOON_SHOW_DISCOUNT_PERCENTAGE = 0.20; // 20% off for afternoon shows
    public static final int MINIMUM_TICKETS_FOR_THIRD_DISCOUNT = 3;
    public static final LocalTime AFTERNOON_SHOW_START_TIME = LocalTime.of(12, 0);

    // Seat status constants
    public static final String SEAT_STATUS_AVAILABLE = "AVAILABLE";
    public static final String SEAT_STATUS_BOOKED = "BOOKED";

    // Booking status constants
    public static final String BOOKING_STATUS_CONFIRMED = "CONFIRMED";
    public static final String BOOKING_STATUS_CANCELLED = "CANCELLED";
    public static final String BOOKING_STATUS_PENDING = "PENDING";

    // Error messages
    public static final String ERROR_SHOW_NOT_FOUND = "Show not found";
    public static final String ERROR_MOVIE_NOT_FOUND = "Movie not found";
    public static final String ERROR_SCREEN_NOT_FOUND = "Screen not found";
    public static final String ERROR_SEATS_NOT_AVAILABLE = "Seats already booked: ";
}

