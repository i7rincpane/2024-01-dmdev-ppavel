package ru.nvkz.dao;

import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.nvkz.entity.Product;
import ru.nvkz.entity.ProductProperty;
import ru.nvkz.entity.ProductType;
import ru.nvkz.filter.ProductFilter;
import ru.nvkz.util.HibernateTestUtil;
import ru.nvkz.util.TestDataImporter;

import java.util.List;

class ProductDaoTestIT {

    private static SessionFactory sessionFactory;
    private Session session;
    private ProductDao productDao = ProductDao.getInstance();

    @BeforeAll
    static void initSessionFactory() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @BeforeEach
    void openSession() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        new TestDataImporter().importData(sessionFactory);
    }

    @AfterEach
    void closeSession() {
        session.getTransaction().rollback();
        session.close();
    }

    @AfterAll
    static void closeSessionFactory() {
        sessionFactory.close();
    }

    @DisplayName("Проверить работу с Querydsl и Criteria отыскав товары с нужными свойствами")
    @Test
    void checkFindedProductByProductFilter() {
        ProductType productTypeExpected = productDao.findAllProductTypeByProductTypeName(session, "Электрическая варочная поверхность").get(0);
        List<Long> productPropertyIdExpectedBatch = productDao.findAllDistinctPropertyByProductTypeId(session, 5)
                .stream()
                .filter(item -> item.getName().equals("Таймер конфорок"))
                .filter(item -> item.getValue().equals("независимый (только оповещение), с автоотключением"))
                .flatMap(productProperties -> productProperties.getProductProperties().stream())
                .map(ProductProperty::getId)
                .toList();
        ProductFilter productFilter = ProductFilter.builder()
                .productTypeId(productTypeExpected.getId())
                .productPropertyIdBatch(productPropertyIdExpectedBatch)
                .build();

        List<Product> actualProducts = productDao.findAll(session, productFilter);

        Assertions.assertThat(actualProducts).hasSize(2);
    }
}