package ru.nvkz.entity;

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
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Property implements BaseEntity<Long> {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private PropertyInfo propertyInfo;
    private String stringValue;
    private Integer integerValue;
    private BigDecimal doubleValue;
    private Instant dateValue;
    private Boolean isValue;
    @Builder.Default
    @OneToMany(mappedBy = "property")
    private List<ProductProperty> productProperties = new ArrayList<>();
}