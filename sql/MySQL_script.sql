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