package com.driw.product;

import java.util.List;

public interface ProductService {
    Product getProductByName(String name);
    Product getProductById(Long id);
    List<Product> findAll();
}
