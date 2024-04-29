package ru.nvkz.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    DRAFT("ЧЕРНОВИК"),
    PROCESSING("В ОБРАБОТКЕ"),
    PROCESSING_ERROR("ОШИБКА ОБРАБОТКИ"),
    WAITING("ОЖИДАЕТ"),
    CANCELED("ОТМЕНЕН"),
    COMPLETED("ПОЛУЧЕН");

    private String text;
}
