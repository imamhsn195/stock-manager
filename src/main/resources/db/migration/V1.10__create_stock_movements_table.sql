CREATE TABLE stock_movements(
  id INT NOT NULL AUTO_INCREMENT,
  product_id INT NOT NULL,
  quantity_moved INT NOT NULL,
  movement_date DATE NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);