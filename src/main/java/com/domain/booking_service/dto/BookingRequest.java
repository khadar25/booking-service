package com.domain.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    @NotNull(message = "Show ID is required")
    private Long showId;

    @NotBlank(message = "User name cannot be blank")
    private String userName;

    @NotEmpty(message = "At least one seat must be selected")
    private List<String> seats;

    @DecimalMin(value = "0.0", inclusive = false, message = "Seat price must be greater than 0")
    private double seatPrice;
}



