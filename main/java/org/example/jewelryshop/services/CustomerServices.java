package org.example.jewelryshop.services;

import org.example.jewelryshop.models.customers;
import org.example.jewelryshop.repositories.CustomersDao;

import java.util.List;

public class CustomerServices {

private static CustomersDao currentDao = new CustomersDao();

public static List<customers> findAll(){return currentDao.findAll();}

    public customers findOne(final long id){return currentDao.findOne(id);}

    public void save(final customers entity){
    if (entity == null){
        return;
    }
        currentDao.save(entity);
    }

    public void update(final customers entity){
    if(entity == null){
        return;
    }
        currentDao.update(entity);
    }

    public void delete(final customers entity){
    if(entity == null){
        return;
    }
        currentDao.delete(entity);
    }

    public void deleteById(final Long id){
    if (id == null){
        return;
    }
        currentDao.deleteById(id);
    }
}
