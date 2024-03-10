package ru.nvkz.repository;

import org.springframework.stereotype.Repository;
import ru.nvkz.entity.Property;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class PropertyRepository extends RepositoryBase<Long, Property> {

    public PropertyRepository(EntityManager entityManager) {
        super(Property.class, entityManager);
    }

    public List<Property> findAllDistinctPropertyByProductTypeId(Integer productTypeId) {
        List<Property> result = getEntityManager().createQuery("SELECT DISTINCT p2 " +
                        "FROM Property p2 " +
                        "JOIN  p2.productProperties pp " +
                        "JOIN  pp.product p " +
                        "JOIN  p.productType pt " +
                        "where pt.id = :productTypeId", Property.class)
                .setParameter("productTypeId", productTypeId)
                .getResultList();
        return result;
    }
}
