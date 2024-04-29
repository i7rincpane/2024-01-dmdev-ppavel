package ru.nvkz.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CollectionUtilsTest {

    @Test
    void except() {
        List<Integer> values = List.of(1, 2);
        List<Integer> values1 = List.of(1);
        List<Integer> expectedValues = List.of(2);

        List<Integer> actualValues = CollectionUtils.except(values, values1);

        assertThat(actualValues).hasSize(1);
        assertThat(actualValues.get(0)).isEqualTo(expectedValues.get(0));
    }
}