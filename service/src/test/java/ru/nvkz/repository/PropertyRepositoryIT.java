package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.PropertyFilterReadDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class PropertyRepositoryIT extends IntegrationTestBase {

    private static final Long ELECTRIC_HOB_TYPE_ID = 5L;

    private final PropertyRepository repository;

    @Test
    void findAllDistinctPropertyByProductTypeId() {
        String[] expectedPropertyNames = {"testNumberField","Таймер конфорок", "Основной материал изготовления панели", "Всего конфорок", "Ширина", "Рамка"};

        List<PropertyFilterReadDto> properties = repository.findAllWithCountProductProperty(ELECTRIC_HOB_TYPE_ID);

        assertThat(properties).hasSize(6);
        List<String> propertyNameBatch = properties.stream()
                .map(PropertyFilterReadDto::getName)
                .toList();
        assertThat(propertyNameBatch).contains(expectedPropertyNames);
    }

    @Test
    @DisplayName("Создание всех типов свойств")
    void test() {

    }
}
