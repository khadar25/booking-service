package com.domain.booking_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/offers")
@Tag(name = "Offer API", description = "Endpoints for viewing available offers")
public class OfferController {
    @Operation(summary = "Get offers", description = "Get available offers for a city and optionally a theatre.")
    @ApiResponse(responseCode = "200", description = "Offers returned")
    @GetMapping
    public Map<String, String> getOffers(
            @Parameter(description = "City", required = true) @RequestParam String city,
            @Parameter(description = "Theatre (optional)") @RequestParam(required = false) String theatre) {
        Map<String, String> offers = new HashMap<>();
        offers.put("third_ticket", "50% discount on the third ticket");
        offers.put("afternoon_show", "20% discount for afternoon shows");
        return offers;
    }
}
