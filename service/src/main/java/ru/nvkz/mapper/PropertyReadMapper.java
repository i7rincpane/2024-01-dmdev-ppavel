package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.CategoryReadDto;
import ru.nvkz.dto.PropertyReadDto;
import ru.nvkz.entity.Property;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PropertyReadMapper implements Mapper<Property, PropertyReadDto> {

    private final CategoryReadMapper categoryReadMapper;

    @Override
    public PropertyReadDto map(Property object) {
        CategoryReadDto categoryReadDto = Optional.ofNullable(object.getCategory())
                .map(categoryReadMapper::map)
                .orElse(null);
        return PropertyReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .unit(object.getUnit())
                .category(categoryReadDto)
                .dtype(object.getDtype())
                .build();
    }
}
