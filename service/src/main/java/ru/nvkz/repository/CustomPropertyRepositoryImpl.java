package ru.nvkz.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import ru.nvkz.dto.PropertyFilterReadDto;
import ru.nvkz.extractor.PropertyReadDtoExtractor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomPropertyRepositoryImpl implements CustomPropertyRepository {

    private final NamedParameterJdbcOperations jdbcOperations;
    private final PropertyReadDtoExtractor propertyReadDtoExtractor;

    @Override
    public List<PropertyFilterReadDto> findAllWithCountProductProperty(Long categoryId) {
        Map<String, Object> params = new HashMap<>();
        params.put("categoryId", categoryId);
        String resultSql = """                                       
                         
                SELECT prop.id           as property_id,
                                                                                                        prop.unit         as property_unit,
                                                                                                        prop.name         as property_name,
                                                                                                        prop.dtype        as property_dtype,
                                                                                                        sc.id             as string_classifier_id,
                                                                                                        sc.name           as string_classifier_name,
                                                                                                        pp.text_value    as text_value,
                                                                                                        pp.number_value  as number_value,
                                                                                                        pp.float_value   as float_value,
                                                                                                        pp.date_value    as date_value,
                                                                                                        pp.boolean_value as boolean_value
                                                                                                 FROM property prop
                                                                                                          left join product_property pp on pp.property_id = prop.id
                                                                                                          left join string_classifier sc on pp.string_classifier_id = sc.id
                                                                                                          join category c on c.id = prop.category_id
                                                                                                 WHERE c.id = :categoryId""";
        return jdbcOperations.query(resultSql, params, propertyReadDtoExtractor::extractData);
    }
}
