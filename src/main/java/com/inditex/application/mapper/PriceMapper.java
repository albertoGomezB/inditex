package com.inditex.application.mapper;

import com.inditex.application.dto.PriceDTO;
import com.inditex.domain.model.Price;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceDTO toPriceDTO(Price price);
}