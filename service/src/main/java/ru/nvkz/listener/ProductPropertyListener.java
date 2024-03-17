package ru.nvkz.listener;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import ru.nvkz.entity.ProductProperty;

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
