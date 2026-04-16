package com.domain.booking_service.repository;

import com.domain.booking_service.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {
    @Query("""
        SELECT s.id, t.name, s.time
        FROM Show s
        JOIN s.movie m
        JOIN s.screen sc
        JOIN sc.theatre t
        WHERE m.name = :movieName
        AND t.city = :city
        AND s.showDate = :date
        AND (:showTime IS NULL OR s.time = :showTime)
        """)
    List<Object[]> findByMovieAndCityAndShowDate(
        @Param("movieName") String movie,
        @Param("city") String city,
        @Param("date") java.time.LocalDate date,
        @Param("showTime") java.time.LocalTime showTime);
}


