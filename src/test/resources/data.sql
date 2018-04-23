INSERT INTO t_catalogues(code, name) VALUES ('CAT001','Xiaomi Smartphones');
INSERT INTO t_catalogues(code, name) VALUES ('CAT002','BQ Smartphones');
INSERT INTO t_catalogues(code, name) VALUES ('CAT002','Huawei Smartphones');

INSERT INTO t_articles(code, description, name, price, catalogue_id) VALUES ('ART001','Xiaomi Mi A1 con 4GB de RAM y 64GB de almacenamiento','Xiaomi Mi A1',229,1);
INSERT INTO t_articles(code, description, name, price, catalogue_id) VALUES ('ART002','Xiaomi Redmi 5 2/16Gb Negro Libre','Xiaomi Redmi 5',129,1);
INSERT INTO t_articles(code, description, name, price, catalogue_id) VALUES ('ART003','Xiaomi Redmi Note 4 3GB/32GB 4G Gris Libre','Xiaomi Redmi Note 4',169,1);
INSERT INTO t_articles(code, description, name, price, catalogue_id) VALUES ('ART004','Xiaomi Redmi 5A 4G 16GB Dual Sim Gris Libre','Xiaomi Redmi 5A',109,1);

INSERT INTO t_articles(code, description, name, price, catalogue_id) VALUES ('ART005','BQ Aquaris VS 4/64GB Dorado Libre','BQ Aquaris VS',199,2);
INSERT INTO t_articles(code, description, name, price, catalogue_id) VALUES ('ART006','Bq Aquaris U Plus 4G 2GB/16GB Negro Libre','Bq Aquaris U Plus',134,2);
INSERT INTO t_articles(code, description, name, price, catalogue_id) VALUES ('ART007','Bq Aquaris X Pro 3GB/32GB Negro Libre Dual Sim','Bq Aquaris X Pro',251.90,2);
INSERT INTO t_articles(code, description, name, price, catalogue_id) VALUES ('ART008','Bq Aquaris U2 Lite 2GB/16GB Blanco','Bq Aquaris U2 Lite',129,2);

INSERT INTO t_articles(code, description, name, price, catalogue_id) VALUES ('ART009','Huawei P8 Lite 2017 Negro Libre','Huawei P8 Lite',159.01,3);
INSERT INTO t_articles(code, description, name, price, catalogue_id) VALUES ('ART010','Huawei P10 Lite 4GB/32GB Blanco Libre','Huawei P10 Lite',259,3);
INSERT INTO t_articles(code, description, name, price, catalogue_id) VALUES ('ART011','Huawei Y6 II Blanco','Huawei Y6',139,3);
INSERT INTO t_articles(code, description, name, price, catalogue_id) VALUES ('ART012','Huawei Nexus 6P 4G Gris Libre','Huawei Nexus 6P',430.33,3);

INSERT INTO t_orders(code) VALUES ('ORD001');
INSERT INTO t_orders(code) VALUES ('ORD002');

INSERT INTO t_orders_articles(order_id, articles_id) VALUES (1,1);
INSERT INTO t_orders_articles(order_id, articles_id) VALUES (1,5);
INSERT INTO t_orders_articles(order_id, articles_id) VALUES (1,9);

INSERT INTO t_orders_articles(order_id, articles_id) VALUES (2,2);
INSERT INTO t_orders_articles(order_id, articles_id) VALUES (2,6);
INSERT INTO t_orders_articles(order_id, articles_id) VALUES (2,10);