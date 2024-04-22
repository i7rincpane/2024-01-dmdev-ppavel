package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nvkz.dto.BasketCreateEditDto;
import ru.nvkz.dto.BasketReadDto;
import ru.nvkz.mapper.BasketCreateEditMapper;
import ru.nvkz.mapper.BasketReadMapper;
import ru.nvkz.repository.BasketRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasketService {

    private final BasketRepository basketRepository;
    private final BasketReadMapper basketReadMapper;
    private final BasketCreateEditMapper basketCreateEditMapper;

    public Optional<BasketReadDto> findById(Long id) {
        return basketRepository.findById(id)
                .map(basketReadMapper::map);
    }

    public Optional<BasketReadDto> findByUserId(Long id) {
        return basketRepository.findByUserId(id)
                .map(basketReadMapper::map);
    }

    @Transactional
    public BasketReadDto create(BasketCreateEditDto basketCreateEditDto) {
        return Optional.of(basketCreateEditDto)
                .map(basketCreateEditMapper::map)
                .map(basketRepository::save)
                .map(basketReadMapper::map)
                .orElseThrow();
    }
}
