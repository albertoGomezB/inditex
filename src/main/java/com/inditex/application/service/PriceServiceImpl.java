package com.inditex.application.service;

import com.inditex.application.dto.PriceDTO;
import com.inditex.application.mapper.PriceMapper;
import com.inditex.domain.exception.PriceNotFoundException;
import com.inditex.domain.model.Price;
import com.inditex.infraestructure.adapter.out.persistence.PriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements IPriceService {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    @Override
    @Transactional(readOnly = true)
    public PriceDTO getPrice(Long productId, Long brandId, LocalDateTime applicationDate) {

        if (productId == null || brandId == null || applicationDate == null) {
            throw new IllegalArgumentException("Product ID, Brand ID, and application date must not be null");
        }

        // Obtain the valid prices in the date range
        List<Price> prices = priceRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId, brandId, applicationDate, applicationDate);

        if (prices.isEmpty()) {
            throw new PriceNotFoundException("The list of the price is empty and the price not found");
        }

        // Sort the prices by priority
        Price priotiryPrice = prices.stream()
                .max((p1, p2) -> p1.getPriority().compareTo(p2.getPriority()))
                .orElseThrow(() -> new PriceNotFoundException("Price not found with that priority"));

        // Map the price to the DTO
        return priceMapper.toPriceDTO(priotiryPrice);

    }
}
