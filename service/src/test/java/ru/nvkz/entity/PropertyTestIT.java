package ru.nvkz.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class PropertyTestIT extends BaseHibernateCrudTestIT {

    @Test
    @Override
    void create() {
        Property property = getProperty("test-name1");

        session.save(property);

        assertNotNull(property.getId());
    }

    @Test
    @Override
    void read() {
        Property property = getProperty("test-name1");
        Property propertyExpected = getProperty("test-name2");
        session.save(property);
        session.save(propertyExpected);
        session.flush();
        session.clear();

        Property propertyActual = session.get(Property.class, propertyExpected.getId());

        assertThat(propertyActual.getName()).isEqualTo(propertyExpected.getName());
    }

    @Test
    @Override
    void update() {
        Property propertyExpected = getProperty("testName");
        session.save(propertyExpected);
        session.flush();
        session.clear();
        propertyExpected.setName("updated");

        session.update(propertyExpected);
        session.flush();
        session.clear();

        Property propertyActual = session.get(Property.class, propertyExpected.getId());
        assertThat(propertyActual.getName()).isEqualTo(propertyExpected.getName());
    }

    @Test
    @Override
    void delete() {
        Property property = getProperty("test-name");
        session.save(property);

        session.delete(property);
        session.flush();

        Property propertyActual = session.get(Property.class, property.getId());
        assertNull(propertyActual);
    }

    private Property getProperty(String name) {
        return Property.builder()
                .name(name)
                .unit("unit")
                .build();
    }
}
