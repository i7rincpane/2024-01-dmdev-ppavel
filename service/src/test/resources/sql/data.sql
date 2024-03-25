INSERT INTO product_type (id, parent_id, name)
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
SELECT SETVAL('product_id_seq', (SELECT MAX(id) FROM product_type));

INSERT INTO users (email, password, name, patronimic, surname, telephone)
VALUES ('test@mail.ru', '123', 'Иван', 'Иванович', 'Иванов', '89089566776');

INSERT INTO product (id, code, product_type_id, producer, model, price, count)
VALUES (1, '1292955', 5, 'DEXP', '4M2CTYL/B', 6499.0, 5),
       (2, '1332067', 5, 'DEXP', 'EH-C2NSMA/B', 6999.0, 5),
       (3, '5005295', 5, 'DARINA', '1B4TODB', 5555.0, 2),
       (4, '5005292', 5, 'DEXP', '1B4TODB', 10000.0, 2),
       (5, '5059789', 7, 'DEXP', 'EH-I2MB/B', 7499.0, 5),
       (6, '5059786', 7, 'DEXP', 'EH-I2SMA/B', 7499.0, 5),
       (7, '4714690', 9, 'De Luxe', '9M2GT ST', 3000.0, 1),
       (8, '1291416', 15, 'Vari Litta', 'L31122', 799.0, 10),
       (9, '1118856', 15, 'Vari Litta', 'L31122', 1350.0, 3);

INSERT INTO property_info(id, name, unit, dtype)
VALUES (1, 'Всего конфорок', 'шт', 'INTEGER'),
       (2, 'Ширина', 'см', 'DOUBLE'),
       (3, 'Основной материал изготовления панели', null, 'STRING'),
       (4, 'Рамка', null, 'BOOLEAN'),
       (5, 'Таймер конфорок', null, 'STRING'),
       (6, 'Диаметр сковороды', 'см', 'DOUBLE');

INSERT INTO property (id, property_info_id, string_value, integer_value, double_value, date_value, is_value)
VALUES (1, 1, null, 2, null, null, null),
       (2, 1, null, 4, null, null, null),
       (3, 2, null, null, 56.0, null, null),
       (4, 2, null, null, 26.5, null, null),
       (5, 2, null, null, 26.8, null, null),
       (6, 3, 'стеклокерамика', null, null, null, null),
       (7, 4, null, null, null, null, false),
       (8, 5, 'с автоотключением', null, null, null, null),
       (9, 5, 'независимый (только оповещение), с автоотключением', null, null, null, null),
       (10, 6, null, 22.0, null, null, null),
       (11, 6, null, 28.0, null, null, null);

INSERT INTO product_property(id, product_id, property_id)
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
       (17, 9, 11);





