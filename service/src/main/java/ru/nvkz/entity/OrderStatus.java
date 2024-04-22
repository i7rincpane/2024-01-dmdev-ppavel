package ru.nvkz.entity;

import lombok.Getter;

@Getter
public enum OrderStatus {

    DRAFT("ЧЕРНОВИК"),
    PROCESSING("В ОБРАБОТКЕ"),
    PROCESSING_ERROR("ОШИБКА ОБРАБОТКИ"),
    WAITING("ОЖИДАЕТ"),
    CANCELED("ОТМЕНЕН"),
    COMPLETED("ПОЛУЧЕН");

    private String text;

    OrderStatus(String text) {
        this.text = text;
    }

}
