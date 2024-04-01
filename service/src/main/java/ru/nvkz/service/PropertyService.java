package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nvkz.dto.PropertyReadDto;
import ru.nvkz.repository.PropertyRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PropertyService {

    private final PropertyRepository propertyRepository;

    public List<PropertyReadDto> findAllWithCountProductPropertyValue(Integer productTypeId) {
        return propertyRepository.findAllWithCountProductPropertyValue(productTypeId);
    }
}
