INSERT INTO producer (id, name)
VALUES (1, 'DEXP'),
       (2, 'DARINA'),
       (3, 'De Luxe'),
       (4, 'Vari Litta');
SELECT SETVAL('producer_id_seq', (SELECT MAX(id) FROM producer));

INSERT INTO category (id, parent_id, name)
VALUES (1, null, 'Бытовая техника'),
       (2, 1, 'Встраиваемая техника'),
       (3, 2, 'Варочные панели'),
       (4, 3, 'Варочные панели электрические'),
       (5, 4, 'Электрическая варочная поверхность'),
       (6, 3, 'Варочные панели индукционные'),
       (7, 6, 'Индукционная варочная поверхност'),
       (8, 3, 'Варочные панели газовые'),
       (9, 8, 'Газовая варочная поверхность'),
       (10, 2, 'Духовые шкафы'),
       (11, 1, 'Техника для кухни'),
       (12, null, 'Дом, декор и посуда'),
       (13, 12, 'Посуда и кухонные предметы'),
       (14, 13, 'Сковороды и сотейники'),
       (15, 14, 'Сковорода');
SELECT SETVAL('product_id_seq', (SELECT MAX(id) FROM category));

INSERT INTO users (email, password, name, patronimic, surname, telephone, role, birth_date)
VALUES ('test@mail.ru', '$2a$10$vreI7Z9jXgtZj1Gx.UJnAO6H52s3hLEUMLYBQEPMrrOHR1BtHyg/K', 'Иван', 'Иванович', 'Иванов', '89089566776', 'ADMIN', '1990-01-10');
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO product (id, code, category_id, producer_id, model, price, count)
VALUES (1, '1292955', 5, 1, '4M2CTYL/B', 6499.0, 5),
       (2, '1332067', 5, 1, 'EH-C2NSMA/B', 6999.0, 5),
       (3, '5005295', 5, 2, '1B4TODB', 5555.0, 2),
       (4, '5005292', 5, 1, '1B4TOD2B', 10000.0, 2),
       (5, '5059789', 7, 1, 'EH-I2MB/B', 7499.0, 5),
       (6, '5059786', 7, 1, 'EH-I2SMA/B', 7499.0, 5),
       (7, '4714690', 9, 3, '9M2GT ST', 3000.0, 1),
       (8, '1291416', 15, 4, 'L31122', 799.0, 10),
       (9, '1118856', 15, 4, 'L31122', 1350.0, 3);
SELECT SETVAL('product_id_seq', (SELECT MAX(id) FROM product));


INSERT INTO property(id, category_id, name, unit, dtype)
VALUES (1, 5, 'Всего конфорок', 'шт', 'NUMBER'),
       (2, 5, 'Ширина', 'см', 'FLOAT'),
       (3, 5, 'Основной материал изготовления панели', null, 'STRING_CLASSIFIER'),
       (4, 5, 'Рамка', null, 'STRING_CLASSIFIER'),
       (5, 5, 'Таймер конфорок', null, 'STRING_CLASSIFIER'),
       (6, 15, 'Диаметр сковороды', 'см', 'FLOAT');
SELECT SETVAL('property_id_seq', (SELECT MAX(id) FROM property));

INSERT INTO string_classifier(id, name, property_id)
VALUES (1, 'стеклокерамика', 3),
       (2, 'нет', 4),
       (3, 'с автоотключением', 5),
       (4, 'независимый (только оповещение), с автоотключением', 5);
SELECT SETVAL('string_classifier_id_seq', (SELECT MAX(id) FROM string_classifier));

INSERT INTO product_property(id, product_id, property_id, text_value, number_value,
                                   float_value, date_value,
                                   boolean_value, string_classifier_id)
VALUES (1, 1, 1, null, 2, null, null, null, null),
       (2, 2, 1, null, 2, null, null, null, null),
       (3, 3, 1, null, 4, null, null, null, null),
       (4, 1, 2, null, null, 56.0, null, null, null),
       (5, 2, 2, null, null, 26.5, null, null, null),
       (6, 3, 2, null, null, 26.8, null, null, null),
       (7, 1, 3, null, null, null, null, null, 1),
       (8, 2, 3, null, null, null, null, null, 1),
       (9, 3, 3, null, null, null, null, null, 1),
       (10, 1, 4, null, null, null, null, null, 2),
       (11, 2, 4, null, null, null, null, null, 2),
       (12, 3, 4, null, null, null, null, null, 2),
       (13, 1, 5, null, null, null, null, null, 3),
       (14, 2, 5, null, null, null, null, null, 4),
       (15, 3, 5, null, null, null, null, null, 4),
       (16, 8, 6, null, null, 22.0, null, null, null),
       (17, 9, 6, null, null, 28.0, null, null, null),
       (18, 4, 1, null, 2, null, null, null, null),
       (19, 4, 2, null, null, 26.5, null, null, null);
SELECT SETVAL('product_property_id_seq', (SELECT MAX(id) FROM product_property));
