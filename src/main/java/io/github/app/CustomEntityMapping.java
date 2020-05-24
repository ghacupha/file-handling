package io.github.app;

import com.google.common.collect.ImmutableList;

import java.util.List;

public interface CustomEntityMapping<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    default List<E> toEntity(List<D> dtoList) {
        return dtoList.stream().map(this::toEntity).collect(ImmutableList.toImmutableList());
    }

    default List <D> toDto(List<E> entityList) {
        return entityList.stream().map(this::toDto).collect(ImmutableList.toImmutableList());
    }
}
