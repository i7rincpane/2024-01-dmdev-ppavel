package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.StringClassifierReadDto;
import ru.nvkz.entity.StringClassifier;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StringClassifierReadMapper implements Mapper<StringClassifier, StringClassifierReadDto> {

    private final PropertyReadMapper propertyReadMapper;

    @Override
    public StringClassifierReadDto map(StringClassifier object) {
        return new StringClassifierReadDto(object.getId(),
                object.getName(),
                Optional.ofNullable(object.getProperty()).map(propertyReadMapper::map).orElse(null));
    }
}
