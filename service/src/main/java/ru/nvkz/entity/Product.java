package ru.nvkz.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "productProperties")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Product implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private Integer code;
    private String name;
    private String model;
    private String producer;
    private BigDecimal price;
    private Integer count;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ProductType productType;
    @Builder.Default
    @OneToMany(mappedBy = "product")
    private List<ProductProperty> productProperties = new ArrayList<>();
}
