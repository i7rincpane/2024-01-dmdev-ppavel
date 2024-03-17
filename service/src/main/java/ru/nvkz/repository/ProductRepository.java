package ru.nvkz.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.nvkz.entity.Product;
import ru.nvkz.entity.ProductProperty_;
import ru.nvkz.entity.ProductType_;
import ru.nvkz.entity.Product_;
import ru.nvkz.entity.Property_;
import ru.nvkz.filter.ProductFilter;

import java.util.List;

@Slf4j
@Repository
public class ProductRepository extends RepositoryBase<Long, Product> {

    public ProductRepository(EntityManager entityManager) {
        super(Product.class, entityManager);
    }

    public List<Product> findAllDistinctByProductFilter(ProductFilter productFilter) {
        log.info("find all distinct by productfilter, filter {}", productFilter);
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
        Root<Product> product = criteria.from(Product.class);
        var productType = (Join<Object, Object>) product.fetch(Product_.PRODUCT_TYPE);
        var productProperties = product.join(Product_.PRODUCT_PROPERTIES);
        var properties = productProperties.join(ProductProperty_.PROPERTY);
        Predicate[] predicates = CPredicate.builder()
                .add(productFilter.getProductTypeId(), (param) -> cb.equal(productType.get(ProductType_.ID), param))
                .add(productFilter.getPropertyIdBatch(), properties.get(Property_.ID)::in)
                .add(productFilter.getProducerNames(), product.get(Product_.PRODUCER)::in)
                .add(productFilter.getPriceFrom(), productFilter.getPriceBy(), (param1, param2) -> cb.between(product.get(Product_.PRICE), param1, param2))
                .build();
        criteria.select(product).where(predicates).distinct(true);
        return getEntityManager().createQuery(criteria).getResultList();
    }
}
