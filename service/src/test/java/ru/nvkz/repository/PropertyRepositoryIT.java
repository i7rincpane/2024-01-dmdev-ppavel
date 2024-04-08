package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.nvkz.IntegrationTestBase;
import ru.nvkz.dto.PropertyReadDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
class PropertyRepositoryIT extends IntegrationTestBase {

    private static final Integer ELECTRIC_HOB_TYPE_ID = 5;

    private final PropertyRepository repository;

    @Test
    void findAllDistinctPropertyByProductTypeId() {
        String[] expectedPropertyNames = {"Таймер конфорок", "Основной материал изготовления панели", "Всего конфорок", "Ширина", "Рамка"};

        List<PropertyReadDto> properties = repository.findAllWithCountProductPropertyValue(ELECTRIC_HOB_TYPE_ID);

        assertThat(properties).hasSize(5);
        List<String> propertyNameBatch = properties.stream()
                .map(PropertyReadDto::getName)
                .toList();
        assertThat(propertyNameBatch).contains(expectedPropertyNames);
    }
}
