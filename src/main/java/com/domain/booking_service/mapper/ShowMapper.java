package com.domain.booking_service.mapper;

import com.domain.booking_service.dto.ShowDTO;
import com.domain.booking_service.model.Movie;
import com.domain.booking_service.model.Screen;
import com.domain.booking_service.model.Show;
import java.util.Optional;

public class ShowMapper {
    public static ShowDTO toDTO(Show show) {
        return Optional.ofNullable(show).map(s -> {
            ShowDTO dto = new ShowDTO();
            dto.setId(s.getId());
            Optional.ofNullable(s.getMovie()).ifPresent(movie -> {
                dto.setMovieId(movie.getId());
                dto.setMovieName(movie.getName());
                dto.setMovieLanguage(movie.getLanguage());
                dto.setMovieDuration(movie.getDuration());
            });
            Optional.ofNullable(s.getScreen()).ifPresent(screen -> {
                dto.setScreenId(screen.getId());
                dto.setScreenName(screen.getScreenName());
                dto.setScreenCapacity(screen.getCapacity());
            });
            dto.setTime(Optional.ofNullable(s.getShowTime()).map(Object::toString).orElse(null));
            return dto;
        }).orElse(null);
    }

    public static Show toEntity(ShowDTO dto, Movie movie, Screen screen) {
        return Optional.ofNullable(dto).map(d -> {
            Show show = new Show();
            show.setId(d.getId());
            show.setMovie(movie);
            show.setScreen(screen);
            Optional.ofNullable(d.getTime()).ifPresent(time -> show.setShowTime(java.time.LocalTime.parse(time)));
            return show;
        }).orElse(null);
    }
}
