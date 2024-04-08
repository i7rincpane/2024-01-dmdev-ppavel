package ru.nvkz.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.ProducerReadDto;
import ru.nvkz.entity.Producer;

@Component
@RequiredArgsConstructor
public class ProducerReadMapper implements Mapper<Producer, ProducerReadDto> {

    @Override
    public ProducerReadDto map(Producer object) {
        return new ProducerReadDto(object.getId(), object.getName());
    }
}