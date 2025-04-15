package com.inditex.application.service;

import com.inditex.application.dto.PriceDTO;

import java.time.LocalDateTime;

public interface IPriceService {

    PriceDTO getPrice(Long productId, Long brandId, LocalDateTime applicationDate);
}
