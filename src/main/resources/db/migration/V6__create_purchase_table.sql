-- Version: 1
CREATE TABLE purchase (
  id INT NOT NULL AUTO_INCREMENT,
  supplier_id INT NOT NULL,
  purchase_date DATE NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (supplier_id) REFERENCES supplier(id)
);
