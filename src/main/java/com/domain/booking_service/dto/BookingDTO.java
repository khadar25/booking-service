package com.domain.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Long id;
    private String userName;
    private Long showId;
    private List<String> seatIds;
    private String status;
    private Double totalPrice;
}
