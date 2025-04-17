package com.inditex.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceDTO {

    @NotNull(message = "Product ID is mandatory.")
    private Long productId;

    @NotNull(message = "Brand ID is mandatory.")
    private Long brandId;

    @NotNull(message = "Price list is mandatory.")
    private Long priceList;

    @NotNull(message = "Start date is mandatory.")
    private LocalDateTime startDate;

    @NotNull(message = "End date is mandatory.")
    private LocalDateTime endDate;

    @NotNull(message = "Priority is mandatory.")
    private Integer priority;

    @NotNull(message = "Price is mandatory.")
    @Positive(message = "Price must be positive.")
    private Double price;

    @NotNull(message = "Currency is mandatory.")
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid 3-letter ISO currency code.")
    private String curr;
}
