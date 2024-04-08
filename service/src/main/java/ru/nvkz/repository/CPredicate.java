package ru.nvkz.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.function.TriFunction;
import org.thymeleaf.util.StringUtils;
import ru.nvkz.entity.ProductPropertyValue;
import ru.nvkz.entity.Property;
import ru.nvkz.entity.PropertyValue;
import ru.nvkz.entity.PropertyValue_;
import ru.nvkz.entity.Property_;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.Objects.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CPredicate {

    private final List<Predicate> predicates = new ArrayList<>();

    public static CPredicate builder() {
        return new CPredicate();
    }

    public CPredicate add(String param, Function<String, Predicate> function) {

        if (!StringUtils.isEmpty(param)) {
            predicates.add(function.apply(param));
        }

        return this;
    }

    public <T> CPredicate add(T param, Function<T, Predicate> function) {

        if (nonNull(param)) {
            predicates.add(function.apply(param));
        }

        return this;
    }

    public <T> CPredicate addIf(T param, Function<T, Predicate> function, boolean is) {
        return is ? this.add(param, function) : this;
    }

    public <T> CPredicate add(Collection<T> objects, Function<Collection<T>, Predicate> function) {

        if (objects != null && !objects.isEmpty()) {
            predicates.add(function.apply(objects));
        }

        return this;
    }

    public <T> CPredicate add(T param1, T param2, BiFunction<T, T, Predicate> function) {

        if (nonNull(param1) && nonNull(param2)) {
            predicates.add(function.apply(param1, param2));
        }

        return this;
    }

    public <Y, T> CPredicate add(Map<Y, T> map1, Map<Y, T> map2, TriFunction<T, T, Y, Predicate> function) {

        if (map1 != null && !map1.isEmpty()) {
            if (map2 != null && !map2.isEmpty()) {
                for (Map.Entry<Y, T> entry : map2.entrySet()) {
                    if (nonNull(entry.getKey())) {
                        T value1 = map1.get(entry.getKey());
                        T value2 = entry.getValue();

                        if (nonNull(value1) || nonNull(value2)) {
                            predicates.add(function.apply(value1, value2, entry.getKey()));
                        }

                    }
                }
            }
        }

        return this;
    }

    public <Y, T> CPredicate add(Map<Y, List<T>> map, BiFunction<List<T>, Y, Predicate> function) {

        if (map != null && !map.isEmpty()) {
            for (Map.Entry<Y, List<T>> entry : map.entrySet()) {
                if (Objects.nonNull(entry.getKey())) {
                    if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                        predicates.add(function.apply(entry.getValue(), entry.getKey()));
                    }
                }
            }
        }

        return this;
    }

    public Predicate[] build() {
        return predicates.toArray(Predicate[]::new);
    }
}