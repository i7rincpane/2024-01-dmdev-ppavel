package ru.nvkz.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import ru.nvkz.entity.ProductType;
import ru.nvkz.entity.ProductType_;

import java.util.List;

@Repository
public class ProductTypeRepository extends RepositoryBase<Integer, ProductType> {

    public ProductTypeRepository(EntityManager entityManager) {
        super(ProductType.class, entityManager);
    }

    public List<ProductType> findAllByProductTypeName(String productTypeName) {
        var cb = getEntityManager().getCriteriaBuilder();
        var criteria = cb.createQuery(ProductType.class);
        var root = criteria.from(ProductType.class);
        criteria.select(root).where(cb.equal(root.get(ProductType_.NAME), productTypeName));
        return getEntityManager().createQuery(criteria).getResultList();
    }
}
