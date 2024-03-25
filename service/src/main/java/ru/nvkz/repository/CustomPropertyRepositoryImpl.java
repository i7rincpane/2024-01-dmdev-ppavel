package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.nvkz.dto.PropertyReadDto;
import ru.nvkz.extractor.PropertyReadDtoExtractor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomPropertyRepositoryImpl implements CustomPropertyRepository {

    private final NamedParameterJdbcOperations jdbc;
    private final PropertyReadDtoExtractor propertyReadDtoExtractor;

    @Override
    public List<PropertyReadDto> findAllWithCountProductPropertyValue(Integer productTypeId) {
        Map<String, Object> params = new HashMap<>();
        params.put("productTypeId", productTypeId);
        String resultSql = """
                SELECT pi.id as property_info_id,
                       pi.unit as property_info_unit,
                       pi.name as property_info_name,
                       pi.dtype as property_info_dtype,
                       prop.string_value as property_string_value,
                       prop.integer_value as property_integer_value,
                       prop.double_value as property_double_value,
                       prop.date_value as property_date_value,
                       prop.is_value as property_is_value
                FROM property prop
                         left join property_info pi on pi.id = prop.property_info_id
                         left join product_property pp on prop.id = pp.property_id
                         left join product p on pp.product_id = p.id
                         left join product_type pt on p.product_type_id = pt.id
                         WHERE pt.id = :productTypeId
                                """;

        return jdbc.query(resultSql, params, propertyReadDtoExtractor::extractData);
    }
}
