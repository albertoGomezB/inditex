package com.inditex.infraestructure.adapter.out.persistence;

import com.inditex.domain.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    /**
     * Finds a list of prices by productId, brandId, startDate and endDate.
     */
    List<Price> findByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long productId, Long brandId, LocalDateTime startDate, LocalDateTime endDate);
}
