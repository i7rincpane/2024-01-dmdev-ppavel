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
public class ProductProperty implements BaseEntity<Long> {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    protected Product product;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    protected Property property;

    @Builder
    public ProductProperty(Long id, Product product, Property property) {
        this.id = id;
        this.setProduct(product);
        this.setProperty(property);
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