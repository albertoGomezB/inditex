package com.inditex.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PriceTest {

    @Test
    void shouldCreatePriceSuccessfully() {
        LocalDateTime startDate = LocalDateTime.parse("2020-06-14T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse("2020-06-14T23:59:59");
        Price price = new Price(1L, startDate, 35455L, endDate, 1L, 0, 35.50, "EUR");
        assertNotNull(price);
        assertEquals(1L, price.getPriceList());
        assertEquals(35455L, price.getProductId());
        assertEquals(1L, price.getBrandId());
        assertEquals(startDate, price.getStartDate());
        assertEquals(endDate, price.getEndDate());
        assertEquals(0, price.getPriority());
        assertEquals(35.50, price.getPrice());
        assertEquals("EUR", price.getCurr());
    }

    @Test
    void shouldCreateWrongPrice() {
        LocalDateTime startDate = LocalDateTime.parse("2020-06-14T00:00:00");
        LocalDateTime endDate = LocalDateTime.parse("2020-06-14T23:59:59");
        Price price = new Price(1L, startDate, 35455L, endDate, 1L, 0, 35.50, "EUR");

        assertNotNull(price);
        assertNotEquals(2L, price.getPriceList());
        assertNotEquals(35456L, price.getProductId());
        assertNotEquals(2L, price.getBrandId());
        assertNotEquals(startDate.plusDays(1), price.getStartDate());
        assertNotEquals(endDate.plusDays(1), price.getEndDate());
        assertNotEquals(1, price.getPriority());
        assertNotEquals(36.50, price.getPrice());
        assertNotEquals("USD", price.getCurr());
    }
}
