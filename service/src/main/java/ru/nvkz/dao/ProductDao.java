package ru.nvkz.dao;

import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import ru.nvkz.entity.Product;
import ru.nvkz.entity.ProductType;
import ru.nvkz.entity.Property;
import ru.nvkz.entity.QProductType;
import ru.nvkz.filter.ProductFilter;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDao {

    private static final ProductDao INSTANCE = new ProductDao();

    public List<ProductType> findAllProductTypeByProductTypeName(Session session, String productTypeName) {
        return new JPAQuery<ProductType>(session)
                .select(QProductType.productType)
                .from(QProductType.productType)
                .where(QProductType.productType.name.eq(productTypeName))
                .fetch();
    }

    public List<Product> findAll(Session session, ProductFilter productFilter) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Product.class);
        var product = criteria.from(Product.class);
        var productType = (Join<Object, Object>) product.fetch("productType");
        var productProperties = (Join<Object, Object>) product.fetch("productProperties");
        List<Predicate> predicates = new ArrayList<>();

        if (productFilter.getProductTypeId() != null) {
            predicates.add(cb.equal(productType.get("id"), productFilter.getProductTypeId()));
        }

        if (productFilter.getProductPropertyIdBatch() != null) {
            predicates.add(productProperties.in(productFilter.getProductPropertyIdBatch()));
        }

        criteria.select(product).where(predicates.toArray(Predicate[]::new));
        return session.createQuery(criteria).list();
    }

    public List<Property> findAllDistinctPropertyByProductTypeId(Session session, Integer productTypeId) {
        List<Property> result = session.createQuery("SELECT DISTINCT p2 FROM Property p2 " +
                        "JOIN FETCH  p2.productProperties pp " +
                        "JOIN  pp.product p " +
                        "JOIN  p.productType pt " +
                        "where pt.id = :productTypeId", Property.class)
                .setParameter("productTypeId", productTypeId)
                .getResultList();
        return result;
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
