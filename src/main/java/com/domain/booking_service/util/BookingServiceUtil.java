package com.domain.booking_service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Function;

public class BookingServiceUtil {
    private static final Logger logger = LoggerFactory.getLogger(BookingServiceUtil.class);

    /**
     * Parses a string into LocalTime. Returns null if parsing fails or input is null/blank.
     * @param time the time string to parse (format: HH:mm:ss)
     * @return parsed LocalTime or null if invalid
     */
    public static LocalTime parseLocalTime(String time) {
        return parseLocalDateTime(time, LocalTime::parse, "time");
    }

    /**
     * Parses a string into LocalDate. Returns null if parsing fails or input is null/blank.
     * @param date the date string to parse (format: yyyy-MM-dd)
     * @return parsed LocalDate or null if invalid
     */
    public static LocalDate parseLocalDate(String date) {
        return parseLocalDateTime(date, LocalDate::parse, "date");
    }

    /**
     * Generic parser for temporal types to reduce code duplication.
     * @param input the string to parse
     * @param parser the parser function
     * @param fieldName the name of the field (for logging)
     * @param <T> the temporal type
     * @return parsed value or null if parsing fails
     */
    private static <T> T parseLocalDateTime(String input, Function<String, T> parser, String fieldName) {
        return Optional.ofNullable(input)
                .filter(s -> !s.isBlank())
                .map(s -> {
                    try {
                        return parser.apply(s);
                    } catch (DateTimeParseException e) {
                        logger.warn("Invalid {} format: '{}'. Expected format: {}", fieldName, s,
                            fieldName.equals("time") ? "HH:mm:ss" : "yyyy-MM-dd");
                        return null;
                    }
                })
                .orElse(null);
    }
}
