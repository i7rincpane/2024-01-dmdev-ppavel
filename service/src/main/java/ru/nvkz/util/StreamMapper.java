package ru.nvkz.util;

import lombok.experimental.UtilityClass;

import java.util.stream.Stream;

@UtilityClass
public class StreamMapper {

    public static final Double mapToMaxDoubleValue(Stream<WithValue> stream) {
        return stream.map(WithValue::getValue).mapToDouble(Double::parseDouble).max().orElseThrow();
    }

    public static final Double mapToMinDoubleValue(Stream<WithValue> stream) {
        return stream.map(WithValue::getValue).mapToDouble(Double::parseDouble).min().orElseThrow();
    }

    public static final Integer mapToMaxIntegerValue(Stream<WithValue> stream) {
        return stream.map(WithValue::getValue).mapToInt(Integer::parseInt).max().orElseThrow();
    }

    public static final Integer mapToMinIntegerValue(Stream<WithValue> stream) {
        return stream.map(WithValue::getValue).mapToInt(Integer::parseInt).min().orElseThrow();
    }
}