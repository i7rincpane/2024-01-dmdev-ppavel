INSERT INTO producer (id, name)
VALUES (1, 'DEXP'),
       (2, 'DARINA'),
       (3, 'De Luxe'),
       (4, 'Vari Litta');

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
VALUES ('test@mail.ru', '123', 'Иван', 'Иванович', 'Иванов', '89089566776', 'ADMIN', '1990-01-10');

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


INSERT INTO property(id, category_id, name, unit, dtype)
VALUES (1, 5, 'Всего конфорок', 'шт', 'NUMBER'),
       (2, 5, 'Ширина', 'см', 'FLOAT'),
       (3, 5, 'Основной материал изготовления панели', null, 'TEXT'),
       (4, 5, 'Рамка', null, 'TEXT'),
       (5, 5, 'Таймер конфорок', null, 'TEXT'),
       (6, 15, 'Диаметр сковороды', 'см', 'FLOAT');

INSERT INTO property_value (id, property_id, text_value, number_value, float_value, date_value, boolean_value)
VALUES (1, 1, null, 2, null, null, null),
       (2, 1, null, 4, null, null, null),
       (3, 2, null, null, 56.0, null, null),
       (4, 2, null, null, 26.5, null, null),
       (5, 2, null, null, 26.8, null, null),
       (6, 3, 'стеклокерамика', null, null, null, null),
       (7, 4, 'нет', null, null, null, null),
       (8, 5, 'с автоотключением', null, null, null, null),
       (9, 5, 'независимый (только оповещение), с автоотключением', null, null, null, null),
       (10, 6, null, 22.0, null, null, null),
       (11, 6, null, 28.0, null, null, null);

INSERT INTO product_property_value(id, product_id, property_value_id)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 2),
       (4, 1, 3),
       (5, 2, 4),
       (6, 3, 5),
       (7, 1, 6),
       (8, 2, 6),
       (9, 3, 6),
       (10, 1, 7),
       (11, 2, 7),
       (12, 3, 7),
       (13, 1, 8),
       (14, 2, 9),
       (15, 3, 9),
       (16, 8, 10),
       (17, 9, 11),
       (18, 4, 1),
       (19, 4, 4);
