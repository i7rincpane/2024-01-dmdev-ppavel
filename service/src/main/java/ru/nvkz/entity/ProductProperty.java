package ru.nvkz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nvkz.listener.ProductPropertyListener;



@Data
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(ProductPropertyListener.class)
public class ProductProperty implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Property property;

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
