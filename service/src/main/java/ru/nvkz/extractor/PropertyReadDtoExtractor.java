package ru.nvkz.extractor;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.PropertyFilterReadDto;
import ru.nvkz.dto.StringClassifierReadDto;
import ru.nvkz.entity.TypeValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public class PropertyReadDtoExtractor implements ResultSetExtractor<List<PropertyFilterReadDto>> {

    @Override
    public List<PropertyFilterReadDto> extractData(ResultSet rs) throws SQLException {
        Map<Long, PropertyFilterReadDto> resultMap = new HashMap<>();
        while (rs.next()) {
            Long id = rs.getLong("property_id");
            resultMap.merge(id, this.createProperty(rs), (oldValue, newValue) -> {
                incrementValueCounts(oldValue.getStringClassifierValueCounts(), newValue.getStringClassifierValueCounts());
                incrementValueCounts(oldValue.getBooleanValueCounts(), newValue.getBooleanValueCounts());
                incrementValueCounts(oldValue.getFloatValueCounts(), newValue.getFloatValueCounts());
                incrementValueCounts(oldValue.getNumberValueCounts(), newValue.getNumberValueCounts());
                incrementValueCounts(oldValue.getDateValueCounts(), newValue.getDateValueCounts());
                incrementValueCounts(oldValue.getTextValueCounts(), newValue.getTextValueCounts());
                return oldValue;
            });
        }
        return new ArrayList<>(resultMap.values());
    }

    private static <T> void incrementValueCounts(Map<T, Integer> oldValues, Map<T, Integer> newValues) {
        newValues.keySet().stream().findFirst().ifPresent(
                (value) -> {
                    Integer oldCount = oldValues.getOrDefault(value, 0);
                    oldValues.put(value, oldCount + 1);
                }
        );

    }

    private PropertyFilterReadDto createProperty(ResultSet rs) throws SQLException {
        return PropertyFilterReadDto.builder()
                .id(rs.getLong("property_id"))
                .name(rs.getString("property_name"))
                .unit(rs.getString("property_unit"))
                .dtype(TypeValue.valueOf(rs.getString("property_dtype")))
                .textValueCounts(createNewValueCounts(rs.getString("text_value")))
                .numberValueCounts(createNewValueCounts(rs.getInt("number_value")))
                .floatValueCounts(createNewValueCounts(rs.getDouble("float_value")))
                .dateValueCounts(createNewValueCounts(rs.getTimestamp("date_value"), Timestamp::toInstant))
                .stringClassifierValueCounts(createNewValueCounts(rs.getLong("string_classifier_id"), rs.getString("string_classifier_name")))
                .booleanValueCounts(createNewValueCounts(rs.getBoolean("boolean_value")))
                .build();
    }

    private static <T, R> Map<T, Integer> createNewValueCounts(R object, Function<R, T> mapper) {
        Map<T, Integer> result = new HashMap<>();
        if (object != null) {
            result.put(mapper.apply(object), 1);
        }
        return result;
    }

    private static Map<StringClassifierReadDto, Integer> createNewValueCounts(Long object1, String object2) {
        Map<StringClassifierReadDto, Integer> result = new HashMap<>();
        if (object1 == 0 && object2 == null) {
            result.put(new StringClassifierReadDto(object1, "нет", null), 1);
        } else {
            result.put(new StringClassifierReadDto(object1, object2, null), 1);
        }
        return result;
    }

    private static <T> Map<T, Integer> createNewValueCounts(T object) {
        Map<T, Integer> result = new HashMap<>();
        if (object != null) {
            result.put(object, 1);
        }
        return result;
    }

}