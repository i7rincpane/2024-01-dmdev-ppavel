package ru.nvkz.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@Builder
@Entity
public class ProductProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Property property;
    private String value;

    public ProductProperty(Long id, Product product, Property property, String value) {
        this.id = id;
        this.setProduct(product);
        this.setProperty(property);
        this.value = value;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.product.getProductProperties().add(this);
    }

    public void setProperty(Property property) {
        this.property = property;
        this.property.getProductProperties().add(this);
    }
}
