package org.edufood.edufood.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartRequest {

    @NotNull(message = "ID блюда обязателен")
    private Long dishId;

    @NotNull(message = "Количество обязательно")
    @Min(value = 1, message = "Количество не может быть меньше 1")
    @Builder.Default
    private Integer quantity = 1;
}