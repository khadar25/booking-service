package com.domain.booking_service.mapper;

import com.domain.booking_service.dto.ShowDTO;
import com.domain.booking_service.model.Movie;
import com.domain.booking_service.model.Screen;
import com.domain.booking_service.model.Show;

public class ShowMapper {
    public static ShowDTO toDTO(Show show) {
        if (show == null) return null;
        ShowDTO dto = new ShowDTO();
        dto.setId(show.getId());
        if (show.getMovie() != null) {
            dto.setMovieId(show.getMovie().getId());
            dto.setMovieName(show.getMovie().getName());
            dto.setMovieLanguage(show.getMovie().getLanguage());
            dto.setMovieDuration(show.getMovie().getDuration());
        }
        if (show.getScreen() != null) {
            dto.setScreenId(show.getScreen().getId());
            dto.setScreenName(show.getScreen().getName());
            dto.setScreenCapacity(show.getScreen().getCapacity());
        }
        dto.setTime(show.getTime() != null ? show.getTime().toString() : null);
        return dto;
    }

    public static Show toEntity(ShowDTO dto, Movie movie, Screen screen) {
        if (dto == null) return null;
        Show show = new Show();
        show.setId(dto.getId());
        show.setMovie(movie);
        show.setScreen(screen);
        if (dto.getTime() != null) {
            show.setTime(java.time.LocalTime.parse(dto.getTime()));
        }
        return show;
    }
}
