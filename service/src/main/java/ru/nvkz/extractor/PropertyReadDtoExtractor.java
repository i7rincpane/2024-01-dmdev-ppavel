package ru.nvkz.extractor;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.nvkz.dto.PropertyReadDto;

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
            Long id = rs.getLong("property_info_id");
            resultMap.merge(id, this.createProperty(rs), (oldValue, newValue) -> {
                String value = newValue.getValues().keySet().stream().findFirst().get();
                Integer oldCount = oldValue.getValues().getOrDefault(value, 0);
                oldValue.getValues().put(value, oldCount+1);
                return oldValue;
            });
        }
        return new ArrayList<>(resultMap.values());
    }

    private PropertyReadDto createProperty(ResultSet rs) throws SQLException {

        String propertyInfoId = rs.getString("property_info_id");
        String propertyInfoUnit = rs.getString("property_info_unit");
        String propertyInfoName = rs.getString("property_info_name");
        String propertyInfoDtype = rs.getString("property_info_dtype");
        String propertyValue = getValue(rs.getString("property_string_value"),
                rs.getString("property_integer_value"),
                rs.getString("property_double_value"),
                rs.getString("property_date_value"),
                rs.getString("property_is_value"));
        Map<String, Integer> values = new HashMap<>();
        values.put(propertyValue, 1);
        return PropertyReadDto.builder()
                .id(propertyInfoId)
                .name(propertyInfoName)
                .unit(propertyInfoUnit)
                .dtype(propertyInfoDtype)
                .values(values)
                .build();
    }

    private String getValue(String... values) {
        return Arrays.stream(values).filter(Objects::nonNull).findFirst().get();
    }

}