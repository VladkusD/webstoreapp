CREATE DATABASE IF NOT EXISTS web_shop;
USE web_shop;
CREATE TABLE user (
user_id int NOT NULL AUTO_INCREMENT,
user_first_name varchar(20) NOT NULL,
user_last_name varchar(20) NOT NULL,
user_phone varchar(30) NOT NULL,
user_email varchar(50) NOT NULL,
user_address varchar(100),
user_status int,
user_password varchar(100),
PRIMARY KEY (user_id)
);
CREATE TABLE user_status(
user_status int,
status_description varchar(30)
);
INSERT INTO user_status VALUES (0,"Not registered"),
							   (1,"Registeruser statused") ;
CREATE TABLE system_roles (
role_id int,
role_description varchar(50),
PRIMARY KEY (role_id)
);
CREATE TABLE user_right (
user_id int,
role_id int,
PRIMARY KEY (user_id),
FOREIGN KEY (role_id) REFERENCES system_roles (role_id)
);
CREATE TABLE web_order (
    order_id int NOT NULL AUTO_INCREMENT,
    user_id int,
    user_status VARCHAR(30),
    PRIMARY KEY (order_id),
    FOREIGN KEY (user_id) REFERENCES user(user_id)
);
CREATE TABLE products (
product_id int NOT NULL AUTO_INCREMENT,
product_name varchar(50),
product_desc varchar(500),
product_price double(6,2),
product_image varchar(300) DEFAULT NULL,
PRIMARY KEY (product_id)
);
CREATE TABLE order_item(
order_item_id int NOT NULL AUTO_INCREMENT,
order_id int,
product_id int,
product_quantity int,
PRIMARY KEY (order_item_id),
FOREIGN KEY (order_id) REFERENCES web_order (order_id),
FOREIGN KEY (product_id) REFERENCES products(product_id)
);
INSERT INTO products (product_name,product_desc,product_price) VALUES ("Cucumber","Cucumber is a summer vegetable, with elongate shape and 15cm long. Its skin is of a green colour, turning into yellow in maturation.
 At present, it is found in the European markets all over the year. Fresh or pickled cucumbers are also available.",4.50),
 ("Tomato","They are usually red, scarlet, or yellow, though green and purple varieties do exist, and they vary in shape from almost spherical to oval and elongate to pear-shaped. Each fruit contains at least two
 cells of small seeds surrounded by jellylike pulp.",5.00);


-- UPDATE products
-- SET product_image =
--     CASE
--         WHEN product_name = 'Cucumber' THEN 'cucumber.jpg'
--         WHEN product_name = 'Tomato' THEN 'tomato.jpg'
--         WHEN product_name = 'Onion' THEN 'onion.jpg'
--         WHEN product_name = 'Potato' THEN 'potato.jpg'
--         WHEN product_name = 'Carrot' THEN 'carrot.jpg'
--         WHEN product_name = 'Red pepper' THEN 'redpepper.jpg'
--         WHEN product_name = 'Broccoli' THEN 'broccoli.jpg'
--         WHEN product_name = 'Mushrooms' THEN 'mushrooms.jpg'
--         WHEN product_name = 'Beetroot' THEN 'beetroot.jpg'
--         WHEN product_name = 'Cabbage' THEN 'cabbage.jpg'
--         WHEN product_name = 'Brinjal' THEN 'brinjal.jpg'
--     END
-- WHERE product_name IN ('Cucumber', 'Tomato', 'Onion', 'Potato', 'Carrot', 'Red pepper', 'Broccoli', 'Mushrooms', 'Beetroot', 'Cabbage', 'Brinjal');


--UPDATE products
-- SET product_desc =
--    CASE
--        WHEN product_name = 'Cucumber' THEN 'Cucumber is a summer green vegetable, with elongate shape and 15cm long. Its skin is of a green colour, turning into yellow in maturation.
-- '
--       WHEN product_name = 'Broccoli' THEN 'Broccoli has large green flower heads, usually dark green, arranged in a tree-like structure branching out from a thick stalk which is usually light green.'
--        WHEN product_name = 'Cabbage' THEN 'Cabbage is green, leafy vegetable with a round, compact head or "heart" made up of many layers of thick, sturdy leaves that grow in a tightly packed formation.'
--
--    END
--WHERE product_name IN ('Cucumber','Broccoli','Cabbage');