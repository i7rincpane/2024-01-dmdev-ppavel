package ru.nvkz.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.nvkz.entity.Product;
import ru.nvkz.filter.ProductFilter;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {

    private final EntityManager entityManager;

    //TODO: собрать sql по фильтру, после того как придумаю фильтр
    public List<Product> findAllDistinctByProductFilter(ProductFilter productFilter) {
        log.info("find all distinct by productfilter, filter {}", productFilter);

        return entityManager.createQuery("select p from Product p " +
                "join p.productProperties pp " +
                "join pp.property prop " +
                "join prop.propertyInfo pi " +
                "where (pi.name = 'Всего конфорок' and prop.integerValue=2) " +
                "OR (pi.name = 'Ширина' and prop.doubleValue between 20.0 and 27.0) " +
                "GROUP BY p.id " +
                "HAVING count (p.id) = 2 ", Product.class).getResultList();
    }
}
