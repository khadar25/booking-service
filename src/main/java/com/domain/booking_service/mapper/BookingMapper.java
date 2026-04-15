package com.domain.booking_service.mapper;

import com.domain.booking_service.dto.BookingDTO;
import com.domain.booking_service.model.Booking;
import com.domain.booking_service.model.Seat;
import com.domain.booking_service.model.Show;
import java.util.List;
import java.util.stream.Collectors;

public class BookingMapper {
    public static BookingDTO toDTO(Booking booking) {
        if (booking == null) return null;
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setUserName(booking.getUserName());
        dto.setShowId(booking.getShow() != null ? booking.getShow().getId() : null);
        dto.setSeatIds(booking.getSeats() != null ? booking.getSeats().stream().map(Seat::getId).collect(Collectors.toList()) : null);
        dto.setStatus(booking.getStatus());
        dto.setTotalPrice(booking.getTotalPrice());
        return dto;
    }

    public static Booking toEntity(BookingDTO dto, Show show, List<Seat> seats) {
        if (dto == null) return null;
        Booking booking = new Booking();
        booking.setId(dto.getId());
        booking.setUserName(dto.getUserName());
        booking.setShow(show);
        booking.setSeats(seats);
        booking.setStatus(dto.getStatus());
        booking.setTotalPrice(dto.getTotalPrice());
        return booking;
    }
}
