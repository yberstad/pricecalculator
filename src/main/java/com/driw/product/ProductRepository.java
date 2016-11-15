package com.driw.product;

import org.springframework.data.jpa.repository.JpaRepository;

interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    Product findById(Long id);
}
