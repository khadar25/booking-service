package com.domain.booking_service.controller;

import com.domain.booking_service.dto.ShowDTO;
import com.domain.booking_service.dto.TheatreDTO;
import com.domain.booking_service.service.ShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@RestController
@RequestMapping("/api/shows")
@Tag(name = "Show API", description = "Endpoints for managing and browsing shows")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @Operation(summary = "Browse shows", description = "Browse available shows for a movie in a city on a given date, with pagination.")
    @ApiResponse(responseCode = "200", description = "Shows found")
    @GetMapping
    public ResponseEntity<Page<TheatreDTO>> browseTheatres(
            @Parameter(description = "Movie name") @RequestParam String movie,
            @Parameter(description = "City") @RequestParam String city,
            @Parameter(description = "Date (yyyy-MM-dd)") @RequestParam(required = false) String date,
            @Parameter(description = "Show time (HH:mm:ss)") @RequestParam(required = false) String showTime,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TheatreDTO> result = showService.getTheatres(movie, city, date, showTime, pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<ShowDTO> createShow(@RequestBody ShowDTO showDTO) {
        ShowDTO created = showService.createShow(showDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShowDTO> updateShow(@PathVariable Long id, @RequestBody ShowDTO showDTO) {
        ShowDTO updated = showService.updateShow(id, showDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShow(@PathVariable Long id) {
        showService.deleteShow(id);
        return ResponseEntity.noContent().build();
    }
}
