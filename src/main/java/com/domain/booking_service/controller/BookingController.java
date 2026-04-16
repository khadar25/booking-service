package com.domain.booking_service.controller;

import com.domain.booking_service.dto.BookingDTO;
import com.domain.booking_service.dto.BookingRequest;
import com.domain.booking_service.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Booking API", description = "Endpoints for booking movie tickets")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Book movie tickets", description = "Book tickets by selecting a show, seats, and price. Applies discounts as per business rules.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Booking successful"),
        @ApiResponse(responseCode = "400", description = "Invalid input or seat not available")
    })
    @PostMapping
    public ResponseEntity<BookingDTO> book(@Valid @RequestBody BookingRequest bookingRequest) {
        BookingDTO booking = bookingService.bookTickets(
            bookingRequest.getUserName(),
            bookingRequest.getShowId(),
            bookingRequest.getSeats(),
            bookingRequest.getSeatPrice()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }
}
