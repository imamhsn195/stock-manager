-- Version: 1
CREATE TABLE customer (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  shipping_address TEXT NOT NULL,
  PRIMARY KEY (id)
);