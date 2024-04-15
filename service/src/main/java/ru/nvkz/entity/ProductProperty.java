package ru.nvkz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ProductProperty implements BaseEntity<Long> {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Property property;
    @ManyToOne(fetch = FetchType.LAZY)
    private StringClassifier stringClassifier;
    private String textValue;
    private Integer numberValue;
    private Double floatValue;
    private Instant dateValue;
    private Boolean booleanValue;

    public void setProperty(Property property) {

        this.property = property;
        property.getProductProperties().add(this);
    }

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
}