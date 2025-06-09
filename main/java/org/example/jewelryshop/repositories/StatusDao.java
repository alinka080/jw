package org.example.jewelryshop.repositories;

import org.example.jewelryshop.models.statuses;

public class StatusDao extends BaseDao<statuses>{
    public StatusDao() {
        super(statuses.class);
    }
}
