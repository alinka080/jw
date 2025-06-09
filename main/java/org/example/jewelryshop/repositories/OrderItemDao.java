package org.example.jewelryshop.repositories;

import org.example.jewelryshop.models.orderItems;

public class OrderItemDao extends BaseDao<orderItems>{
    public OrderItemDao() {
        super(orderItems.class);
    }
}
