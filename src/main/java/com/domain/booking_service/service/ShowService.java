package com.domain.booking_service.service;

import com.domain.booking_service.dto.ShowDTO;
import com.domain.booking_service.exception.BookingServiceException;
import com.domain.booking_service.mapper.ShowMapper;
import com.domain.booking_service.model.Movie;
import com.domain.booking_service.model.Screen;
import com.domain.booking_service.model.Show;
import com.domain.booking_service.repository.MovieRepository;
import com.domain.booking_service.repository.ScreenRepository;
import com.domain.booking_service.repository.ShowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowService {
    private static final Logger logger = LoggerFactory.getLogger(ShowService.class);
    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;

    public ShowService(ShowRepository showRepository, MovieRepository movieRepository, ScreenRepository screenRepository) {
        this.showRepository = showRepository;
        this.movieRepository = movieRepository;
        this.screenRepository = screenRepository;
    }

    /**
     * Finds shows with optional filters for movie name, city, and date, and paginates the result.
     *
     * @param movie   the movie name to filter by (optional)
     * @param city    the city to filter by (optional, uses Show.screen.theatre.city)
     * @param date    the date to filter by (optional, uses Show.showDate)
     * @param pageable pagination information
     * @return a page of ShowDTOs matching the filters
     */
    public Page<ShowDTO> findShowsWithFilters(String movie, String city, String date, Pageable pageable) {
        java.time.LocalDate showDate = null;
        if (date != null && !date.isEmpty()) {
            showDate = java.time.LocalDate.parse(date);
        }
        List<Show> shows = showRepository.findByMovieAndCityAndShowDate(
            (movie != null && !movie.isEmpty()) ? movie : null,
            (city != null && !city.isEmpty()) ? city : null,
            showDate
        );
        List<ShowDTO> filtered = shows.stream().map(ShowMapper::toDTO).toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filtered.size());
        List<ShowDTO> pageContent = start > end ? List.of() : filtered.subList(start, end);
        return new PageImpl<>(pageContent, pageable, filtered.size());
    }

    public ShowDTO createShow(ShowDTO showDTO) {
        Movie movie = movieRepository.findById(showDTO.getMovieId())
            .orElseThrow(() -> new BookingServiceException("Movie not found", HttpStatus.NOT_FOUND));
        Screen screen = screenRepository.findById(showDTO.getScreenId())
            .orElseThrow(() -> new BookingServiceException("Screen not found", HttpStatus.NOT_FOUND));
        Show show = ShowMapper.toEntity(showDTO, movie, screen);
        Show created = showRepository.save(show);
        logger.info("Show created successfully: {}", created);
        return ShowMapper.toDTO(created);
    }

    public ShowDTO updateShow(Long id, ShowDTO showDTO) {
        showDTO.setId(id);
        if (!showRepository.existsById(id)) {
            logger.error("Show not found for id: {}", id);
            throw new BookingServiceException("Show not found", HttpStatus.NOT_FOUND);
        }
        Movie movie = movieRepository.findById(showDTO.getMovieId())
            .orElseThrow(() -> new BookingServiceException("Movie not found", HttpStatus.NOT_FOUND));
        Screen screen = screenRepository.findById(showDTO.getScreenId())
            .orElseThrow(() -> new BookingServiceException("Screen not found", HttpStatus.NOT_FOUND));
        Show show = ShowMapper.toEntity(showDTO, movie, screen);
        Show updated = showRepository.save(show);
        logger.info("Show updated successfully: {}", updated);
        return ShowMapper.toDTO(updated);
    }

    public void deleteShow(Long id) {
        logger.info("Deleting show with id: {}", id);
        if (!showRepository.existsById(id)) {
            logger.error("Show not found for id: {}", id);
            throw new BookingServiceException("Show not found", HttpStatus.NOT_FOUND);
        }
        showRepository.deleteById(id);
        logger.info("Show deleted successfully with id: {}", id);
    }
}
