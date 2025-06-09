package org.example.jewelryshop.repositories;

import org.example.jewelryshop.models.products;

public class ProductsDao extends BaseDao<products>{
    public ProductsDao() {
        super(products.class);
    }
}
