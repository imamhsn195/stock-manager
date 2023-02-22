-- Version: 1
CREATE TABLE stock_movement (
  id INT NOT NULL AUTO_INCREMENT,
  product_id INT NOT NULL,
  quantity_moved INT NOT NULL,
  movement_date DATE NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (product_id) REFERENCES product(id)
);