
/*PRODUCT*/
SET @product_id_1 = 1;
SET @product_id_2 = 2;

INSERT INTO product(product_id, name, package_discount_threshold_value, package_discount_percentage, package_price, unit_increase_percentage, units_pr_package) VALUES (@product_id_1, 'Pingvinører', 3, 10, 175.0, 30, 20);

INSERT INTO product(product_id, name, package_discount_threshold_value, package_discount_percentage, package_price, unit_increase_percentage, units_pr_package) VALUES (@product_id_2, 'Hestesko', 3, 10, 825.0, 30, 5);