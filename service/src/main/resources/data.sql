INSERT INTO categories (code, parent, name)
VALUES (1, 0, 'Бытовая техника'),
       (2, 0, 'Смартфоны и фототехника'),
       (3, 0, 'Тв, консоли и аудио'),
       (4, 1, 'Встраиваемая техника'),
       (5, 1, 'Техника для кухни'),
       (6, 1, 'Техника для Дома'),
       (7, 5, 'Плиты и печи'),
       (8, 5, 'Холодильное оборудование'),
       (9, 5, 'Посудомоечные машины'),
       (10, 7, 'Плиты электрические'),
       (11, 7, 'Плиты газовые'),
       (12, 3, 'Телевизоры и аксессуары'),
       (13, 3, 'Телевизоры'),
       (14, 3, 'Холодильники');

INSERT INTO users (email, password, name, patronimic, surname, telephone)
VALUES ('test@mail.ru', '123', 'Иван', 'Иванович', 'Иванов', '89089566776');

INSERT INTO product (categories_id, color, model, name, price, producer)
VALUES ((SELECT id FROM categories WHERE name = 'Плиты электрические'), 'белый', '15М',
        'Электрическая плита Мечта 15М белый', 11199, 'Мечта'),
       ((SELECT id FROM categories WHERE name = 'Холодильники'), 'белый', 'RF-SD050RMA/W',
        'Холодильник компактный DEXP RF-SD050RMA/W белый', 7399, 'DEXP'),
       ((SELECT id FROM categories WHERE name = 'Телевизоры'), 'черный', '24HEN1',
        '24" (61 см) Телевизор LED Aceline 24HEN1 черный', 7399, 'Aceline');


INSERT INTO property(id, name, unit)
VALUES (1, 'Материал покрытия панели', null),
       (2, 'Общее количество конфорок', 'шт'),
       (3, 'Индукционные конфорки', null),
       (4, 'Мин. температура холодильной камеры', '°C'),
       (5, 'Диагональ экрана', 'см');

INSERT INTO product_property(product_id, property_id, value)
VALUES ((SELECT id FROM product WHERE name = 'Электрическая плита Мечта 15М белый'), 1, 'эмалированная сталь'),
       ((SELECT id FROM product WHERE name = 'Электрическая плита Мечта 15М белый'), 2, '2'),
       ((SELECT id FROM product WHERE name = 'Электрическая плита Мечта 15М белый'), 3, 'нет'),
       ((SELECT id FROM product WHERE name = 'Холодильник компактный DEXP RF-SD050RMA/W белый'), 2, '2'),
       ((SELECT id FROM product WHERE name = '24" (61 см) Телевизор LED Aceline 24HEN1 черный'), 2, '61');

INSERT INTO storage(house, name, street, town)
VALUES ('26/1', 'DNS в ТРЦ «Парус»', 'Архитекторов', 'Новокузнецк'),
       ('14А', 'DNS на «Бардина»', 'Бардина проспект', 'Новокузнецк'),
       ('19А', 'DNS в ТРЦ «Полёт»', 'Шахтеров', 'Новокузнецк');

INSERT INTO product_store(product_id, storage_id, count)
VALUES ((SELECT id FROM product WHERE name = 'Электрическая плита Мечта 15М белый'),
        (SELECT id FROM storage WHERE name = 'DNS в ТРЦ «Парус»'), 2),
       ((SELECT id FROM product WHERE name = 'Электрическая плита Мечта 15М белый'),
        (SELECT id FROM storage WHERE name = 'DNS на «Бардина»'), 1),
       ((SELECT id FROM product WHERE name = 'Электрическая плита Мечта 15М белый'),
        (SELECT id FROM storage WHERE name = 'DNS в ТРЦ «Полёт»'), 4),
       ((SELECT id FROM product WHERE name = 'Холодильник компактный DEXP RF-SD050RMA/W белый'),
        (SELECT id FROM storage WHERE name = 'DNS на «Бардина»'), 2),
       ((SELECT id FROM product WHERE name = '24" (61 см) Телевизор LED Aceline 24HEN1 черный'),
        (SELECT id FROM storage WHERE name = 'DNS в ТРЦ «Полёт»'), 3);

INSERT INTO orders(date_create, date_update, order_status, sum, user_id, storage_id)
VALUES ('2024-02-01 10:27:22', '2024-02-01 10:37:22', 'COMPLETED', 1, 1, 1);

INSERT INTO product_orders(count, orders_id, product_id)
VALUES (1, 1, 1),
       (1, 1, 2);