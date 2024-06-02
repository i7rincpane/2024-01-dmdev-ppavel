package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import ru.nvkz.dto.CategoryPathElement;
import ru.nvkz.dto.CategoryReadDto;
import ru.nvkz.entity.Category;
import ru.nvkz.mapper.CategoryReadMapper;
import ru.nvkz.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private static final Long PARENT_ROOT_ID = null;

    private final CategoryRepository categoryRepository;

    private final CategoryReadMapper categoryReadMapper;

    private final ImageService imageService;


    public List<CategoryPathElement> findAllPathElementByParentId(Long id) {
        return categoryRepository.findAllPathElementByParentId(id);
    }

    public List<CategoryPathElement> findAllPathElementByParentRoot() {
        return categoryRepository.findAllPathElementByParentId(PARENT_ROOT_ID);
    }


    public List<CategoryReadDto> findAllByParentRoot() {

        return categoryRepository.findAllByParentId(PARENT_ROOT_ID).stream()
                .map(categoryReadMapper::map)
                .toList();
    }

    public List<CategoryReadDto> findAllByParentId(Long id) {
        return categoryRepository.findAllByParentId(id).stream()
                .map(categoryReadMapper::map)
                .toList();
    }

    public Optional<CategoryReadDto> findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryReadMapper::map);
    }

    public Optional<byte[]> findImage(Long id) {
        return categoryRepository.findById(id)
                .map(Category::getImage)
                .filter(StringUtils::hasText)
                .flatMap(imageService::get);
    }
}
