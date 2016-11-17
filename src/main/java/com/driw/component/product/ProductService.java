package com.driw.component.product;

import java.util.List;

/**
 * <p>By dividing the application up into "features" instead of layers, it is easier to achieve encapsulation.
 * Using encapsulation will help the developer making the "right" decisions.</p>
 *
 * <p>An example would be that instead of letting the controller do the orchestration between different services,
 * one could be tempted to call the ProductRepository directly from another service without going through
 * the ProductService. Hence missing out important functionality implemented in the ProductService.</p>
 *
 */
public interface ProductService {
    /**
     * Retrieve a specific Product by id.
     * @param id The id (primary key) of the Product
     * @return Returns Product if found. Else null.
     */
    Product getProductById(Long id);

    /**
     * Retrieve a list of Products
     * @return List of all Product's
     */
    List<Product> findAll();
}
