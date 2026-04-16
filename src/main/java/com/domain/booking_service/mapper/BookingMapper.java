package com.domain.booking_service.mapper;

import com.domain.booking_service.dto.BookingDTO;
import com.domain.booking_service.model.Booking;
import com.domain.booking_service.model.Seat;
import com.domain.booking_service.model.Show;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookingMapper {
    public static BookingDTO toDTO(Booking booking) {
        return Optional.ofNullable(booking).map(b -> {
            BookingDTO dto = new BookingDTO();
            dto.setId(b.getId());
            dto.setUserName(b.getUserName());
            dto.setShowId(Optional.ofNullable(b.getShow()).map(Show::getId).orElse(null));
            dto.setSeatIds(b.getSeats());
            dto.setStatus(b.getStatus());
            dto.setTotalPrice(b.getTotalPrice());
            return dto;
        }).orElse(null);
    }

    public static Booking toEntity(BookingDTO dto, Show show, List<Seat> seats) {
        return Optional.ofNullable(dto).map(d -> {
            Booking booking = new Booking();
            booking.setId(d.getId());
            booking.setUserName(d.getUserName());
            booking.setShow(show);
            booking.setSeats(seats.stream().map(Seat::getSeatNumber).collect(Collectors.toList()));
            booking.setStatus(d.getStatus());
            booking.setTotalPrice(d.getTotalPrice());
            return booking;
        }).orElse(null);
    }
}


