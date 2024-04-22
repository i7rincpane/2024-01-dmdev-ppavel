package ru.nvkz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.util.ArrayList;
import java.util.List;

@Data
@ToString(exclude = {"productProperties"})
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Property implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Category category;
    private String unit;
    @Enumerated(EnumType.STRING)
    private TypeValue dtype;

    @Builder.Default
    @OneToMany(mappedBy = "property", fetch = FetchType.LAZY)
    private List<ProductProperty> productProperties = new ArrayList<>();

}
