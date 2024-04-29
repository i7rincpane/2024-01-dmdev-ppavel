package ru.nvkz.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@UtilityClass
public class CollectionUtils {

    public static  String getMapValue(Map<Long, String> map , Long id) {
        return map.get(id);
    }

    public static <T> List<T> except(List<T> list1, List<T> list2, BiFunction<T, T, Boolean> excepter) {
        return list1.stream()
                .filter(value1 -> list2.stream()
                        .noneMatch((value2) -> excepter.apply(value1, value2))).toList();
    }

    public static <T> List<T> except(List<T> list1, List<T> list2) {
        return list1.stream()
                .filter(value1 -> list2.stream()
                        .noneMatch((value2) -> value1.equals(value2))).toList();
    }

}
