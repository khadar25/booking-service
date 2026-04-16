package com.domain.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TheatreDTO {

    private String theatreName;
    private List<ShowDTO> shows;
}
