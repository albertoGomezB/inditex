package com.inditex.application.service;

import com.inditex.application.dto.PriceDTO;
import com.inditex.application.mapper.PriceMapper;
import com.inditex.domain.exception.PriceListEmptyException;
import com.inditex.domain.model.Price;
import com.inditex.infraestructure.adapter.out.persistence.PriceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceServiceImpl priceService;

    @Mock
    private PriceMapper priceMapper;


    public PriceServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnPriceWithHighestPriority() {
        // Given
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        Price priceLowPriority = Price.builder()
                .productId(productId)
                .brandId(brandId)
                .priceList(1L)
                .priority(0)
                .price(35.50)
                .curr("EUR")
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .build();

        Price priceHighPriority = Price.builder()
                .productId(productId)
                .brandId(brandId)
                .priceList(2L)
                .priority(1)
                .price(25.45)
                .curr("EUR")
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .build();

        PriceDTO dtoExpected = PriceDTO.builder()
                .productId(productId)
                .brandId(brandId)
                .priceList(2L)
                .priority(1)
                .price(25.45)
                .curr("EUR")
                .startDate(priceHighPriority.getStartDate())
                .endDate(priceHighPriority.getEndDate())
                .build();

        when(priceRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId, brandId, applicationDate, applicationDate))
                .thenReturn(List.of(priceLowPriority, priceHighPriority));

        when(priceMapper.toPriceDTO(priceHighPriority)).thenReturn(dtoExpected);

        // When
        PriceDTO result = priceService.getPrice(productId, brandId, applicationDate);

        // Then
        assertNotNull(result);
        assertEquals(2L, result.getPriceList());
        assertEquals(25.45, result.getPrice());
        assertEquals("EUR", result.getCurr());
    }

    @Test
    void shouldThrowExceptionWhenNoPriceFound() {
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime applicationDate = LocalDateTime.of(2021, 1, 1, 0, 0);

        when(priceRepository.findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                productId, brandId, applicationDate, applicationDate))
                .thenReturn(List.of());

        assertThrows(PriceListEmptyException.class,
                () -> priceService.getPrice(productId, brandId, applicationDate));
    }
}
