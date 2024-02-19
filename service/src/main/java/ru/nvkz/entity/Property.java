package ru.nvkz.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = "productProperties")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String unit;
    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "property")
    private List<ProductProperty> productProperties = new ArrayList<>();
}
