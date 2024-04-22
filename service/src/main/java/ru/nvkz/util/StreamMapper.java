package ru.nvkz.util;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.stream.Stream;

@UtilityClass
public class StreamMapper {

    public static Double mapToMaxDoubleValue(Stream<Double> stream) {
        return stream.filter(Objects::nonNull).mapToDouble(Double::valueOf).max().orElse(0);
    }

    public static Double mapToMinDoubleValue(Stream<Double> stream) {
        return stream.filter(Objects::nonNull).mapToDouble(Double::valueOf).min().orElse(0);
    }

    public static Integer mapToMaxIntegerValue(Stream<Integer> stream) {
        return stream.filter(Objects::nonNull).mapToInt(Integer::valueOf).max().orElse(0);
    }

    public static Integer mapToMinIntegerValue(Stream<Integer> stream) {
        return stream.filter(Objects::nonNull).mapToInt(Integer::valueOf).min().orElse(0);
    }
}