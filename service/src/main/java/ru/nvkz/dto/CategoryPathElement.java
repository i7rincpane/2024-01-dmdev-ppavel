package ru.nvkz.dto;

public interface CategoryPathElement {

    Long getId();

    String getName();

    Long getParentId();

    Integer getLevel();
}
