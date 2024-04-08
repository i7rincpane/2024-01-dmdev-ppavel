package ru.nvkz.mapper;

import org.springframework.stereotype.Component;
import ru.nvkz.dto.CategoryReadDto;
import ru.nvkz.entity.Category;

import java.util.Optional;

@Component
public class CategoryReadMapper implements Mapper<Category, CategoryReadDto> {

    @Override
    public CategoryReadDto map(Category object) {
        return CategoryReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .parent(getParentDro(object.getParent()))
                .build();
    }

    private CategoryReadDto getParentDro(Category category) {
        return Optional.ofNullable(category).map(this::mapWithoutParent).orElse(null);
    }

    private CategoryReadDto mapWithoutParent(Category object) {
        return CategoryReadDto.builder()
                .id(object.getId())
                .name(object.getName())
                .build();
    }
}
