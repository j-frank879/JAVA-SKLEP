CREATE TABLE IF NOT EXISTS Product
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(50) NOT NULL,
    price decimal NOT NULL
);

CREATE TABLE IF NOT EXISTS Orders
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customerId integer NOT NULL,
    productName varchar(50) NOT NULL,
    productCount integer NOT NULL,
    total integer NOT NULL,
    isPaid bit NOT NULL,
    isCancelled bit NOT NULL
);

CREATE TABLE IF NOT EXISTS Users
(
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  login varchar(50) NOT NULL,
  password varchar(50) NOT NULL,
role varchar(50) NOT NULL,
    name varchar(50) ,
    email varchar(50),
  balance decimal
);