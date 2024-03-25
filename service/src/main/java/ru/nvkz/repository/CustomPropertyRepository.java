package ru.nvkz.repository;

import ru.nvkz.dto.PropertyReadDto;

import java.util.List;

public interface CustomPropertyRepository {

    List<PropertyReadDto> findAllWithCountProductPropertyValue(Integer productTypeId);

}
