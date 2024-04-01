package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nvkz.dto.ProductTypePathElement;
import ru.nvkz.dto.ProductTypeReadDto;
import ru.nvkz.mapper.ProductTypeReadMapper;
import ru.nvkz.repository.ProductTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    public final ProductTypeReadMapper productTypeReadMapper;

    public List<ProductTypePathElement> findAllPathElementByParentId(Integer id) {
        return productTypeRepository.findAllPathElementByParentId(id);
    }

    public List<ProductTypeReadDto> findAllByParentId(Integer id) {
        return productTypeRepository.findAllByParentId(id).stream()
                .map(productTypeReadMapper::map)
                .toList();
    }

    public Optional<ProductTypeReadDto> findById(Integer id) {
        return productTypeRepository.findById(id)
                .map(productTypeReadMapper::map);
    }
}
