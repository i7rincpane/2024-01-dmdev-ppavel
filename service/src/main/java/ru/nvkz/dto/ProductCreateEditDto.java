package ru.nvkz.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Value
public class ProductCreateEditDto {

    Integer code;
    String name;
    String model;
    Long producerId;
    BigDecimal price;
    Integer count;
    Long categoryId;

    List<ProductPropertyCreateEditDto> productProperties = new ArrayList<>();
}
