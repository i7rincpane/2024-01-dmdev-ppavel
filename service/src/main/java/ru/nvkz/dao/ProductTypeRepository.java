package ru.nvkz.dao;

import ru.nvkz.entity.ProductType;
import ru.nvkz.entity.ProductType_;

import javax.persistence.EntityManager;
import java.util.List;

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
