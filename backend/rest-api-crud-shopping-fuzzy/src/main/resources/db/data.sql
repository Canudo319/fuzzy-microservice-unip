INSERT INTO TBL_SHOPPING_ITEM (name,brand,type,image) VALUES ('Bolacha Wafer Avelã','Bauducco','Alimentação','https://www.bauducco.com.br/Style%20Library/assets/images/Wafer%20Chocolate%20com%20Avel%C3%A3/wafer_choco_avela.png');
INSERT INTO TBL_SHOPPING_ITEM (name,brand,type,image) VALUES ('Bolhacha Wafer Prestígio','Bauducco','Alimentação','https://images.tcdn.com.br/img/img_prod/655345/biscoito_prestigio_wafer_110g_7119_1_922c35ddf8b8b774804db7d12a3a8982.png');
INSERT INTO TBL_SHOPPING_ITEM (name,brand,type,image) VALUES ('Bolhacha Wafer Chocolate','Marilan','Alimentação','https://www.marilan.com/wp-content/uploads/2018/10/wafer-chocolate.png');
INSERT INTO TBL_SHOPPING_ITEM (name,brand,type,image) VALUES ('Bolhacha Wafer Chocolate','Adria','Alimentação','https://www.adria.com.br/wp-content/uploads/2020/11/Adria-Wafer-Chocolate-100g.png');
INSERT INTO TBL_SHOPPING_ITEM (name,brand,type,image) VALUES ('Bolhacha Wafer Chocolate','Aymoré','Alimentação','https://upside.vteximg.com.br/arquivos/ids/725838-1000-1000/38450.png?v=637751978712400000');
INSERT INTO TBL_SHOPPING_ITEM (name,brand,type,image) VALUES ('Arroz Branco 1kg','Tio João','Alimentação','https://www.clubeextra.com.br/img/uploads/1/246/596246.png');
INSERT INTO TBL_SHOPPING_ITEM (name,brand,type,image) VALUES ('Arroz Branco 1kg','Prato Fino','Alimentação','https://static.clubeextra.com.br/img/uploads/1/1/574001.png');
INSERT INTO TBL_SHOPPING_ITEM (name,brand,type,image) VALUES ('Arroz Branco 1kg','Broto Legal','Alimentação','https://trimais.vteximg.com.br/arquivos/ids/1028752-1000-1000/foto_original.jpg?v=637719741869070000');
INSERT INTO TBL_SHOPPING_ITEM (name,brand,type,image) VALUES ('Café instantaneo','Nescafé','Alimentação','https://www.clubeextra.com.br/img/uploads/1/119/12355119.jpg');
INSERT INTO TBL_SHOPPING_ITEM (name,brand,type,image) VALUES ('Achocolatado em pó Nescau','Nestle','Alimentação','https://static.paodeacucar.com/img/uploads/1/855/675855.jpg');

INSERT INTO TBL_SUPPLIER(name,latitude,longitude) VALUES ('Mercadinho do zé',20,30);
INSERT INTO TBL_SUPPLIER(name,latitude,longitude) VALUES ('Mercearia da maria',40,40);
INSERT INTO TBL_SUPPLIER(name,latitude,longitude) VALUES ('Mercadão do Tonhão',5,60);
INSERT INTO TBL_SUPPLIER(name,latitude,longitude) VALUES ('O Grande Mercado de Cidadezoplis',190,120);

INSERT INTO TBL_STOCK(item_id,supplier_id,stock,price) VALUES (1,1,10,3.99);
INSERT INTO TBL_STOCK(item_id,supplier_id,stock,price) VALUES (2,2,20,4.99);
INSERT INTO TBL_STOCK(item_id,supplier_id,stock,price) VALUES (2,3,40,4.57);
INSERT INTO TBL_STOCK(item_id,supplier_id,stock,price) VALUES (3,3,40,3.99);
INSERT INTO TBL_STOCK(item_id,supplier_id,stock,price) VALUES (3,2,50,2.99);
INSERT INTO TBL_STOCK(item_id,supplier_id,stock,price) VALUES (2,4,50,2.99);
INSERT INTO TBL_STOCK(item_id,supplier_id,stock,price) VALUES (3,4,50,5.99);


