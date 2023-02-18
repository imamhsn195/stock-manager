-- Version: 1
CREATE TABLE purchase_item (
  id INT NOT NULL AUTO_INCREMENT,
  purchase_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity_purchased INT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (purchase_id) REFERENCES purchase(id),
  FOREIGN KEY (product_id) REFERENCES product(id)
);