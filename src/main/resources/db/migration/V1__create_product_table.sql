-- Version: 1
CREATE TABLE product (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  price DECIMAL(10,2) NOT NULL,
  quantity_in_stock INT NOT NULL,
  PRIMARY KEY (id)
);
