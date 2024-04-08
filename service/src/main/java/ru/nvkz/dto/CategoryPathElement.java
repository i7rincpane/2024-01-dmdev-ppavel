package ru.nvkz.dto;

public interface CategoryPathElement {

    Integer getId();

    String getName();

    Integer getParentId();

    Integer getLevel();
}
