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
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"subProductTypes", "parent"})
@Entity
public class Category implements BaseEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private Category parent;
    @Builder.Default
    @OneToMany(mappedBy = "parent")
    private List<Category> subCategories = new ArrayList<>();

    public Category(Integer id, String name, Category parent) {
        this.id = id;
        this.name = name;
        this.setParent(parent);
    }

    public void setParent(Category parent) {
        this.parent = parent;
        parent.getSubCategories().add(this);
    }
}
