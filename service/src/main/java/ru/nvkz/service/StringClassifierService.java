package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nvkz.dto.StringClassifierCreateEditDto;
import ru.nvkz.dto.StringClassifierReadDto;
import ru.nvkz.mapper.StringClassifierCreateEditMapper;
import ru.nvkz.mapper.StringClassifierReadMapper;
import ru.nvkz.repository.StringClassifierRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StringClassifierService {

    private final StringClassifierRepository stringClassifierRepository;
    private final StringClassifierReadMapper stringClassifierReadMapper;
    private final StringClassifierCreateEditMapper stringClassifierCreateEditMapper;
    private final PropertyService propertyService;

    public List<StringClassifierReadDto> findByPropertyId(Long propertyId) {
        List<StringClassifierReadDto> result = new ArrayList<>();
        result.add(new StringClassifierReadDto(null, "нет", propertyService.findById(propertyId).orElseThrow()));
        result.addAll(stringClassifierRepository.findByPropertyId(propertyId).stream().map(stringClassifierReadMapper::map).toList());
        return result;
    }

    public Optional<StringClassifierReadDto> findById(Long id) {
        return stringClassifierRepository.findById(id)
                .map(stringClassifierReadMapper::map);
    }

    @Transactional
    public StringClassifierReadDto create(StringClassifierCreateEditDto stringClassifierDto) {
        return Optional.of(stringClassifierDto)
                .map(stringClassifierCreateEditMapper::map)
                .map(stringClassifierRepository::save)
                .map(stringClassifierReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<StringClassifierReadDto> update(Long id, StringClassifierCreateEditDto stringClassifierDto) {
        return stringClassifierRepository.findById(id)
                .map(entity -> stringClassifierCreateEditMapper.map(stringClassifierDto, entity))
                .map(stringClassifierRepository::saveAndFlush)
                .map(stringClassifierReadMapper::map);
    }

    @Transactional
    public boolean delete(Long id) {
        return stringClassifierRepository.findById(id)
                .map(entity -> {
                    stringClassifierRepository.delete(entity);
                    stringClassifierRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
