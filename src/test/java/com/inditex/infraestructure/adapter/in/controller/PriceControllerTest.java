package com.inditex.infraestructure.adapter.in.controller;
import com.inditex.application.dto.PriceDTO;
import com.inditex.application.service.IPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class PriceControllerTest {

    @Mock
    private IPriceService priceService;

    @InjectMocks
    private PriceController priceController;

    private MockMvc mockMvc;

    private final Long productId = 35455L;
    private final Long brandId = 1L;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(priceController).build();
    }

    @ParameterizedTest
    @CsvSource({
            "2020-06-14T10:00:00, 35.50, EUR, 1",
            "2020-06-14T16:00:00, 35.50, EUR, 1",
            "2020-06-14T21:00:00, 35.50, EUR, 1",
            "2020-06-15T10:00:00, 35.50, EUR, 1",
            "2020-06-16T21:00:00, 35.50, EUR, 1"
    })
    void shouldReturnPriceDTOWhenValidProductBrandAndDate(String applicationDate, double expectedPrice, String expectedCurrency, long expectedPriceList) throws Exception {
        LocalDateTime parsedDate = LocalDateTime.parse(applicationDate);
        PriceDTO mockPriceDTO = createMockPriceDTO(expectedPrice, expectedCurrency, expectedPriceList);

        when(priceService.getPrice((productId), (brandId), (parsedDate))).thenReturn(mockPriceDTO);

        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString())
                        .param("applicationDate", applicationDate))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(expectedPrice))
                .andExpect(jsonPath("$.curr").value(expectedCurrency))
                .andExpect(jsonPath("$.priceList").value(expectedPriceList));
    }

    private PriceDTO createMockPriceDTO(double price, String currency, long priceList) {
        PriceDTO mockPriceDTO = new PriceDTO();
        mockPriceDTO.setProductId(productId);
        mockPriceDTO.setBrandId(brandId);
        mockPriceDTO.setStartDate(LocalDateTime.parse("2020-06-14T00:00:00"));
        mockPriceDTO.setEndDate(LocalDateTime.parse("2020-06-16T23:59:59"));
        mockPriceDTO.setPriceList(priceList);
        mockPriceDTO.setPriority(0);
        mockPriceDTO.setPrice(price);
        mockPriceDTO.setCurr(currency);
        return mockPriceDTO;
    }

    @Test
    void shouldReturnBadRequestWhenInvalidProductId() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("productId", "invalid")
                        .param("brandId", brandId.toString())
                        .param("applicationDate", "2020-06-14T10:00:00"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenMissingProductId() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("brandId", brandId.toString())
                        .param("applicationDate", "2020-06-14T10:00:00"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenMissingBrandId() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("applicationDate", "2020-06-14T10:00:00"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenMissingApplicationDate() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", brandId.toString()))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenInvalidBrandId() throws Exception {
        mockMvc.perform(get("/api/prices")
                        .param("productId", productId.toString())
                        .param("brandId", "invalid")
                        .param("applicationDate", "2020-06-14T10:00:00"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
