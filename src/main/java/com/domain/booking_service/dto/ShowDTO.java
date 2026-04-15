package com.domain.booking_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO {
    private Long id;
    private Long movieId;
    private String movieName;
    private String movieLanguage;
    private Integer movieDuration;
    private Long screenId;
    private String screenName;
    private Integer screenCapacity;
    private String time;
}
