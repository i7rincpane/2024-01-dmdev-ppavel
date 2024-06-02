package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.PropertyReadDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class PropertyServiceIT extends IntegrationTestBase {

    private final PropertyService propertyService;

    @Test
    void findMissingPropertiesByProductId() {
        List<String> result = propertyService.findMissingPropertiesByProductId(4L).stream().map(PropertyReadDto::getName).toList();
        result.forEach(System.out::println);
        assertThat(result).hasSize(4);
        assertThat(result).contains("testNumberField","Основной материал изготовления панели", "Рамка", "Таймер конфорок");
    }

    @Test
    void findByCategoryId() {
        List<String> result = propertyService.findByCategoryId(5L).stream().map(PropertyReadDto::getName).toList();
        result.forEach(System.out::println);
        assertThat(result).hasSize(6);
        assertThat(result).contains("testNumberField", "Всего конфорок", "Основной материал изготовления панели", "Рамка", "Таймер конфорок", "Ширина");
    }
}