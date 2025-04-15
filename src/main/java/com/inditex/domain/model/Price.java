package com.inditex.domain.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "PRICES")
@IdClass(PriceId.class)
public class Price {

    @Id
    @Column(name = "BRAND_ID")
    private Long brandId;

    @Id
    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    @Id
    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    @Column(name = "PRICE_LIST")
    private Long priceList;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "CURR")
    private String curr;
}
