package ru.nvkz.repository;

import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CPredicate {

    private final List<Predicate> predicates = new ArrayList<>();

    public static CPredicate builder() {
        return new CPredicate();
    }

    public <T> CPredicate add(T param, Function<T, Predicate> function) {
        if (param != null) {
            predicates.add(function.apply(param));
        }
        return this;
    }

    public <T> CPredicate add(Collection<T> objects, Function<Collection<T>, Predicate> function) {
        if (objects != null && !objects.isEmpty()) {
            predicates.add(function.apply(objects));
        }
        return this;
    }

    public <T> CPredicate add( T param1, T param2, BiFunction<T, T, Predicate> function) {
        if (param1 != null && param2 != null) {
            predicates.add(function.apply(param1, param2));
        }
        return this;
    }

    public Predicate[] build() {
        return predicates.toArray(Predicate[]::new);
    }
}