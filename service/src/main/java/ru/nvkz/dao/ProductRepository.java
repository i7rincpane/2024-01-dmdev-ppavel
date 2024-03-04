package ru.nvkz.dao;

import ru.nvkz.entity.Product;
import ru.nvkz.entity.ProductProperty_;
import ru.nvkz.entity.Product_;
import ru.nvkz.filter.ProductFilter;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class ProductRepository extends RepositoryBase<Long, Product> {

    public ProductRepository(EntityManager entityManager) {
        super(Product.class, entityManager);
    }

    public List<Product> findAllDistinctByProductFilter(ProductFilter productFilter) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
        Root<Product> product = criteria.from(Product.class);
        var productType = (Join<Object, Object>) product.fetch(Product_.PRODUCT_TYPE);
        var productProperties = product.join(Product_.PRODUCT_PROPERTIES);
        var properties = productProperties.join(ProductProperty_.PROPERTY);
        // TODO: что то я тут намудрил.. HELP!
        Predicate[] predicates = CPredicate.builder()
                .add(productType, productFilter.getProductTypeId(), cb::equal)
                .add(productFilter.getPropertyIdBatch(), properties::in)
                .add(productFilter.getProducerNames(), product.get(Product_.PRODUCER)::in)
                .add(product.get(Product_.PRICE), productFilter.getPriceFrom(), productFilter.getPriceBy(), cb::between)
                .build();
        criteria.select(product).where(predicates).distinct(true);
        return getEntityManager().createQuery(criteria).getResultList();
    }
}
