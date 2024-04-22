package ru.nvkz.mapper;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.function.Function;

public interface Mapper<F, T> {

    T map(F object);

    default T map(F fromObject, T toObject) {
        return toObject;
    }

    default <V, E> V getField(E object, Function<E, V> function, V orElse) {
        return Optional.ofNullable(object).map(function::apply).orElse(orElse);
    }

    default <V, E> V getField(E object, Function<E, V> function) {
        return getField(object, function, null);
    }

    default <E, D> D getDto(E object, Mapper<E, D> maper) {
        return Optional.ofNullable(object).map(maper::map).orElse(null);
    }

    default <R extends JpaRepository<E, I>, E, I> E getEntity(I id, R repository) {
        return Optional.ofNullable(id).flatMap(repository::findById).orElse(null);
    }

}