package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.PropertyCreateEditDto;
import ru.nvkz.entity.Category;
import ru.nvkz.entity.Property;
import ru.nvkz.repository.CategoryRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PropertyCreateEditMapper implements Mapper<PropertyCreateEditDto, Property> {

    private final CategoryRepository categoryRepository;

    @Override
    public Property map(PropertyCreateEditDto object) {
        Property property = new Property();
        copy(object, property);
        return property;
    }

    @Override
    public Property map(PropertyCreateEditDto fromObject, Property toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(PropertyCreateEditDto object, Property toObject) {
        toObject.setCategory(getCategory(object.getCategoryId()));
        toObject.setName(object.getName());
        toObject.setUnit(object.getUnit());
        toObject.setDtype(object.getDtype());
    }

    private Category getCategory(Long categoryId) {
        return Optional.ofNullable(categoryId)
                .flatMap(categoryRepository::findById)
                .orElse(null);
    }
}
