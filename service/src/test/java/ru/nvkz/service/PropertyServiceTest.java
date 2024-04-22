package ru.nvkz.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.PropertyReadDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class PropertyServiceTest extends IntegrationTestBase {

    private final PropertyService propertyService;

    @Test
    void findMissingPropertiesByProductId() {
        List<String> result = propertyService.findMissingPropertiesByProductId(4L).stream().map(PropertyReadDto::getName).toList();
        result.forEach(System.out::println);
        assertThat(result).hasSize(3);
        assertThat(result).contains("Основной материал изготовления панели", "Рамка", "Таймер конфорок");
    }

    @Test
    void findByCategoryId() {
        List<String> result = propertyService.findByCategoryId(5L).stream().map(PropertyReadDto::getName).toList();
        result.forEach(System.out::println);
        assertThat(result).hasSize(5);
        assertThat(result).contains("Всего конфорок", "Основной материал изготовления панели", "Рамка", "Таймер конфорок", "Ширина");
    }
}