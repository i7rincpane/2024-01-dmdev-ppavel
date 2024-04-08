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
public class PropertyValue implements BaseEntity<Long> {

    @Id
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Property property;
    private String textValue;
    private Integer numberValue;
    private BigDecimal floatValue;
    private Instant dateValue;
    private Boolean booleanValue;
    @Builder.Default
    @OneToMany(mappedBy = "propertyValue")
    private List<ProductPropertyValue> productPropertyValues = new ArrayList<>();
}