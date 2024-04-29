package ru.nvkz.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;

import java.math.BigDecimal;


@Value
@FieldNameConstants
public class BasketProductCreateEditDto {

    Long productId;
    Long basketId;
    Integer count;
    BigDecimal sum;
    Boolean isSelected;
}
