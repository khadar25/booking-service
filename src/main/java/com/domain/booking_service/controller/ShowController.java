package com.domain.booking_service.controller;

import com.domain.booking_service.dto.ShowDTO;
import com.domain.booking_service.service.ShowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@RequestMapping("/api/shows")
@Tag(name = "Show API", description = "Endpoints for managing and browsing shows")
public class ShowController {
    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @Operation(summary = "Browse shows", description = "Browse available shows for a movie in a city on a given date, with pagination.")
    @ApiResponse(responseCode = "200", description = "Shows found")
    @GetMapping
    public ResponseEntity<Page<ShowDTO>> browseShows(
            @Parameter(description = "Movie name", required = false) @RequestParam(required = false) String movie,
            @Parameter(description = "City", required = false) @RequestParam(required = false) String city,
            @Parameter(description = "Date (yyyy-MM-dd)", required = false) @RequestParam(required = false) String date,
            @Parameter(description = "Page number (0-based)", required = false) @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size", required = false) @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ShowDTO> result = showService.findShowsWithFilters(movie, city, date, pageable);
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
