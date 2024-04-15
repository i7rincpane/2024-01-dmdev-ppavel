package ru.nvkz.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private final EntityManager entityManager;

    public List<Product> findAllDistinctByProductFilter(ProductFilter productFilter, Long categoryId) {
        log.info("find all distinct by productfilter, filter {}", productFilter);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> product = cq.from(Product.class);
        Join<Product, Producer> producer = (Join<Product, Producer>) product.fetch(Product_.producer);
        Join<Product, Category> category = (Join<Product, Category>) product.fetch(Product_.category);
        Join<Category, Category> categoryParent = (Join<Category, Category>) category.fetch(Category_.parent);
        ListJoin<Product, ProductProperty> productProperties = product.join(Product_.productProperties);
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
                                        .addIf(value2, param -> cb.lessThan(productProperties.get(ProductProperty_.NUMBER_VALUE), param), Objects.isNull(value1))
                                        .addIf(value1, param -> cb.greaterThan(productProperties.get(ProductProperty_.NUMBER_VALUE), param), Objects.isNull(value2))
                                        .add(key, param -> cb.equal(property.get(Property_.ID), param))
                                        .build())
                )
                .add(productFilter.getPropertyIdFloatValueFrom(),
                        productFilter.getPropertyIdFloatValueBy(),
                        (value1, value2, key) ->
                                cb.and(CPredicate.builder()
                                        .add(value1, value2, (param1, param2) -> cb.between(productProperties.get(ProductProperty_.FLOAT_VALUE), param1, param2))
                                        .addIf(value2, param -> cb.lessThan(productProperties.get(ProductProperty_.FLOAT_VALUE), param), Objects.isNull(value1))
                                        .addIf(value1, param -> cb.greaterThan(productProperties.get(ProductProperty_.FLOAT_VALUE), param), Objects.isNull(value2))
                                        .add(key, param -> cb.equal(property.get(Property_.ID), param))
                                        .build())
                )
                .add(productFilter.getPropertyIdStringClassifierIds(),
                        (paramInMap, key) ->
                                cb.and(CPredicate.builder()
                                        .add(paramInMap, param -> stringClassifier.get(StringClassifier_.ID).in(paramInMap))
                                        .add(key, param -> cb.equal(property.get(Property_.ID), param))
                                        .build())
                ).build();

        cq.select(product)
                .where(cb.and(cb.and(productPredicates),
                        propertyPredicates.length != 0 ? cb.or(propertyPredicates) : cb.and()))
                .groupBy(product.get(Product_.ID),
                        category.get(Category_.ID),
                        categoryParent.get(Category_.ID),
                        producer.get(Producer_.ID))
                .having(propertyPredicates.length != 0 ? cb.equal(cb.count(product.get(Product_.id)), propertyPredicates.length) : cb.and());

        return entityManager.createQuery(cq).getResultList();
    }
}
