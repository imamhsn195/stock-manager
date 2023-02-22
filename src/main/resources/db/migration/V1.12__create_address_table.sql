CREATE TABLE address (
  id INT NOT NULL AUTO_INCREMENT,
  street VARCHAR(255) NOT NULL,
  city VARCHAR(255) NOT NULL,
  state VARCHAR(255) NOT NULL,
  country VARCHAR(255) NOT NULL,
  zip_code VARCHAR(255),
  PRIMARY KEY (id)
);