-- DROP TABLE users;
CREATE TABLE IF NOT EXISTS users(
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(32) NOT NULL,
    surname VARCHAR(32),
    email VARCHAR(50) NOT NULL,
    login VARCHAR(32) NOT NULL,
    user_password VARCHAR(40) NOT NULL
);

 INSERT INTO users VALUES
  (DEFAULT, 'Tanya', 'Babak','tanya@mail.com', '12345567', '827ccb0eea8a706c4c34a16891f84e7b'),
  (DEFAULT, 'User','Test', 'test@mail.com', 'testlogin','5f4dcc3b5aa765d61d8327deb882cf99');

--  DROP TABLE orders;
CREATE TABLE IF NOT EXISTS orders(
id INT(10) AUTO_INCREMENT PRIMARY KEY,
status_order VARCHAR(255) NOT NULL,
detail_status TEXT,
id_users INT(10) NOT NULL,
created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (id_users) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

 INSERT INTO orders VALUES
 (DEFAULT, 'EXECUTIVE',NULL,1, default);

CREATE TABLE IF NOT EXISTS categories(
id INT AUTO_INCREMENT PRIMARY KEY,
category_name VARCHAR(20)
);

 INSERT INTO categories VALUES
 (DEFAULT,'computer'),
 (DEFAULT,'fridge'),
 (DEFAULT,'coffee machine'),
 (DEFAULT,'microwave');

-- DROP TABLE manufacturers;
CREATE TABLE IF NOT EXISTS manufacturers(
id INT AUTO_INCREMENT PRIMARY KEY,
manufacturer_name VARCHAR(120)
);

 INSERT INTO manufacturers VALUES
 (DEFAULT, 'Acer'),
 (DEFAULT, 'Asus'),
 (DEFAULT, 'HR');

--  DROP TABLE products;
 CREATE TABLE IF NOT EXISTS products(
     id INT(10) AUTO_INCREMENT PRIMARY KEY,
     product_name VARCHAR(60) NOT NULL,
     price DECIMAL(10, 2) NOT NULL,
     product_description text NOT NULL,
     category_id int(10) NOT NULL,
     manufacturer_id int(10) NOT NULL,
     link VARCHAR(60),
     FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE ON UPDATE CASCADE,
     FOREIGN KEY (manufacturer_id) REFERENCES manufacturers(id) ON DELETE CASCADE ON UPDATE CASCADE
 );
  INSERT INTO products VALUES
  (DEFAULT, 'Asus X509FL-BQ053', 456, 'Intel Core i5-8400 (2.8 - 4.0 ГГц) / RAM 8 ГБ / HDD 1 ТБ / Intel UHD Graphics 630 / without ОD / LAN / Endless OS', 1, 2,'../new_image_products/computer1.jpg'),
  (DEFAULT,'Comfee EM720CPL-PM Microwave Oven', 1000, 'Stylish (black) stainless steel finish, Compact Size: 0.7 cubic-foot capacity, 700 watts and 11 power levels', 4, 2, '../new_image_products/microwave.jpg'),
  (DEFAULT,'Liebherr 344L Integrated Fridge SIKB3550RH', 800, 'Width: 29.5 Height: 66.6 Depth: 32.8" / DOS / 2.22 кг / gray', 2, 3,'../new_image_products/fridge.jpg'),
  (DEFAULT,'HP Pavilion Gaming 15-cx0027ua', 2345, '15.6" IPS (1920x1080) Full HD, matte / Intel Core i5-8300H (2.3 - 4.0) / RAM 8 ГБ / SSD 256 ГБ / nVidia GeForce GTX 1050 Ti, 4 ГБ ',1, 1, '../new_image_products/computer2.jpg'),
  (DEFAULT,'New Izzo Alex PID Espresso Coffee Machine', 345, '3 liter fresh water reservoir or alternatively direct water connection by turning the control switch with full drip tray drainage system.', 3, 2, '../new_image_products/coffeemachine.jpg'),
  (DEFAULT,'Dell OptiPlex 3070 MFF', 24567, 'Intel Core i3-9100T (3.1 - 3.7 ГГц) / RAM 4 ГБ / HDD 500 ГБ / Intel UHD Graphics 630 / LAN / Windows 10 Pro', 1, 2, '../new_image_products/computer3.jpg'),
  (DEFAULT,'Acer Veriton S2660G', 3456, 'Intel Core i5-8400 (2.8 - 4.0 ГГц) / RAM 8 ГБ / HDD 1 ТБ / Intel UHD Graphics 630 / LAN / Endless OS', 1, 3, '../new_image_products/computer4.jpg');

--  DROP TABLE order_items;
CREATE TABLE IF NOT EXISTS order_items(
id INT(10) AUTO_INCREMENT PRIMARY KEY,
id_orders INT(10) NOT NULL,
id_products INT(10) NOT NULL,
count INT(10),
FOREIGN KEY (id_orders) REFERENCES orders(id) ON DELETE CASCADE ON UPDATE CASCADE,
FOREIGN KEY (id_products) REFERENCES products(id) ON DELETE CASCADE ON UPDATE CASCADE
);

 INSERT INTO order_items VALUES
 (DEFAULT, 1, 2, 1);
 select * FROM orders;