package ru.nvkz.dao;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.assertj.core.util.TriFunction;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
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

    public <T> CPredicate add(Path path, T param, BiFunction<Path, T, Predicate> function) {
        if (param != null) {
            predicates.add(function.apply(path, param));
        }
        return this;
    }

    public <T> CPredicate add(Collection<T> objects, Function<Collection<T>, Predicate> function) {
        if (objects != null && !objects.isEmpty()) {
            predicates.add(function.apply(objects));
        }
        return this;
    }

    public <T> CPredicate add(Path path, T param1, T param2, TriFunction<Path, T, T, Predicate> function) {
        if (param1 != null && param2 != null) {
            predicates.add(function.apply(path, param1, param2));
        }
        return this;
    }

    public Predicate[] build() {
        return predicates.toArray(Predicate[]::new);
    }
}