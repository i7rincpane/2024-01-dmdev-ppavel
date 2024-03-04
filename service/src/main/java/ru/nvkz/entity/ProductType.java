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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "subProductTypes")
@Entity
public class ProductType implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductType parent;
    @Builder.Default
    @OneToMany(mappedBy = "parent")
    private List<ProductType> subProductTypes = new ArrayList<>();

    public ProductType(Integer id, String name, ProductType parent) {
        this.id = id;
        this.name = name;
        this.setParent(parent);
    }

    public void setParent(ProductType parent) {
        this.parent = parent;
        parent.getSubProductTypes().add(this);
    }
}
