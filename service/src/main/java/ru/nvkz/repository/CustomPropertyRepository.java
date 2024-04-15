package ru.nvkz.repository;

import ru.nvkz.dto.PropertyFilterReadDto;

import java.util.List;

public interface CustomPropertyRepository {

    List<PropertyFilterReadDto> findAllWithCountProductProperty(Long categoryId);

}
