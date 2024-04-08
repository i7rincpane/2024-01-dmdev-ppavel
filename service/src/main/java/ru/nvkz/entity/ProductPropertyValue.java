package ru.nvkz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@Entity
public class ProductPropertyValue implements BaseEntity<Long> {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private PropertyValue propertyValue;

    @Builder
    public ProductPropertyValue(Long id, Product product, PropertyValue propertyValue) {
        this.id = id;
        this.setProduct(product);
        this.setPropertyValue(propertyValue);
    }

    public void setProduct(Product product) {
        this.product = product;
        this.product.getProductPropertyValues().add(this);
    }

    public void setPropertyValue(PropertyValue propertyValue) {
        this.propertyValue = propertyValue;
        this.propertyValue.getProductPropertyValues().add(this);
    }
}