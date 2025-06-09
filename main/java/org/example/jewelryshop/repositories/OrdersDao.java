package org.example.jewelryshop.repositories;

import org.example.jewelryshop.models.orders;

public class OrdersDao extends BaseDao<orders>{
    public OrdersDao() {
        super(orders.class);
    }
}
