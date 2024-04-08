package ru.nvkz.extractor;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.PropertyReadDto;
import ru.nvkz.dto.PropertyValueReadDto;
import ru.nvkz.entity.TypeValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class PropertyReadDtoExtractor implements ResultSetExtractor<List<PropertyReadDto>> {

    @Override
    public List<PropertyReadDto> extractData(ResultSet rs) throws SQLException {
        Map<Long, PropertyReadDto> resultMap = new HashMap<>();
        while (rs.next()) {
            Long id = rs.getLong("property_id");
            resultMap.merge(id, this.createProperty(rs), (oldValue, newValue) -> {
                PropertyValueReadDto value = newValue.getPropertyValueProductCounts().keySet().stream().findFirst().get();
                Integer oldCount = oldValue.getPropertyValueProductCounts().getOrDefault(value, 0);
                oldValue.getPropertyValueProductCounts().put(value, oldCount + 1);
                return oldValue;
            });
        }
        return new ArrayList<>(resultMap.values());
    }

    private PropertyReadDto createProperty(ResultSet rs) throws SQLException {
        Integer propertyInfoId = rs.getInt("property_id");
        String propertyInfoUnit = rs.getString("property_unit");
        String propertyInfoName = rs.getString("property_name");
        String propertyInfoDtype = rs.getString("property_dtype");
        Long propertyValueId = rs.getLong("property_value_id");
        String propertyValue = getValue(rs.getString("property_value_text"),
                rs.getString("property_value_number"),
                rs.getString("property_value_float"),
                rs.getString("property_value_date"),
                rs.getString("property_value_boolean"));
        Map<PropertyValueReadDto, Integer> propertyValueWithCountProductMap = new HashMap<>();
        propertyValueWithCountProductMap.put(new PropertyValueReadDto(propertyValueId, propertyValue), 1);
        return PropertyReadDto.builder()
                .id(propertyInfoId)
                .name(propertyInfoName)
                .unit(propertyInfoUnit)
                .dtype(TypeValue.valueOf(propertyInfoDtype))
                .propertyValueProductCounts(propertyValueWithCountProductMap)
                .build();
    }

    private String getValue(String... values) {
        return Arrays.stream(values).filter(Objects::nonNull).findFirst().get();
    }

}