INSERT INTO producer (id, name)
VALUES (1, 'DEXP'),
       (2, 'DARINA'),
       (3, 'De Luxe'),
       (4, 'bosh'),
       (5, 'Eigen'),
       (6, 'Electrolux'),
       (7, 'Gorenje'),
       (8, 'Haier'),
       (9, 'Akpo'),
       (10, 'BEKO'),
       (11, 'Cata'),
       (12, 'Gefest'),
       (13, 'Graude'),
       (14, 'Hansa'),
       (15, 'HomSair'),
       (16, 'Hopoint_Ariston'),
       (17, 'Hyundai'),
       (18, 'Kaiser'),
       (19, 'krona1'),
       (20, 'Kuppersberg'),
       (21, 'LEX EVH'),
       (22, 'Lg'),
       (23, 'Maunfeld'),
       (24, 'Weissgauff'),
       (25, 'Simfer'),
       (26, 'MBS');
SELECT SETVAL('producer_id_seq', (SELECT MAX(id) FROM producer));

INSERT INTO category (id, parent_id, name, image)
VALUES (1, null, 'Бытовая техника', 'bytovaya-texnika.png'),
       (2, 1, 'Встраиваемая техника', 'vstraivaemaya-texnika.jpg'),
       (3, 2, 'Варочные панели', 'varochnye-paneli.jpg'),
       (4, 3, 'Варочные панели электрические', 'varochnye-paneli-elektricheskie.png'),
       (5, 4, 'Электрическая варочная поверхность', 'varochnye-paneli-elektricheskie.png'),
       (6, 3, 'Варочные панели индукционные', 'varochnye-paneli-ind.jpg'),
       (7, 6, 'Индукционная варочная поверхност', 'varochnye-paneli-ind.jpg'),
       (8, 3, 'Варочные панели газовые', 'varochnye-paneli-gazovye.jpg'),
       (9, 8, 'Газовая варочная поверхность', 'varochnye-paneli-gazovye.jpg'),
       (10, 2, 'Духовые шкафы', 'duxovye-shkafy.jpg'),
       (11, 1, 'Техника для кухни', 'texnika-dlya-kuxni.png'),
       (12, null, 'Дом, декор и посуда', 'dom-dekor-i-posuda.png'),
       (13, 12, 'Посуда и кухонные предметы', 'posuda-i-kuxonnye-predmety.jpg'),
       (14, 13, 'Сковороды и сотейники', 'skovorody-i-sotejniki.JPG'),
       (15, 14, 'Сковорода', 'skovorody-i-sotejniki.JPG');
SELECT SETVAL('product_id_seq', (SELECT MAX(id) FROM category));

INSERT INTO users (id, email, password, name, patronimic, surname, telephone, role, birth_date)
VALUES (1, 'test@mail.ru', '$2a$10$vreI7Z9jXgtZj1Gx.UJnAO6H52s3hLEUMLYBQEPMrrOHR1BtHyg/K', 'Иван', 'Иванович', 'Иванов',
        '89089566776', 'ADMIN', '1990-01-10'),
       (2, 'user@mail.ru', '$2a$10$RM8PwswdINM9J.Uvugp/Ee5zggxRJuwX1HkeFB39fUEcpA5DQ24Om', 'Name', 'Patronimic',
        'Surname',
        '22-22-22', 'USER', '2024-04-01'),
       (3, 'i7rincpane@gmail.com', '$2a$10$Bb3WwQKuRLuf7TfdwNRuZ.La.w2ygvjT5EV82MrYxMmguJwiTZXLm', 'Name2',
        'Patronimic2',
        'Surname2',
        '33-33-33', 'ADMIN', '2024-01-01'),
       (4, 'pane@gmail.com', '$2a$10$2BXtHSoeYfSe3hFjK4rTK.RPkECXACNwh9s/.MgjytXwqncVzgk6G', 'Name3', 'Patronimic3',
        'Surname3',
        '44-44-44', 'ADMIN', '2024-01-01'),
       (5, 'withoutbasket@gmail.com', '$2a$10$8y/b09qmrc5hW3qZ0eKZeOCBRIBRvwvTrV29MB6UAaZTcAhyeg16S', 'Name4',
        'Patronimic4',
        'Surname4',
        '55-55-55', 'ADMIN', '2024-01-01');
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO product (id, code, category_id, producer_id, model, price, count, image)
VALUES (1, '1292955', 5, 1, '4M2CTYL/B', 6499.0, 5, 'products.png'),
       (2, '1332067', 5, 1, 'EH-C2NSMA/B', 6999.0, 5, 'products.png'),
       (3, '5005295', 5, 2, '1B4TODB', 5555.0, 0, 'products.png'),
       (4, '5005292', 5, 1, '1B4TOD2B', 10000.0, 2, 'products.png'),
       (5, '5059789', 7, 1, 'EH-I2MB/B', 7499.0, 5, 'products.png'),
       (6, '5059786', 7, 1, 'EH-I2SMA/B', 7499.0, 5, 'products.png'),
       (7, '4714690', 9, 3, '9M2GT ST', 3000.0, 1, 'products.png'),
       (8, '1291416', 15, 4, 'L31122', 799.0, 10, 'products.png'),
       (9, '1118856', 15, 4, 'L31122', 1350.0, 3, 'products.png'),
       (11, '50597869', 5, 24, 'HV 312 BA', 7599.00, 10, 'products.png'),
       (12, '1075382', 5, 24, 'HV 312B', 8099, 10, 'products.png'),
       (13, '9956635', 5, 24, 'HV 32 BA', 8499, 10, 'products.png'),
       (14, '1148322', 5, 2, 'P E545 B', 8599, 10, 'products.png'),
       (15, '8113498', 5, 24, 'HV 32 B', 8699, 10, 'products.png'),
       (16, '9014373', 5, 1, 'EH-IH2T30/CH', 8999, 10, 'products.png'),
       (17, '5349759', 5, 21, '320-0 BL', 9099, 10, 'products.png'),
       (18, '8198869', 5, 23, 'EVI.292ST-BK', 9299, 10, 'products.png'),
       (19, '1031353', 5, 21, '320 BL', 9699, 10, 'products.png'),
       (20, '9903206', 5, 25, 'H30D12V020', 10199, 10, 'products.png'),
       (21, '5005311', 5, 1, '1B4TDB', 10499, 10, 'products.png'),
       (22, '1196964', 5, 26, 'PE-302', 10499, 10, 'products.png'),
       (23, '8180940', 5, 25, 'H45E03M016', 10499, 10, 'products.png'),
       (24, '5352967', 5, 23, 'EEHE.32VCBB.R', 10699, 10, 'products.png');
