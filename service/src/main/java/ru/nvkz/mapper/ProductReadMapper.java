package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.ProducerReadDto;
import ru.nvkz.dto.ProductReadDto;
import ru.nvkz.dto.CategoryReadDto;
import ru.nvkz.entity.Category;
import ru.nvkz.entity.Producer;
import ru.nvkz.entity.Product;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductReadMapper implements Mapper<Product, ProductReadDto> {

    private final ProducerReadMapper producerReadMapper;
    private final CategoryReadMapper categoryReadMapper;

    @Override
    public ProductReadDto map(Product object) {
        return new ProductReadDto(
                object.getId(),
                object.getCode(),
                object.getName(),
                object.getModel(),
                getProducerDto(object.getProducer()),
                object.getPrice(),
                object.getCount(),
                getCategoryDto(object.getCategory())
        );
    }

    private ProducerReadDto getProducerDto(Producer producer) {
        return Optional.ofNullable(producer).map(producerReadMapper::map).orElse(null);
    }

    private CategoryReadDto getCategoryDto(Category category) {
        return Optional.ofNullable(category).map(categoryReadMapper::map).orElse(null);
    }
}