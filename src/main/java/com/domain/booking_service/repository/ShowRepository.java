package com.domain.booking_service.repository;

import com.domain.booking_service.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {
    @Query("SELECT s FROM Show s " +
           "JOIN FETCH s.movie m " +
           "JOIN FETCH s.screen sc " +
           "JOIN FETCH sc.theatre t " +
           "WHERE (:movie IS NULL OR LOWER(m.name) = LOWER(:movie)) " +
           "AND (:city IS NULL OR LOWER(t.city) = LOWER(:city)) " +
           "AND (:showDate IS NULL OR s.showDate = :showDate)")
    List<Show> findByMovieAndCityAndShowDate(@Param("movie") String movie, @Param("city") String city, @Param("showDate") java.time.LocalDate showDate);
}
