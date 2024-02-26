package ru.nvkz.util;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.nvkz.entity.Product;
import ru.nvkz.entity.ProductProperty;
import ru.nvkz.entity.ProductType;
import ru.nvkz.entity.Property;

import java.math.BigDecimal;

public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        ProductType appliance = saveProductType(session, "Бытовая техника", null);
        ProductType builtInTechnology = saveProductType(session, "Встраиваемая техника", appliance);
        ProductType hobs = saveProductType(session, "Варочные панели", builtInTechnology);
        ProductType electricHobs = saveProductType(session, "Варочные панели электрические", hobs);
        ProductType electricHobType = saveProductType(session, "Электрическая варочная поверхность", electricHobs);
        ProductType inductionHobs = saveProductType(session, "Варочные панели индукционные", hobs);
        ProductType inductionHobType = saveProductType(session, "Индукционная варочная поверхность", inductionHobs);
        ProductType gasHobs = saveProductType(session, "Варочные панели газовые", hobs);
        ProductType gasHobType = saveProductType(session, "Газовая варочная поверхность", gasHobs);
        ProductType ovens = saveProductType(session, "Духовые шкафы", builtInTechnology);
        ProductType kitchenAppliance = saveProductType(session, "Техника для кухни", appliance);
        ProductType homeDecorAndTableware = saveProductType(session, "Дом, декор и посуда", null);
        ProductType crockeryAndKitchenItems = saveProductType(session, "Посуда и кухонные предметы", homeDecorAndTableware);
        ProductType fryingPansAndSaucepans = saveProductType(session, "Сковороды и сотейники", crockeryAndKitchenItems);
        ProductType panType = saveProductType(session, "Сковорода", fryingPansAndSaucepans);

        Product electricHob = saveProduct(session, 1292955, electricHobType, "DEXP", "4M2CTYL/B", "черный", 6499.0, 5);
        Product electricHob2 = saveProduct(session, 1332067, electricHobType, "DEXP", "EH-C2NSMA/B", "черный", 6999.0, 5);
        Product electricHob3 = saveProduct(session, 5005295, electricHobType, "DEXP", "1B4TODB", "черный", 10999.0, 2);
        saveProduct(session, 5059789, inductionHobType, "DEXP", "EH-I2MB/B", "черный", 7499.0, 5);
        saveProduct(session, 5059786, inductionHobType, "DEXP", "EH-I2SMA/B", "черный", 7499.0, 5);
        saveProduct(session, 4714690, gasHobType, "DEXP", "9M2GT ST", "серебристый", 5399.0, 1);
        Product panp1 = saveProduct(session, 1291416, panType, "Vari Litta", "L31122", "черный", 799.0, 10);
        Product panp2 = saveProduct(session, 1118856, panType, "Vari Litta", "L31128", "черный", 1350.0, 3);

        Property electricHob1Property1_2 = saveProperty(session, "Всего конфорок", "2", "шт");
        Property electricHob1Property1_4 = saveProperty(session, "Всего конфорок", "4", "шт");
        Property electricHob1Property2_56 = saveProperty(session, "Ширина", "56", "см");
        Property electricHob1Property2_265 = saveProperty(session, "Ширина", "26.5", "см");
        Property electricHob1Property2_268 = saveProperty(session, "Ширина", "26.8", "см");
        Property electricHobProperty3 = saveProperty(session, "Основной материал изготовления панели", "стеклокерамика", null);
        Property electricHobProperty5 = saveProperty(session, "Рамка", "нет", null);
        Property electricHobProperty7_auto = saveProperty(session, "Таймер конфорок", "с автоотключением", null);
        Property electricHobProperty7 = saveProperty(session, "Таймер конфорок", "независимый (только оповещение), с автоотключением", null);
        Property panDiameterProperty_22 = saveProperty(session, "Диаметр сковороды", "22", "см");
        Property panDiameterProperty_28 = saveProperty(session, "Диаметр сковороды", "28", "см");

        saveProductProperty(session, electricHob, electricHob1Property1_2);
        saveProductProperty(session, electricHob2, electricHob1Property1_2);
        saveProductProperty(session, electricHob3, electricHob1Property1_4);
        saveProductProperty(session, electricHob, electricHob1Property2_56);
        saveProductProperty(session, electricHob2, electricHob1Property2_265);
        saveProductProperty(session, electricHob3, electricHob1Property2_268);
        saveProductProperty(session, electricHob, electricHobProperty3);
        saveProductProperty(session, electricHob2, electricHobProperty3);
        saveProductProperty(session, electricHob3, electricHobProperty3);
        saveProductProperty(session, electricHob, electricHobProperty5);
        saveProductProperty(session, electricHob2, electricHobProperty5);
        saveProductProperty(session, electricHob3, electricHobProperty5);
        saveProductProperty(session, electricHob, electricHobProperty7_auto);
        saveProductProperty(session, electricHob2, electricHobProperty7);
        saveProductProperty(session, electricHob3, electricHobProperty7);
        saveProductProperty(session, panp1, panDiameterProperty_22);
        saveProductProperty(session, panp2, panDiameterProperty_28);

        session.getTransaction().commit();
    }

    private ProductProperty saveProductProperty(Session session, Product product, Property property) {
        property.setCount(property.getCount() + 1);
        session.save(property);
        ProductProperty productProperty = ProductProperty.builder()
                .product(product)
                .property(property)
                .build();
        session.save(productProperty);
        session.flush();
        return productProperty;
    }

    private Property saveProperty(Session session, String name, String value, String unit) {
        Property property = Property.builder()
                .name(name)
                .value(value)
                .unit(unit)
                .build();
        session.save(property);
        session.flush();
        return property;
    }

    private ProductType saveProductType(Session session, String name, ProductType parent) {
        ProductType productType = ProductType.builder()
                .parent(parent)
                .name(name)
                .build();
        session.save(productType);
        return productType;
    }

    private Product saveProduct(Session session,
                                Integer code,
                                ProductType productType,
                                String producer,
                                String model,
                                String color,
                                Double price,
                                Integer count) {
        Product product = Product.builder()
                .name(String.join(" ", productType.getName(), model, color))
                .code(code)
                .color(color)
                .price(new BigDecimal(price))
                .model(model)
                .producer(producer)
                .count(count)
                .productType(productType)
                .build();
        session.save(product);
        return product;
    }
}
