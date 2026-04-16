package com.domain.booking_service.service;

import com.domain.booking_service.dto.ShowDTO;
import com.domain.booking_service.dto.TheatreDTO;
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

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.domain.booking_service.util.BookingServiceUtil.parseLocalDate;
import static com.domain.booking_service.util.BookingServiceUtil.parseLocalTime;

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
    public Page<TheatreDTO> getTheatres(String movie, String city, String date,String time, Pageable pageable) {

        Map<String, List<ShowDTO>> theatreMap = new HashMap<>();
        List<Object[]> shows = showRepository.findByMovieAndCityAndShowDate(movie, city, parseLocalDate(date), parseLocalTime(time));

        for (Object[] row : shows) {

            Long showId = (Long) row[0];
            String theatreName = (String) row[1];
            LocalTime showTime = (LocalTime) row[2];
            ShowDTO showDTO =  ShowDTO.builder().id(showId).time(showTime.toString()).build();
            theatreMap.computeIfAbsent(theatreName, k -> new ArrayList<>()).add(showDTO);
        }

        List<TheatreDTO> result = new ArrayList<>();

        for (Map.Entry<String, List<ShowDTO>> entry : theatreMap.entrySet()) {
            result.add(new TheatreDTO(entry.getKey(), entry.getValue()));
        }
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), result.size());
        List<TheatreDTO> pageContent = start > end ? List.of() : result.subList(start, end);
        return new PageImpl<>(pageContent, pageable, result.size());
    }

    /**
     * This method will create the show.
     * @param showDTO
     * @return
     */
    public ShowDTO createShow(ShowDTO showDTO) {
        Movie movie = getMovie(showDTO.getMovieId());
        Screen screen = getScreen(showDTO.getScreenId());
        Show show = ShowMapper.toEntity(showDTO, movie, screen);
        Show created = showRepository.save(show);
        logger.info("Show created successfully: {}", created);
        return ShowMapper.toDTO(created);
    }

    /**
     * This Method  will update the show details based on the show id. If the show is not found for the given id, it will throw a BookingServiceException with a NOT_FOUND status.
     * @param id
     * @param showDTO
     * @return
     */
    public ShowDTO updateShow(Long id, ShowDTO showDTO) {
        showDTO.setId(id);
        if (!showRepository.existsById(id)) {
            logger.error("Show not found for id: {}", id);
            throw new BookingServiceException("Show not found", HttpStatus.NOT_FOUND);
        }
        Movie movie = getMovie(showDTO.getMovieId());
        Screen screen = getScreen(showDTO.getScreenId());
        Show show = ShowMapper.toEntity(showDTO, movie, screen);
        Show updated = showRepository.save(show);
        logger.info("Show updated successfully: {}", updated);
        return ShowMapper.toDTO(updated);
    }

    /**
     * This method will delete the show based on the show id. If the show is not found for the given id, it will throw a BookingServiceException with a NOT_FOUND status.
     * @param id
     */
    public void deleteShow(Long id) {
        logger.info("Deleting show with id: {}", id);
        if (!showRepository.existsById(id)) {
            logger.error("Show not found for id: {}", id);
            throw new BookingServiceException("Show not found", HttpStatus.NOT_FOUND);
        }
        showRepository.deleteById(id);
        logger.info("Show deleted successfully with id: {}", id);
    }

    /**
     * This method will fetch the movie details based on the movie id. If the movie is not found for the given id, it will throw a BookingServiceException with a NOT_FOUND status.
     * @param movieId
     * @return
     */
    private Movie getMovie(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new BookingServiceException("Movie not found", HttpStatus.NOT_FOUND));
    }

    /**
     * This method will fetch the screen details based on the screen id. If the screen is not found for the given id, it will throw a BookingServiceException with a NOT_FOUND status.
     * @param screenId
     * @return
     */
    private Screen getScreen(Long screenId) {
        return screenRepository.findById(screenId)
                .orElseThrow(() -> new BookingServiceException("Screen not found", HttpStatus.NOT_FOUND));
    }

}
