package org.example.jewelryshop.repositories;

import org.example.jewelryshop.models.customers;

public class CustomersDao extends BaseDao<customers>{

    public CustomersDao() {
        super(customers.class);
    }
}
