package ru.nvkz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "productProperties")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Property implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String value;
    private String unit;
    @Builder.Default
    private Integer count = 0;
    @Builder.Default
    @OneToMany(mappedBy = "property")
    private List<ProductProperty> productProperties = new ArrayList<>();
}
