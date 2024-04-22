package ru.nvkz.repository;


import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.function.TriFunction;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.springframework.util.CollectionUtils.isEmpty;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CPredicate {

    private final List<Predicate> predicates = new ArrayList<>();

    public static CPredicate builder() {
        return new CPredicate();
    }

    public CPredicate add(Predicate predicate) {

        if (predicate != null) {
            predicates.add(predicate);
        }

        return this;
    }

    public CPredicate add(String param, Function<String, Predicate> function) {

        if (!StringUtils.isEmpty(param)) {
            predicates.add(function.apply(param));
        }

        return this;
    }


    public <T> CPredicate add(T param, Function<T, Predicate> function) {
        if (param != null) {
            predicates.add(function.apply(param));
        }

        return this;
    }

    public <T> CPredicate add(T param, Function<T, Predicate> function, boolean is) {
        return is ? this.add(param, function) : this;
    }

    public <T> CPredicate add(Predicate predicate, boolean is) {
        return is ? this.add(predicate) : this;
    }

    public <T> CPredicate add(Collection<T> objects, Function<Collection<T>, Predicate> function) {
        if (objects != null && !objects.isEmpty()) {
            predicates.add(function.apply(objects));
        }

        return this;
    }

    public <T> CPredicate add(T param1, T param2, BiFunction<T, T, Predicate> function) {
        if (param1 != null && param2 != null) {
            predicates.add(function.apply(param1, param2));
        }

        return this;
    }

    public <Y, T> CPredicate add(Map<Y, T> map1, Map<Y, T> map2, TriFunction<T, T, Y, Predicate> function) {
        if (isEmpty(map1) || isEmpty(map2)) {
            return this;
        }

        for (Map.Entry<Y, T> entry : map2.entrySet()) {
            T value1 = map1.get(entry.getKey());
            T value2 = entry.getValue();

            if (value1 != null || value2 != null) {
                predicates.add(function.apply(value1, value2, entry.getKey()));
            }
        }

        return this;
    }

    public <Y, T> CPredicate add(Map<Y, List<T>> map, BiFunction<List<T>, Y, Predicate> function) {
        if (isEmpty(map)) {
            return this;
        }

        for (Map.Entry<Y, List<T>> entry : map.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                predicates.add(function.apply(entry.getValue(), entry.getKey()));
            }
        }
        return this;
    }

    public Predicate[] build() {
        return predicates.toArray(Predicate[]::new);
    }
}