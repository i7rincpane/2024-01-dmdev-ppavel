package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.StringClassifierCreateEditDto;
import ru.nvkz.entity.Property;
import ru.nvkz.entity.StringClassifier;
import ru.nvkz.repository.PropertyRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StringClassifierCreateEditMapper implements Mapper<StringClassifierCreateEditDto, StringClassifier> {

    private final PropertyRepository propertyRepository;

    @Override
    public StringClassifier map(StringClassifierCreateEditDto object) {
        StringClassifier stringClassifier = new StringClassifier();
        copy(object, stringClassifier);
        return stringClassifier;
    }

    @Override
    public StringClassifier map(StringClassifierCreateEditDto fromObject, StringClassifier toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(StringClassifierCreateEditDto objectDto, StringClassifier toObject) {
        toObject.setProperty(getProperty(objectDto.getPropertyId()));
        toObject.setName(objectDto.getName());
    }

    private Property getProperty(Long propertyId) {
        return Optional.ofNullable(propertyId).flatMap(propertyRepository::findById).orElse(null);
    }
}
