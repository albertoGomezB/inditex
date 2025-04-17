package com.inditex.application.service;

import com.inditex.domain.model.Price;
import com.inditex.infraestructure.adapter.out.persistence.PriceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PriceServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PriceRepository priceRepository;

    private final Long productId = 35455L;
    private final Long brandId = 1L;

    @Test
    void test1_14June_10AM() throws Exception {
        priceRepository.save(Price.builder()
                .brandId(brandId)
                .productId(productId)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(1L)
                .priority(0)
                .price(35.50)
                .curr("EUR")
                .build());

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", "2020-06-14T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void test2_14June_16PM() throws Exception {
        priceRepository.save(Price.builder()
                .brandId(brandId)
                .productId(productId)
                .startDate(LocalDateTime.parse("2020-06-14T15:00:00"))
                .endDate(LocalDateTime.parse("2020-06-14T18:30:00"))
                .priceList(2L)
                .priority(1)
                .price(25.45)
                .curr("EUR")
                .build());

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", "2020-06-14T16:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45));
    }

    @Test
    void test3_14June_21PM() throws Exception {
        priceRepository.save(Price.builder()
                .brandId(brandId)
                .productId(productId)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(1L)
                .priority(0)
                .price(35.50)
                .curr("EUR")
                .build());

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", "2020-06-14T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    void test4_15June_10AM() throws Exception {
        priceRepository.save(Price.builder()
                .brandId(brandId)
                .productId(productId)
                .startDate(LocalDateTime.parse("2020-06-15T00:00:00"))
                .endDate(LocalDateTime.parse("2020-06-15T11:00:00"))
                .priceList(3L)
                .priority(1)
                .price(30.50)
                .curr("EUR")
                .build());

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", "2020-06-15T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50));
    }

    @Test
    void test5_16June_21PM() throws Exception {
        priceRepository.save(Price.builder()
                .brandId(brandId)
                .productId(productId)
                .startDate(LocalDateTime.parse("2020-06-15T16:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .priceList(4L)
                .priority(1)
                .price(38.95)
                .curr("EUR")
                .build());

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", "2020-06-16T21:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95));
    }

    @Test
    void test6_NoPriceInDateRange() throws Exception {

        mockMvc.perform(get("/api/prices")
                        .param("productId", "35455")
                        .param("brandId", "1")
                        .param("applicationDate", "2020-01-01T10:00:00"))
                .andExpect(status().isNoContent());
    }

    @Test
    void test7_MissingParameters() throws Exception {
        mockMvc.perform(get("/api/prices")
                                .param("productId", "35455")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void test8_PrioritySelection() throws Exception {
        priceRepository.save(Price.builder()
                .brandId(brandId)
                .productId(productId)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-06-14T23:59:59"))
                .priceList(1L)
                .priority(1)
                .price(20.00)
                .curr("EUR")
                .build());

        priceRepository.save(Price.builder()
                .brandId(brandId)
                .productId(productId)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-06-14T23:59:59"))
                .priceList(2L)
                .priority(0)
                .price(25.00)
                .curr("EUR")
                .build());

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", "2020-06-14T10:00:00"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.00));
    }
    @Test
    void testMissingProductId() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("brandId", "1")
                        .param("applicationDate", "2020-06-14T10:00:00"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMissingBrandId() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("productId", "35455")
                        .param("applicationDate", "2020-06-14T10:00:00"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testMissingApplicationDate() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest());
    }



}
