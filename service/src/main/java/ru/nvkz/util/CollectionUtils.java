package ru.nvkz.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@UtilityClass
public class CollectionUtils {
    public static <T, Y> void merge(List<T> list1, List<Y> list2, BiFunction<Y, T, Boolean> merger, Function<Y, T> mapper) {
        list2.stream()
                .filter((value1) -> !list1.stream()
                        .anyMatch((value2) -> merger.apply(value1, value2)))
                .forEach(newValue -> list1.add(mapper.apply(newValue)));
    }
}
