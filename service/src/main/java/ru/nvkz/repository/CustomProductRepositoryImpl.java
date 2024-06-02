package ru.nvkz.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.AbstractQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import ru.nvkz.entity.Category;
import ru.nvkz.entity.Category_;
import ru.nvkz.entity.Producer;
import ru.nvkz.entity.Producer_;
import ru.nvkz.entity.Product;
import ru.nvkz.entity.ProductProperty;
import ru.nvkz.entity.ProductProperty_;
import ru.nvkz.entity.Product_;
import ru.nvkz.entity.Property;
import ru.nvkz.entity.Property_;
import ru.nvkz.entity.StringClassifier;
import ru.nvkz.entity.StringClassifier_;
import ru.nvkz.filter.ProductFilter;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    public static final Long MISSING_VALUE = 0L;
    private final EntityManager entityManager;

    public Page<Product> findAllDistinctByProductFilter(ProductFilter productFilter, Long categoryId, Pageable pageable) {
        log.info("find all distinct by productfilter, filter {}", productFilter);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        List<Product> content = getContent(productFilter, categoryId, cb, pageable);
        Long count = getCount(productFilter, categoryId, cb);

        return PageableExecutionUtils.getPage(content, pageable, () -> count);
    }

    private List<Product> getContent(ProductFilter productFilter, Long categoryId, CriteriaBuilder cb, Pageable pageable) {
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);
        extracted(productFilter, categoryId, root, cq.select(root), cb);
        TypedQuery<Product> query = entityManager.createQuery(cq);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        return query.getResultList();
    }

    private Long getCount(ProductFilter productFilter, Long categoryId, CriteriaBuilder cb) {
        CriteriaQuery<Long> cq2 = cb.createQuery(Long.class);
        Subquery<Long> csq = cq2.subquery(Long.class);
        Root<Product> subProduct = csq.from(Product.class);
        extracted(productFilter, categoryId, subProduct, csq.select(subProduct.get(Product_.ID)), cb);
        Root<Product> entityRoot = cq2.from(Product.class);
        cq2.select(cb.count(entityRoot)).where(entityRoot.get(Product_.ID).in(csq));
        return entityManager.createQuery(cq2).getSingleResult();
    }

    private <T> void extracted(ProductFilter productFilter, Long categoryId, Root<Product> product, AbstractQuery<T> select, CriteriaBuilder cb) {
        Join<Product, Producer> producer = (Join<Product, Producer>) product.fetch(Product_.producer);
        Join<Product, Category> category = (Join<Product, Category>) product.fetch(Product_.category);
        Join<Category, Category> categoryParent = (Join<Category, Category>) category.fetch(Category_.parent);
        ListJoin<Product, ProductProperty> productProperties = product.join(Product_.productProperties, JoinType.LEFT);
        Join<ProductProperty, Property> property = productProperties.join(ProductProperty_.property, JoinType.LEFT);
        Join<ProductProperty, StringClassifier> stringClassifier = productProperties.join(ProductProperty_.stringClassifier, JoinType.LEFT);

        Predicate[] productPredicates = CPredicate.builder()
                .add(categoryId, (param) -> cb.equal(category.get(Category_.ID), param))
                .add(productFilter.getPriceFrom(), productFilter.getPriceBy(), (param1, param2) -> cb.between(product.get(Product_.PRICE), param1, param2))
                .add(productFilter.getProduceIds(), producer.get(Producer_.ID)::in)
                .build();

        Predicate[] propertyPredicates = CPredicate.builder()
                .add(productFilter.getPropertyIdNumberValueFrom(),
                        productFilter.getPropertyIdNumberValueBy(),
                        (value1, value2, key) ->
                                cb.and(CPredicate.builder()
                                        .add(value1, value2, (param1, param2) -> cb.between(productProperties.get(ProductProperty_.NUMBER_VALUE), param1, param2))
                                        .add(value2, param -> cb.lessThan(productProperties.get(ProductProperty_.NUMBER_VALUE), param), Objects.isNull(value1))
                                        .add(value1, param -> cb.greaterThan(productProperties.get(ProductProperty_.NUMBER_VALUE), param), Objects.isNull(value2))
                                        .add(key, param -> cb.equal(property.get(Property_.ID), param))
                                        .build())
                )
                .add(productFilter.getPropertyIdFloatValueFrom(),
                        productFilter.getPropertyIdFloatValueBy(),
                        (value1, value2, key) ->
                                cb.and(CPredicate.builder()
                                        .add(value1, value2, (param1, param2) -> cb.between(productProperties.get(ProductProperty_.FLOAT_VALUE), param1, param2))
                                        .add(value2, param -> cb.lessThan(productProperties.get(ProductProperty_.FLOAT_VALUE), param), Objects.isNull(value1))
                                        .add(value1, param -> cb.greaterThan(productProperties.get(ProductProperty_.FLOAT_VALUE), param), Objects.isNull(value2))
                                        .add(key, param -> cb.equal(property.get(Property_.ID), param))
                                        .build())
                )
                .add(productFilter.getPropertyIdStringClassifierIds(),
                        (paramInMap, key) ->
                                cb.and(CPredicate.builder()
                                        .add(cb.or(CPredicate.builder()
                                                .add(paramInMap.stream().filter((value) -> value != MISSING_VALUE).toList(), param -> stringClassifier.get(StringClassifier_.ID).in(paramInMap))
                                                .add(cb.isNull(stringClassifier.get(StringClassifier_.ID)), paramInMap.stream().anyMatch((value) -> value.equals(MISSING_VALUE)))
                                                .build()))
                                        .add(key, param -> cb.equal(property.get(Property_.ID), param))
                                        .build())
                ).build();
        select
                .where(cb.and(cb.and(productPredicates),
                        propertyPredicates.length != 0 ? cb.or(propertyPredicates) : cb.and()))
                .groupBy(product.get(Product_.ID),
                        category.get(Category_.ID),
                        categoryParent.get(Category_.ID),
                        producer.get(Producer_.ID))
                .having(propertyPredicates.length != 0 ? cb.equal(cb.count(product.get(Product_.id)), propertyPredicates.length) : cb.and());
    }
}
