CREATE TABLE attachments (
  id INT PRIMARY KEY AUTO_INCREMENT,
  module_id INT NOT NULL,
  module_name VARCHAR(50) NOT NULL,
  attachment_type VARCHAR(50) NOT NULL,
--  Products
  CONSTRAINT attachments_module_id_product_fk
    FOREIGN KEY (module_id)
    REFERENCES products(id)
    ON DELETE CASCADE,
--  Customers
  CONSTRAINT attachments_module_id_customer_fk
    FOREIGN KEY (module_id)
    REFERENCES customers(id)
    ON DELETE CASCADE,
--  Supplier
  CONSTRAINT attachments_module_id_supplier_fk
    FOREIGN KEY (module_id)
    REFERENCES suppliers(id)
    ON DELETE CASCADE,
--  Sale
  CONSTRAINT attachments_module_id_sale_fk
    FOREIGN KEY (module_id)
    REFERENCES sales(id)
    ON DELETE CASCADE,
--  Purchase
  CONSTRAINT attachments_module_id_purchase_fk
    FOREIGN KEY (module_id)
    REFERENCES purchases(id)
    ON DELETE CASCADE,
);

CREATE TABLE attachment_paths (
  id INT PRIMARY KEY AUTO_INCREMENT,
  attachment_id INT NOT NULL,
  path_id INT NOT NULL,
  file_path VARCHAR(255) NOT NULL,
  CONSTRAINT attachment_paths_attachment_id_fk
    FOREIGN KEY (attachment_id)
    REFERENCES attachments(id)
    ON DELETE CASCADE
);