SELECT SETVAL('product_id_seq', (SELECT MAX(id) FROM product));

INSERT INTO property(id, category_id, name, unit, dtype)
VALUES (1, 5, 'Всего конфорок', 'шт', 'NUMBER'),
       (2, 5, 'Ширина', 'см', 'FLOAT'),
       (3, 5, 'Основной материал изготовления панели', null, 'STRING_CLASSIFIER'),
       (4, 5, 'Рамка', null, 'STRING_CLASSIFIER'),
       (5, 5, 'Таймер конфорок', null, 'STRING_CLASSIFIER'),
       (6, 15, 'Диаметр сковороды', 'см', 'FLOAT'),
       (7, 5, 'testNumberField', null, 'NUMBER');
SELECT SETVAL('property_id_seq', (SELECT MAX(id) FROM property));

INSERT INTO string_classifier(id, name, property_id)
VALUES (1, 'стеклокерамика', 3),
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
       (10, 1, 4, null, null, null, null, null, null),
       (11, 2, 4, null, null, null, null, null, null),
       (12, 3, 4, null, null, null, null, null, null),
       (13, 1, 5, null, null, null, null, null, 3),
       (14, 2, 5, null, null, null, null, null, 4),
       (15, 3, 5, null, null, null, null, null, 4),
       (16, 8, 6, null, null, 22.0, null, null, null),
       (17, 9, 6, null, null, 28.0, null, null, null),
       (18, 4, 1, null, 2, null, null, null, null),
       (19, 4, 2, null, null, 26.5, null, null, null),
       (20, 11, 1, null, 3, null, null, null, null),
       (21, 11, 7, null, 28, null, null, null, null),
       (22, 3, 7, null, 30, null, null, null, null),
       (23, 12, 1, null, 5, null, null, null, null),
       (25, 1, 7, null, 28, null, null, null, null),
       (26, 2, 7, null, 30, null, null, null, null),
       (27, 4, 7, null, 32, null, null, null, null);
SELECT SETVAL('product_property_id_seq', (SELECT MAX(id) FROM product_property));

INSERT INTO basket(id, user_id, sum, count)
VALUES (1, 2, 18553, 3),
       (2, 1, 7849, 2),
       (3, 3, 0, 0),
       (4, 4, 0, 0);
SELECT SETVAL('basket_id_seq', (SELECT MAX(id) FROM basket));

INSERT INTO basket_product(id, basket_id, product_id, count, sum, is_selected)
VALUES (1, 1, 1, 2, 12998, true),
       (2, 1, 3, 1, 5555, true),
       (3, 2, 1, 1, 6499, true),
       (4, 2, 9, 1, 1350, true),
       (5, 4, 9, 1, 1350, false),
       (6, 2, 2, 1, 6999, false);
SELECT SETVAL('basket_product_id_seq', (SELECT MAX(id) FROM basket_product));

INSERT INTO orders(id, created_at, updated_at, sum, user_id, order_status, count, basket_id)
VALUES (1, '2024-04-10', null, 5555, 2, 'CANCELED', 1, 1),
       (2, '2024-04-01', null, 6499, 2, 'PROCESSING', 1, 1),
       (3, '2023-04-01', null, 12054, 1, 'PROCESSING', 2, 2);
SELECT SETVAL('orders_id_seq', (SELECT MAX(id) FROM orders));

INSERT INTO order_product(id, order_id, product_id, count, sum)
VALUES (1, 1, 3, 1, 5555),
       (2, 2, 1, 1, 6499),
       (3, 3, 1, 1, 6499),
       (4, 3, 3, 1, 5555);
SELECT SETVAL('order_product_id_seq', (SELECT MAX(id) FROM order_product));