package ru.nvkz.listener;

import ru.nvkz.entity.ProductProperty;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;

public class ProductPropertyListener {

    @PostPersist
    public void postPersist(ProductProperty productProperty) {
        var property = productProperty.getProperty();
        property.setCount(property.getCount() + 1);
    }

    @PostRemove
    public void postRemove(ProductProperty productProperty) {
        var property = productProperty.getProperty();
        property.setCount(property.getCount() - 1);
    }
}
