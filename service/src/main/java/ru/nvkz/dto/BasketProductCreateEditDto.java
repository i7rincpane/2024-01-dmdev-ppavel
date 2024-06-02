package ru.nvkz.dto;

import lombok.Value;
import lombok.experimental.FieldNameConstants;


@Value
@FieldNameConstants
public class BasketProductCreateEditDto {

    Long productId;
    Long basketId;
    Integer count;
    Boolean isSelected;
}
