package org.edufood.edufood.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private Long dishId;
    private String dishName;
    private Long restaurantId;
    private String restaurantName;
    private BigDecimal price;
    private Integer quantity;
    private String imageUrl;
}