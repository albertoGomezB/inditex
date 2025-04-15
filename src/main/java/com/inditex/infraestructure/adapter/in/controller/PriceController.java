package com.inditex.infraestructure.adapter.in.controller;

import com.inditex.application.dto.PriceDTO;
import com.inditex.application.service.IPriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {

    private final IPriceService priceService;

    @Operation(summary = "Get price details for a product",
            description = "Search the price of a product based on productId, brandId, and application date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Price details retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PriceDTO.class))),
            @ApiResponse(responseCode = "204", description = "No content available", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Price not found", content = @Content)
    })
    @GetMapping
    public ResponseEntity<PriceDTO> getPrice(@RequestParam Long productId,
                                             @RequestParam Long brandId,
                                             @RequestParam String applicationDate) {

        PriceDTO priceDTO = priceService.getPrice(productId, brandId, LocalDateTime.parse(applicationDate));
        return ResponseEntity.ok(priceDTO);
    }

}
