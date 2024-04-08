package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.nvkz.dto.PropertyReadDto;
import ru.nvkz.extractor.PropertyReadDtoExtractor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomPropertyRepositoryImpl implements CustomPropertyRepository {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final PropertyReadDtoExtractor propertyReadDtoExtractor;

    @Override
    public List<PropertyReadDto> findAllWithCountProductPropertyValue(Integer categoryId) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        String resultSql = """                           
                SELECT prop.id     as property_id,
                                   prop.unit   as property_unit,
                                   prop.name   as property_name,
                                   prop.dtype  as property_dtype,
                                   pv.id       as property_value_id,
                                   pv.text_value     as property_value_text,
                                   pv.number_value   as property_value_number,
                                   pv.float_value    as property_value_float,
                                   pv.date_value     as property_value_date,
                                   pv.boolean_value  as property_value_boolean
                            FROM property prop
                                      join property_value pv on pv.property_id = prop.id
                                      join product_property_value ppv on ppv.property_value_id = pv.id
                                      join category c on c.id = prop.category_id
                            WHERE c.id = :categoryId
                                                """;
        return jdbcOperations.query(resultSql, params, propertyReadDtoExtractor::extractData);
    }
}
